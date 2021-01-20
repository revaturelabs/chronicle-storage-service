package com.revature.chronicle.controller;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.chronicle.models.Media;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.services.NoteService;
import com.revature.chronicle.services.S3FileService;
import com.revature.chronicle.services.TagService;
import com.revature.chronicle.services.VideoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(path="/file", method = {RequestMethod.GET, RequestMethod.POST})
public class FileUploadController {

    private final S3FileService s3FileService;
    private final TagService tagService;
    private final VideoService videoService;
    private final NoteService noteService;

    @Autowired
    public FileUploadController(S3FileService service, VideoService videoService, NoteService noteService, TagService tagService) {
        this.s3FileService = service;
        this.videoService = videoService;
        this.noteService = noteService;
        this.tagService = tagService;
    }

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> uploadFile(@RequestParam("json") String json,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        log.info("Processing the following file data:" + content);

        Media newFile = null;
        String fileType = "";

        //Determine what type of file has been uploaded: [VIDEO or TEXT] and create the appropriate model object
        try {
            if (file.getContentType().contains("text") || file.getContentType().contains("pdf")) {
                newFile = new ObjectMapper().readValue(json, Note.class);
                fileType = "note";
            } else if (file.getContentType().contains("video")) {
                newFile = new ObjectMapper().readValue(json, Video.class);
                fileType = "video";
            } else {
                return new ResponseEntity<>("Unsupported file type. Please upload either a video or a text file.", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
            log.info(fileType.toUpperCase() + ": " + newFile.toString());

        } catch (MismatchedInputException e) {
            log.warn(e.getMessage());
        }

            File compiledFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            compiledFile.deleteOnExit();

        try {
            file.transferTo(compiledFile.getAbsoluteFile());
            log.debug(compiledFile);
            String s3URL = s3FileService.uploadFile(compiledFile);

            log.info("The generated S3 URL: " + s3URL);

            //Insert s3URL and json form data into the database
            if (newFile != null) {
                newFile.setUrl(s3URL);
            }
            saveToDatabase(newFile, fileType);

        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            log.error("Unable to access the AWS S3 bucket!");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed to write to file");
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("Service was interrupted!");
        } finally {
            compiledFile.delete();
        }
        return new ResponseEntity<>("Upload Successful", HttpStatus.OK);
    }
    public void saveToDatabase (Media media, String mediaType){
        if (mediaType.equalsIgnoreCase("note")) {
            log.info("Saving the " + mediaType.toUpperCase() + ": " + media.getDescription() + " to the database!");
            Note newNote = (Note) media;
            List<Tag> noteTags = new ArrayList<>();
            for (Tag tag: newNote.getTags()) {
                Tag tempTag = new Tag();
                tempTag.setTagID(tag.getTagID());
                tempTag.setName(tag.getName());
                tempTag.setValue(tag.getValue());
                if (tag.getTagID() == 0) {
                    tagService.save(tempTag);
                    tempTag = tagService.findByValue(tempTag.getValue());
                }
                noteTags.add(tempTag);
            }
            newNote.setTags(noteTags);
            noteService.save(newNote);
        } else if (mediaType.equalsIgnoreCase("video")) {
            log.info("Saving the " + mediaType.toUpperCase() + ": " + media.getDescription() + " to the database!");
            Video newVideo = (Video) media;
            List<Tag> videoTags = new ArrayList<>();
            for (Tag tag: newVideo.getTags()) {
                Tag tempTag = new Tag();
                tempTag.setTagID(tag.getTagID());
                tempTag.setName(tag.getName());
                tempTag.setValue(tag.getValue());
                if (tag.getTagID() == 0) {
                    tagService.save(tempTag);
                    tempTag = tagService.findByValue(tempTag.getValue());
                }
                videoTags.add(tempTag);
            }
            newVideo.setTags(videoTags);
            videoService.save(newVideo);
        }
    }
}
