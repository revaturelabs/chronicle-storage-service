package com.revature.chronicle.Controller;

import com.revature.chronicle.models.Media;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.services.InsertFileService;
import com.revature.chronicle.services.NoteService;
import com.revature.chronicle.services.S3FileService;
import com.revature.chronicle.services.VideoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Log4j2
@RestController
@RequestMapping(path="/file", method = {RequestMethod.GET, RequestMethod.POST})
public class FileUploadController {

    private S3FileService s3FileService;

    private final VideoService videoService;
    private NoteService noteService;

    @Autowired
    public FileUploadController(S3FileService service, VideoService videoService, NoteService noteService){
        this.s3FileService = service;
        this.videoService = videoService;
        this.noteService = noteService;
    }

    @PostMapping(path="/submit")
    @ResponseBody
    public ResponseEntity<String> uploadFile(@RequestParam("name") String json,
                                             @RequestParam ("file") MultipartFile file) {
        log.debug("Processing the following file data:" + json);
        Media newFile;
        String fileType;

        //Determine what type of file has been uploaded: [VIDEO or TEXT] and create the appropriate model object
        if(file.getContentType().equalsIgnoreCase("text")) {
            newFile = new Note();
            fileType = "note";
        } else if (file.getContentType().equalsIgnoreCase("video")) {
            newFile = new Video();
            fileType = "video";
        } else {
            return new ResponseEntity<>("Unsupported file type. Please upload either a video or a text file.",HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        File compiledFile = new File(System.getProperty("java.io.tmpdir")+"/" + file.getOriginalFilename());
        compiledFile.deleteOnExit();

        try {
            file.transferTo(compiledFile);
            String s3URL = s3FileService.uploadFile(compiledFile);

            //Insert s3URL and json form data into the database
            newFile.setUrl(s3URL);

            saveToDatabase(newFile, fileType);

        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed to write to file");
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("Service was interrupted!");
        } finally {
            compiledFile.delete();
        }
        return null;
    }

    private void saveToDatabase(Media media, String mediaType) {
        if(mediaType.equalsIgnoreCase("note")) {
            noteService.save((Note)media);
        } else if (mediaType.equalsIgnoreCase("video")) {
            videoService.save((Video)media);
        }
    }
}
