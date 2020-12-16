package com.dev.neo.supportportal.service;

import java.util.List;

import com.dev.neo.supportportal.domain.User;
import com.dev.neo.supportportal.exception.domain.EmailExistException;
import com.dev.neo.supportportal.exception.domain.UserNotFoundException;
import com.dev.neo.supportportal.exception.domain.UsernameExistException;

public interface UserService
{
	User register(String firstName, String lastName, String userName, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;
	
	List<User> getUsers();
	
	User findByUserName(String userName);
	
	User findUserByEmail(String email);
}
