package com.revature.chronicle.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.ListUsersPage;

@RestController
public class FirebaseController {	
	@GetMapping(value = "/")
	public void authenticateUser(HttpServletRequest req, HttpServletResponse resp) throws FirebaseAuthException {
		String idToken = "";
		FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(idToken);
		
	}
	
	@GetMapping(value ="/user")
	public void getUsers(HttpServletRequest req, HttpServletResponse resp) throws FirebaseAuthException {
		 ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
         while (page != null) {
         	for (ExportedUserRecord user : page.getValues()) {
         		System.out.println("User: " + user.getUid() + " | " + user.getDisplayName() + " | " + user.getCustomClaims());
         	}
         	page = page.getNextPage();
         }
	}
}
