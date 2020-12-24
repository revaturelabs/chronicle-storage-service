package com.revature.chronicle.daos;

import com.revature.chronicle.models.Video;

import java.util.List;

/**
 * Data access object interface for video data
 */
public interface VideoDAO {
    public List<Video> getVideos();
    public Video getVideoById(int id);
    public boolean addVideo(Video video);
    public boolean updateVideo(Video video);
    public boolean deleteVideoById(int id);
}
