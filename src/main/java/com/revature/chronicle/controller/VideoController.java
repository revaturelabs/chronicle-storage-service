package com.revature.chronicle.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.chronicle.daos.TagRepo;
import com.revature.chronicle.daos.VideoRepo;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.services.VideoService;
import com.revature.chronicle.services.VideoWhitelistService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/videos")
public class VideoController {

    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

    private final VideoService videoService;
    private final VideoWhitelistService videoWhitelistService;
    private final TagRepo tagRepo;
    private final VideoRepo videoRepo;

    @Autowired	
    public VideoController (VideoService vs, VideoWhitelistService vws, TagRepo tr, VideoRepo vr) {
        this.videoService = vs;
        this.videoWhitelistService = vws;
        this.tagRepo = tr;
        this.videoRepo = vr;
    }

    /**
     * returns a list of <code>Video</code> objects in the response body, determined by the tags specified in the URI
     * path in the form 'TagID:TagKey:TagValue', separating multiple tags by the '+' character. The handler method is
     * mapped to the path '/videos/tags/{videoTags}' and produces media of type application-json. The method formats the
     * passed path variables into <code>Tag</code> objects and passes this formatted list into the <code>VideoService</code>
     * <code>findAllVideosByTags</code> method. The returned list of <code>Video</code> objects is returned int the
     * response body with an HTTP status code of 200.
     *
     * @param crudeTags URI path variable in the form 'TagID:TagKey:TagValue'
     * @return list of <code>Video</code> objects
     */
    // Can convert the path variable formatting clause into a service method which can be called in both controllers to reduce clutter
    @GetMapping(path = "tags/{videoTags}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Video>> getVideosByTag(HttpServletRequest request, @PathVariable(name="videoTags") String crudeTags){
        logger.info("Received request for videos with tags: " + crudeTags);
        String[] arrTags = crudeTags.split("\\+");
        List<Tag> targetTags = new ArrayList<>();
        for (String tag: arrTags) {
            Tag tempTag = new Tag();
            String[] tagComponents = tag.split(":");
            tempTag.setTagID(Integer.parseInt(tagComponents[0]));
            tempTag.setType(tagComponents[1]);
            tempTag.setValue(tagComponents[2]);
            targetTags.add(tempTag);
        }
        logger.info("Retrieving target videos...");
        List <Video> targetVideos = videoService.findAllVideosByTags(targetTags);
        return new ResponseEntity<>(targetVideos, HttpStatus.OK);
    }

    /**
     * returns a list of all <code>Video</code> objects in the database in the response body. The handler method is
     * mapped to the URI '/videos/all/' and produces media type of application-json. The handler retrieves the list through
     * the <code>VideoService</code> <code>findAll</code> method.
     * @return list of all <code>Video</code> objects
     */
    //future iterations can add pagination to backend or front end
    @GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Video>> getAllVideos(HttpServletRequest request) {
        logger.info("Retrieving all videos...");
        List<Video> targetVideos = videoService.findAll();
        return new ResponseEntity<>(targetVideos, HttpStatus.OK);
    }

    /**
     * returns a list of all <code>Tag</code> objects in the database linked to a <code>Video</code> in the response
     * body. The handler method is mapped to the URI '/videos/available-tags/' and produces media type of application-json.
     * The handler retrieves the list through the <code>TagRepo</code> <code>findByNameIn</code> method. The tag keys are
     * determined by a list tagNames which cn be updated based on what keys exist in the database.
     * @return list of all <code>Video</code> objects
     */
    @GetMapping(path = "available-tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tag>> getAllVideoTags(HttpServletRequest request) {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("Topic");
        tagNames.add("Batch");
        logger.info("Retrieving all video tags with keys: " + tagNames +" ...");
        List<Tag> availableTags = tagRepo.findByTypeIn(tagNames);
        return new ResponseEntity<>(availableTags, HttpStatus.OK);
    }

    /**
     * returns a <code>Video</code> object in the response body, determined by the specified videoID in the URI path.
     * The handler method is mapped to the URI 'videos/id/{videoId}' and returns media type of application-json. The
     * target video is retrieved via the <code>VideoService</code> <code>findById</code> method, passing in the URI
     * path variable.
     * @param id target <code>Video</code>'s ID
     * @return target <code>Video</code> object
     */
    @GetMapping(path = "id/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Video> getVideoById(HttpServletRequest request, @PathVariable(name="videoId") int id) {
        logger.info("Retrieving target video with ID: " + id + " ...");
        Optional<Video> targetVideo = videoService.findById(id);
        return targetVideo.map(video -> new ResponseEntity<>(video, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    
    @PostMapping(path = "{videoId}/whitelist/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addToWhitelist(@PathVariable(name="videoId") int id, @RequestParam("json") String json) throws JsonMappingException, JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
    	List<User> userList = mapper.readValue(json, new TypeReference<List<User>>(){});
    	Optional<Video> targetVideo = videoService.findById(id);
    	targetVideo.ifPresent(video ->
    		videoWhitelistService.addUserToWhitelist((Video)video, userList)
    	);
    	String response = "";
    	if(targetVideo.isPresent()) {
    		response = "Successfully retrieved and added to the video.";
    	} else {
    		response = "Error, invalid note";
    		return new ResponseEntity<>(mapper.writeValueAsString(response), HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<>(mapper.writeValueAsString(response), HttpStatus.OK);
    }
}
