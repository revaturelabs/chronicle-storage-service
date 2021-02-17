package com.revature.chronicle.daos;

import com.revature.chronicle.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access object interface for video data
 */
@Repository
public interface VideoRepo extends JpaRepository<Video, Integer> {

      /**
       * Finds videos with an offset and limit used for paging
       * @param offset where to begin
       * @param limit how many records should be returned
       * @return the videos
       */
	//VIDEO QUERIES
    @Query(value = "SELECT * FROM video v order by v.date asc offset ?1 fetch next ?2 rows only",nativeQuery = true)
    List<Video> findVideosWithOffsetAndLimit(int offset,int limit);
    
}
