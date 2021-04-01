package com.revature.chronicle.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;

import java.util.Objects;

@RestController
@RequestMapping(value = "/firebase")
public class FirebaseController {		
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
        		item.put("uid", user.getUid());
        		item.put("email", user.getEmail());
        		item.put("displayName", user.getDisplayName());
        		response.add(item);
        	}
        	page = page.getNextPage();
        }
		
		return response;
	}
	
	
	/**
	 * Assigns a role to a newly registered user
	 * @param req
	 * @param userId
	 * @param resp
	 * @throws FirebaseAuthException
	 */
	
	@PutMapping(path="/newRegister")
	public void setUser(@RequestParam("userId") String userId, @RequestParam("role") String role) throws FirebaseAuthException {
		Object rolesObject = FirebaseAuth.getInstance().getUser(userId).getCustomClaims().get("role");
		if(Objects.isNull(rolesObject)) {
			//Firebase already has the userID in it's user table
			//the following update the user with role or roles
			ArrayList<String> rolesList = new ArrayList<>();
			switch(role) {
				case "trainer":
					rolesList.add("ROLE_TRAINER");
					break;
				case "editor":
					rolesList.add("ROLE_EDITOR");
				default:
					rolesList.add("ROLE_TRAINER");	
					break;
			}
			Map<String, Object> claims = new HashMap<>();
			claims.put("role", rolesList);
			//Update done with this line
			FirebaseAuth.getInstance().setCustomUserClaims(userId, claims);			
		}
	}	
	
	
	/**
	 * Assigns a role to a newly registered user
	 * @param req
	 * @param userId
	 * @param resp
	 * @throws FirebaseAuthException
	 */
	@PutMapping(path="/register/{userId}")
	public void setUser(HttpServletRequest req, @PathVariable(name="userId") String userId, HttpServletResponse resp) throws FirebaseAuthException {
		System.out.println(userId);
		Object rolesObject = FirebaseAuth.getInstance().getUser(userId).getCustomClaims().get("role");
		if(Objects.isNull(rolesObject)) {
			//Firebase already has the userID in it's user table
			//the following update the user with role or roles
			ArrayList<String> rolesList = new ArrayList<>();
			rolesList.add("ROLE_TRAINER");
			rolesList.add("ROLE_EDITOR");
			Map<String, Object> claims = new HashMap<>();
			claims.put("role", rolesList);
			//Update done with this line
			FirebaseAuth.getInstance().setCustomUserClaims(userId, claims);
			
			//the following code get all infomation fro userId
			UserRecord ur = FirebaseAuth.getInstance().getUser(userId);
			//The string of uid(UserId)
			String uid = ur.getUid();
			//user email
			String email = ur.getEmail(); 

			Object roleObject = ur.getCustomClaims().get("role");
			System.out.println(ur.getCustomClaims().get("role"));
			
			List<String> roles = (List)ur.getCustomClaims().get("role");
			for(String r:roles) {
				System.out.println(r);
			}
			
			System.out.println(email);
		}
	}
}
