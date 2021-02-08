package com.revature.chronicle.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean result = false;
		
		String bearerToken = null;
		String authorization = request.getHeader("Authorization");
		if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
			bearerToken = authorization.substring(7, authorization.length());
		}
		
		if(bearerToken == null) {
			response.setStatus(401);
		} else {
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(bearerToken);
			System.out.println("\n || " + decodedToken.getClaims().get("role") + "\n");
			result = true;
		}
		return result;
	}
}

	
