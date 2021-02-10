//package com.revature.chronicle.services;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.revature.chronicle.daos.NoteWhitelistRepo;
//import com.revature.chronicle.models.Note;
//import com.revature.chronicle.models.User;
//import com.revature.chronicle.security.FirebaseInitializer;
//
//@Service
//public class NoteWhitelistService {
//	private static final Logger logger = LoggerFactory.getLogger(FirebaseInitializer.class);
//    
//	@Autowired
//    private NoteWhitelistRepo noteWhitelistRepo;
//
//    public NoteWhitelistService(NoteWhitelistRepo noteWhitelistRepo) {
//        this.noteWhitelistRepo = noteWhitelistRepo;
//    }
//    
//	public void addUserToWhitelist(Note note, List<User> users) {
//    	for(User user : users) {
//    		this.addUserToWhitelist(note, user);
//    	}
//    }
//    public void addUserToWhitelist(Note note, User user) {
//    	logger.info("Adding user to whitelist" );
//    	noteWhitelistRepo.addUser(note.getId(), user.getUserID());
//    }
//}
