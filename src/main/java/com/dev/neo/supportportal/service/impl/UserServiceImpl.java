package com.dev.neo.supportportal.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dev.neo.supportportal.domain.User;
import com.dev.neo.supportportal.domain.UserPrincipal;
import com.dev.neo.supportportal.enumeration.Role;
import com.dev.neo.supportportal.exception.domain.EmailExistException;
import com.dev.neo.supportportal.exception.domain.UserNotFoundException;
import com.dev.neo.supportportal.exception.domain.UsernameExistException;
import com.dev.neo.supportportal.repository.UserRepository;
import com.dev.neo.supportportal.service.UserService;

import static com.dev.neo.supportportal.constant.UserImplContant.*;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService
{
	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userRepository.findUserByUserName(username);
		if(user == null)
		{
			LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
			throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
		}
		else
		{
			user.setLastLoginDateDisplay(user.getLastLoginDate());
			user.setLastLoginDate(new Date());
			userRepository.save(user);

			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info(FOUND_USER_WITH_USERNAME + username);
			return userPrincipal;
		}

	}

	@Override
	public User register(String firstName, String lastName, String userName, String email) throws UserNotFoundException, UsernameExistException, EmailExistException
	{
		validateNewUsernameAndEmail(StringUtils.EMPTY, userName, email);
		User user = new User();
		user.setUserId(generateUserId());
		
		String password = generatePassword();
		String encodedPassword = encodePassword(password);
		user.setPassword(encodedPassword);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserName(userName);
		user.setEmail(email);
		user.setJoinDate(new Date());
		user.setActive(true);
		user.setNotLocked(true);
		user.setRole(Role.ROLE_USER.name());
		user.setAuthorities(Role.ROLE_USER.getAuthorities());
		user.setProfileImageUrl(getTemporaryProfileImageUrl());
		userRepository.save(user);
		LOGGER.info("New user password: "+ password);
		return user;
	}

	private String getTemporaryProfileImageUrl()
	{
		// return http://localhost:8081/
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_PROFILE_IMAGE_PATH).toUriString();
	}

	private String encodePassword(String password)
	{
		return passwordEncoder.encode(password);
	}

	private String generatePassword()
	{
		return RandomStringUtils.randomAlphanumeric(10);
	}

	private String generateUserId()
	{
		return RandomStringUtils.randomNumeric(10);
	}

	private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String email) throws UserNotFoundException, UsernameExistException, EmailExistException
	{

		User userByNewUsername = userRepository.findUserByUserName(newUsername);
		User userByNewEmail = userRepository.findUserByEmail(email);
		
		if(StringUtils.isNotBlank(currentUsername))
		{
			User currentUser = userRepository.findUserByUserName(currentUsername);
			if(currentUser == null)
			{
				throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
			}
			if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId()))
			{
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}
			if(userByNewEmail != null && !currentUser.getEmail().equals(userByNewEmail.getEmail()))
			{
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			return currentUser;
		}
		else
		{
			if(userByNewUsername != null)
			{
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}
			if(userByNewEmail != null)
			{
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			return null;
		}
	}

	@Override
	public List<User> getUsers()
	{
		return userRepository.findAll();
	}

	@Override
	public User findByUserName(String userName)
	{
		return userRepository.findUserByUserName(userName);
	}

	@Override
	public User findUserByEmail(String email)
	{
		return userRepository.findUserByEmail(email);
	}

}
