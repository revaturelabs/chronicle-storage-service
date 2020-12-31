package com.revature.chronicle.services;

import com.revature.chronicle.daos.VideoRepo;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service to handle business logic surrounding data access layer for videos
 */

@Service
public class VideoService {
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
    public List<Video> findAllVideosByTags(List<Tag> tags){
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
                for(Video video:videos){
                    //Check to see if result has all passed in tags,if so add to desiredVideos
                    if(video.getVideoTags().containsAll(tags)){
                        System.out.println("Adding video");
                        desiredVideos.add(video);
                    }
                    else{
                        System.out.println("Video not found");
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

    public List<Video> findAll() {
        try{
            return videoRepo.findAll();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public Optional<Video> findById(int id){
        try{
            return videoRepo.findById(id);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Video getVideoById(int id) {
        try{
            Optional<Video> v = videoRepo.findById(id);
            if (v.isPresent()) {
                return v.get();
            }
            else {
                return new Video();
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return new Video();
        }
    }

    public boolean save(Video video) {
        System.out.println("Saving video");
        try{
            videoRepo.save(video);
            System.out.println("Saved");
            return true;
        }
        catch(Exception e) {
            System.out.println(e.getMessage()); //replace with logging
            return false;
        }
    }

    public boolean updateVideo(Video video) {
        try{
            videoRepo.save(video); //update uses the jpa repo method as save
            return true;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteVideo(Video video) {
        try{
            videoRepo.delete(video);
            return true;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

//    //this method is to test the service method (use the repo method instead!)
//    public List<Video> findVideosByTagService(Tag tag) { //HQL (hibernate should have mapped the relationships!)
//        try{
//            return videoRepo.findVideosByTag(tag); //update uses the jpa repo method as save
//        }
//        catch(Exception e) {
//            System.out.println(e.getMessage());
//            return new ArrayList<>();
//        }
//    }

//    List<Video> findByTags(Set<Tag> tags) {
//        List<Video> videoList;
//        videoList = new ArrayList<>();
//        return videoList;
//    }
}
