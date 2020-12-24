package com.revature.chronicle.daos;

import com.revature.chronicle.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data access object interface for video data
 */
public interface VideoRepo extends JpaRepository<Video, Integer> {
    /*
    public List<Video> getVideos();
    public Video getVideoById(int id);
    public boolean addVideo(Video video);
    public boolean updateVideo(Video video);
    public boolean deleteVideoById(int id);*/
}
