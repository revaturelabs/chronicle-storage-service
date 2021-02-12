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
	//WHITELIST QUERY STRINGS
	public static final String ADD_USER = "INSERT INTO video_whitelist VALUES ? ?";
	public static final String GET_USERS = "SELECT * FROM video_whitelist WHERE video_id = ?";
	
      /**
       * Finds videos with an offset and limit used for paging
       * @param offset where to begin
       * @param limit how many records should be returned
       * @return the videos
       */
	//VIDEO QUERIES
    @Query(value = "SELECT * FROM video v LEFT JOIN video_whitelist w ON v.video_id = w.video_id Where w.user_id = ?1 order by v.date asc offset ?2 fetch next ?3 rows only",nativeQuery = true)
    List<Video> findVideosWithOffsetAndLimit(String uid, int offset,int limit);
      
    //WHITELIST QUERIES
    @Query(value = ADD_USER, nativeQuery = true)
    void addUser(int noteId, String userId);
  	
  	@Query(value = GET_USERS, nativeQuery = true)
    List<String> getUsers(int noteId);
  	
}
