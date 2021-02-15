package com.revature.chronicle.services;

import com.revature.chronicle.daos.VideoRepo;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import com.revature.chronicle.models.Video;
import com.revature.chronicle.security.FirebaseInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service to handle business logic surrounding data access layer for videos
 */

@Service
public class VideoService {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseInitializer.class);
    @Autowired
    private VideoRepo videoRepo;

    public VideoService(VideoRepo videoRepo) {
        this.videoRepo = videoRepo;
    }

    /**
     * Finds all Videos that have the all of provided tags.
     * @param tags the tags provided by the user
     * @return a list of videos that have all tags
     */
    public List<Video> findAllVideosByTags(List<Tag> tags, User user){
        System.out.println("Entered service method");
        List<Video> desiredVideos = new ArrayList<>();
        int offset = 0;
        final int LIMIT = 50;
        do{
            //Query database for first 50 most recent results
            //Since date is a timestamp it should account for hours, mins, secs as well ensuring the order of the list
            List<Video> videos = videoRepo.findVideosWithOffsetAndLimit(offset,LIMIT);
            System.out.println(videos.size());

            //Check if videos is empty as no more records exist
            if(videos.size()>0){
                //Iterate through 50 results
            	System.out.println(videos);
                for(Video video:videos){
                    //Check to see if result has all passed in tags,if so add to desiredVideos
                    if(video.getTags().containsAll(tags)){
                    	if(user.getRole().equals("ROLE_ADMIN")) {
                    		logger.info("Adding video");
                    		desiredVideos.add(video);
                    	} else {
                    		if(!video.isPrivate()) {
                    			logger.info("Adding video");
                        		desiredVideos.add(video);
                    		} else {
	                    		for(User u : video.getWhitelist()) {
	                    			if(u.getUid().equals(user.getUid())) {
	                    				logger.info("Adding video");
	                            		desiredVideos.add(video);
	                            		break;
	                    			}
	                    		}
	                    		logger.warn("Not on video whitelist");
                    		}
                    	}
                    }
                    else{
                        logger.warn("Video not found");
                    }
                }
            }
            else{
                break;
            }
            offset+= videos.size();
        }
        while(desiredVideos.size() < 50 && desiredVideos.size()>0);

        //Find way to sort by return if it doesn't keep by recent order
        return desiredVideos;
    }

    public List<Video> findAll(User user) {
        try{
        	List<Video> desiredVideos = new ArrayList<>();
            int offset = 0;
            final int LIMIT = 50;
            do{
                //Query database for first 50 most recent results
                //Since date is a timestamp it should account for hours, mins, secs as well ensuring the order of the list
                List<Video> videos = videoRepo.findVideosWithOffsetAndLimit(offset,LIMIT);
                System.out.println(videos.size());

                //Check if videos is empty as no more records exist
                if(videos.size()>0){
                    //Iterate through 50 results
                    for(Video video:videos){
                        //Check to see if result has all passed in tags,if so add to desiredVideos
                    	if(user.getRole().equals("ROLE_ADMIN")) {
                    		logger.info("Adding video");
                    		desiredVideos.add(video);
                    	} else {
                    		if(!video.isPrivate()) {
                    			logger.info("Adding video");
                        		desiredVideos.add(video);
                    		} else {
	                    		for(User u : video.getWhitelist()) {
	                    			if(u.getUid().equals(user.getUid())) {
	                    				logger.info("Adding video");
	                            		desiredVideos.add(video);
	                            		break;
	                    			}
	                    		}
	                    		logger.warn("Not on video whitelist");
                    		}
                    	}
                    }
                }
                else{
                    break;
                }
                offset+= videos.size();
            }
            while(desiredVideos.size() < 50 && desiredVideos.size()>0);

            //Find way to sort by return if it doesn't keep by recent order
            return desiredVideos;
        }
        catch(Exception e) {
            logger.warn(e.getMessage());
            return new ArrayList<Video>();
        }
    }

    public Video findById(int id){
        try{
            return videoRepo.findById(id).get();
        }
        catch (Exception e){
            logger.warn(e.getMessage());
            return null;
        }
    }

    public boolean save(Video video) {
        System.out.println("Saving video");
        try{
            videoRepo.save(video);
            logger.info("Saved");
            return true;
        }
        catch(Exception e) {
            logger.warn(e.getMessage()); //replace with logging
            return false;
        }
    }
    
    public boolean update(Video video, User user) {
        try {
        	if(user.getRole().equals("ROLE_ADMIN")) {
	            videoRepo.save(video);
	            return true;
        	} else if(user.getUid().equals(video.getUser())) {
        		videoRepo.save(video);
	            return true;
        	} else {
        		return false;
        	}
        }
        catch(Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }

    public boolean deleteVideo(Video video, User user) {
        try{
        	if(user.getRole().equals("ROLE_ADMIN")) {
	            videoRepo.save(video);
	            return true;
        	} else if(user.getUid().equals(video.getUser())) {
        		videoRepo.save(video);
	            return true;
        	} else {
        		return false;
        	}
        }
        catch(Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }
}
