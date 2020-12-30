package com.revature.chronicle.daos;

import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data access object interface for video data
 */
@Repository
public interface VideoRepo extends JpaRepository<Video, Integer> {
//    Needs custom implementation
      @Query(value = "SELECT * FROM video WHERE tag = ?", nativeQuery = true) //@Query annotation is used to perform complex/custom queries, nativeQuery means plain SQL
      List<Video> findVideosByTag(Tag tag); //TODO: GOAL! of this method is to find videos containing a specific tag

      //@Query(value = "SELECT * FROM videos", nativeQuery = true)
      //List<Video> findVideoByTags(Tag tag); //TODO: GOAL! of this method is to find videos containing multiple specific tags

//    JPA Implementations
//    Optional<Video> findById(Integer id);   --> public Video getVideoById(int id);
//    List<Video> findAll();                  --> public List<Video> getVideos();
//    void save();                            --> public boolean addVideo(Video video);/public boolean updateVideo(Video video);
//    void delete();                          --> public boolean deleteVideo(Video video);
}
