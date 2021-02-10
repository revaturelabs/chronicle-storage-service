package com.revature.chronicle.daos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.chronicle.models.Video;

@Repository
public interface NoteWhitelistRepo {
	public static final String ADD_USER = "INSERT INTO note_whitelist VALUES ? ?";
	public static final String GET_USERS = "SELECT * FROM note_whitelist WHERE note_id = ?";
	
	@Query(value = ADD_USER, nativeQuery = true)
    List<Video> addUser(int noteId, String userId);
	
	@Query(value = GET_USERS, nativeQuery = true)
    List<Video> getUsers(int noteId);
}
