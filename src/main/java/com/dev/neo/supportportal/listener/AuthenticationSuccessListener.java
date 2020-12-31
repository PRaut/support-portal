package com.dev.neo.supportportal.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.dev.neo.supportportal.domain.User;
import com.dev.neo.supportportal.domain.UserPrincipal;
import com.dev.neo.supportportal.service.LoginAttemptService;

@Component
public class AuthenticationSuccessListener
{
	@Autowired
	private LoginAttemptService loginAttemptService;
	
	@EventListener
	public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if(principal instanceof UserPrincipal) {
			UserPrincipal userPrincipal  = (UserPrincipal) event.getAuthentication().getPrincipal();
			loginAttemptService.evictUserFromLoginAttemptCache(userPrincipal.getUsername());
		}
	}
}
