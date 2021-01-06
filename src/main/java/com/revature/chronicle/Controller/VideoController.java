package com.revature.chronicle.Controller;

import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.daos.VideoRepo;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.services.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/videos")
public class VideoController {

    private final VideoService videoService;
    private final TagRepo tagRepo;
    private final VideoRepo videoRepo;


    public VideoController (VideoService vs, TagRepo tr, VideoRepo vr) {
        this.videoService = vs;
        this.tagRepo = tr;
        this.videoRepo = vr;
    }

    @GetMapping(path = "tags/{videoTags}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Video>> getVideosByTag(@PathVariable(name="videoTags") String crudeTags){
        System.out.println(crudeTags);
        String[] arrTags = crudeTags.split("\\+");
        List<Tag> targetTags = new ArrayList<>();
        for (String tag: arrTags) {
            Tag tempTag = new Tag();
            String[] tagComponents = tag.split(":");
            tempTag.setTagID(Integer.parseInt(tagComponents[0]));
            tempTag.setName(tagComponents[1]);
            tempTag.setValue(tagComponents[2]);
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

    @GetMapping(path = "available-tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tag>> getAllVideoTags() {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("Technology");
        tagNames.add("Batch");
        List<Tag> availableTags = tagRepo.findByNameIn(tagNames);
        return new ResponseEntity<>(availableTags, HttpStatus.OK);
    }

    @GetMapping(path = "id/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Video> getVideoById(@PathVariable(name="videoId") int id) {
        Video targetVideo = videoRepo.findByVideoID(id);
        return new ResponseEntity<>(targetVideo, HttpStatus.OK);
    }
}
