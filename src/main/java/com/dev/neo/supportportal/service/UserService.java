package com.dev.neo.supportportal.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dev.neo.supportportal.domain.User;
import com.dev.neo.supportportal.exception.domain.EmailExistException;
import com.dev.neo.supportportal.exception.domain.EmailNotFoundException;
import com.dev.neo.supportportal.exception.domain.UserNotFoundException;
import com.dev.neo.supportportal.exception.domain.UsernameExistException;

public interface UserService
{
	User register(String firstName, String lastName, String userName, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;
	
	List<User> getUsers();
	
	User findByUserName(String userName);
	
	User findUserByEmail(String email);
	
	User addUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
	
	User updateUser(String currentUsername, String newFirstname, String newLastname, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
	
	void deleteUser(Long id);
	
	void resetPassword(String email) throws EmailNotFoundException;
	
	User updateProfileImage(String username, MultipartFile profileFile) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
}
