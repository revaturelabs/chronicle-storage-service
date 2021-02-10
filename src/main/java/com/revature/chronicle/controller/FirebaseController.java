package com.revature.chronicle.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.ListUsersPage;

@RestController
@RequestMapping(value = "/firebase")
public class FirebaseController {	
	
	@GetMapping(value = "/authenticate")
	public void authenticateUser(HttpServletRequest req, HttpServletResponse resp) throws FirebaseAuthException {
		String idToken = "";
		FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(idToken);
		
	}
	
	//Developer Testing Only
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
	
	/**
	 * Method to get all users from firebase to send to front end for display purposes.
	 * @return a JSON Element that contains the UID, Email, and Display Name from firebase.
	 */
	
	@GetMapping(path = "")
	public JSONArray getUsers() throws FirebaseAuthException {
		ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
		JSONArray response = new JSONArray();
		
		while (page != null) {
        	for (ExportedUserRecord user : page.getValues()) {
        		JSONObject item = new JSONObject();
        		item.put("UID", user.getUid());
        		item.put("Email", user.getEmail());
        		item.put("Display Name", user.getDisplayName());
        		response.add(item);
        	}
        	page = page.getNextPage();
        }
		
		return response;
	}
}
