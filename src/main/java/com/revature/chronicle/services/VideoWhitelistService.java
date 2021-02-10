//package com.revature.chronicle.services;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.revature.chronicle.daos.VideoWhitelistRepo;
//import com.revature.chronicle.models.Video;
//import com.revature.chronicle.models.User;
//import com.revature.chronicle.security.FirebaseInitializer;
//
//@Service
//public class VideoWhitelistService {
//	private static final Logger logger = LoggerFactory.getLogger(FirebaseInitializer.class);
//    
//	@Autowired
//    private VideoWhitelistRepo videoWhitelistRepo;
//
//    public VideoWhitelistService(VideoWhitelistRepo videoWhitelistRepo) {
//        this.videoWhitelistRepo = videoWhitelistRepo;
//    }
//    
//	public void addUserToWhitelist(Video video, List<User> users) {
//    	for(User user : users) {
//    		this.addUserToWhitelist(video, user);
//    	}
//    }
//    public void addUserToWhitelist(Video video, User user) {
//    	logger.info("Adding user to video whitelist" );
//    	videoWhitelistRepo.addUser(video.getId(), user.getUserID());
//    }
//}
