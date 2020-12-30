package com.revature.chronicle.daos;

import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//@Component
@Service
public class VideoService {
    @Autowired
    VideoRepo videoRepo;

    //wait so we don't this constructor injection from the videoRepo for the videoService anymore?
    VideoService (VideoRepo videoRepo) {
        this.videoRepo = videoRepo;
    }

    List<Video> getVideos() {
        try{
            return videoRepo.findAll();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    Video getVideoById(int id) {
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

    boolean addVideo(Video video) {
        try{
            videoRepo.save(video);
            return true;
        }
        catch(Exception e) {
            System.out.println(e.getMessage()); //replace with logging
            return false;
        }
    }

    boolean updateVideo(Video video) {
        try{
            videoRepo.save(video); //update uses the jpa repo method as save
            return true;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    boolean deleteVideo(Video video) {
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
