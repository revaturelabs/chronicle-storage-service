package com.revature.chronicle.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.chronicle.models.User;
import com.revature.chronicle.models.Video;

@Repository
public interface VideoWhitelistRepo extends JpaRepository<User, Integer> {
	public static final String ADD_USER = "INSERT INTO video_whitelist VALUES ? ?";
	public static final String GET_USERS = "SELECT * FROM video_whitelist WHERE video_id = ?";
	
	@Query(value = ADD_USER, nativeQuery = true)
    void addUser(int noteId, String userId);
	
	@Query(value = GET_USERS, nativeQuery = true)
    List<Video> getUsers(int noteId);
}
