package com.revature.chronicle.controller;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.chronicle.models.Media;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.services.NoteService;
import com.revature.chronicle.services.S3FileService;
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

/**
 * This controller is being used to upload text or video files to the s3 bucket and save
 * the url location, of the newly uploaded content, to the application's database.
 */
@Log4j2
@RestController
@RequestMapping(path="/file", method = {RequestMethod.GET, RequestMethod.POST})
public class FileUploadController {

    private final S3FileService s3FileService;

    private final VideoService videoService;
    private final NoteService noteService;


    /**
     *
     * @param service The S3 file upload service
     * @param videoService The video service that saves the video s3 location to the database
     * @param noteService The note service that saves the note s3 location to the database
     */
    @Autowired
    public FileUploadController(S3FileService service, VideoService videoService, NoteService noteService) {
        this.s3FileService = service;
        this.videoService = videoService;
        this.noteService = noteService;
    }

    /**
     * This controller handles a request to upload a file and its metadata to the S3 bucket and save the file's S3
     * bucket location to the database
     * @param json This String param contains meta information about the file being uploaded to the S3 bucket
     * @param file This MulitpartFile param is the file being uploaded to the S3 bucket
     * @return The controller will return a status code and a message if the file has succeeded or failed to upload to
     * to the S3 bucket
     * @throws IOException
     */
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
            if (file.getContentType().contains("text")) {
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

    /**
     * Depending on the media type, the appropriate service will be called to save the file and its meta information to the database
     * @param media This parameter is a Media object that contains the file being uploaded and its associated meta
     *              information
     * @param mediaType This string parameter will either be "note" or "video"
     */
    public void saveToDatabase (Media media, String mediaType){
        if (mediaType.equalsIgnoreCase("note")) {
            noteService.save((Note) media);
        } else if (mediaType.equalsIgnoreCase("video")) {
            videoService.save((Video) media);
        }
    }
}
