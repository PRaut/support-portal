package com.dev.neo.supportportal.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dev.neo.supportportal.domain.User;
import com.dev.neo.supportportal.domain.UserPrincipal;
import com.dev.neo.supportportal.repository.UserRepository;
import com.dev.neo.supportportal.service.UserService;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService
{
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userRepository.findUserByUsername(username);
		if(user == null) {
			LOGGER.error("User not found for username: "+ username);
			throw new UsernameNotFoundException("User not found for username : "+ username);
		}else {
			user.setLastLoginDateDisplay(user.getLastLoginDate());
			user.setLastLoginDate(new Date());
			userRepository.save(user);
			
			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info("Returning found user with username: "+ username);
			return userPrincipal;
		}
		
	}

}
