package com.revature.chronicle.interceptors;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.revature.chronicle.models.User;

/*
 * Interceptor for Firebase Authentication and pre-Controller User construction
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor{
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean handleRequest = false;
		String authorization = request.getHeader("Authorization");
		String bearerToken = null;
		if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
			bearerToken = authorization.substring(7, authorization.length());
		}
		
		if(bearerToken == null) {
			logger.info("No JWT Token present, ignoring request");
			response.setStatus(401);
		} else {
			User user = new User();
			try {
				FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(bearerToken, true);
				user.setUid(decodedToken.getUid());
        
				Object rolesObject = decodedToken.getClaims().get("role");
				if(rolesObject instanceof ArrayList) {
					ArrayList<String> rolesList = (ArrayList<String>) rolesObject;
					if(rolesList.contains("ROLE_ADMIN")) {
						user.setRole("ROLE_ADMIN");
					}
				}
				request.setAttribute("user", user);
				handleRequest = true;
			}catch (FirebaseException e){
				logger.warn(e.getMessage());
			}
		}
		return handleRequest;
	}
}

	
