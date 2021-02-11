package com.revature.chronicle.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;

@RestController
@RequestMapping(path="/firebase")
public class FirebaseController {
	
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
        		item.put("uid", user.getUid());
        		item.put("email", user.getEmail());
        		item.put("displayName", user.getDisplayName());
        		response.add(item);
        	}
        	page = page.getNextPage();
        }
		
		return response;
	}
}
