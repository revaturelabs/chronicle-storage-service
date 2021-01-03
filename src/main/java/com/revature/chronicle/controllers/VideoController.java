package com.revature.chronicle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/videos")
public class VideoController {

    private VideoService videoService;
    private VideoRepo videoRepo;

    @Autowired
    public VideoController (VideoService vs, VideoRepo vr) {
        this.videoService = vs;
        this.videoRepo = vr;
    }

    @GetMapping(path = "tags/{videoTags}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Video>> getVideosByTag(@PathVariable(name="videoTags") String crudeTags){
        System.out.println(crudeTags);
        String[] arrTags = crudeTags.split("\\+");
        List<Tag> targetTags = new List<>();
        for (String tag: arrTags) {
            Tag tempTag = new Tag();
            String[] tagComponents = tag.split(":");
            tempTag.setKey(tagComponents[0]);
            tempTag.setValue(tagComponents[1]);
            targetTags.add(tempTag);
        }
        List <Video> targetVideos = videoService.findAllVideosByTags(targetTags);
        return new ResponseEntity<>(targetVideos, HttpStatus.OK);
    }

    @GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Video>> getAllVideos() {
        List<Video> targetVideos = videoService.findAll();
        return new ResponseEntity<>(targetVideos, HttpStatus.OK);
    }
}
