package com.dev.neo.supportportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.neo.supportportal.domain.User;
import com.dev.neo.supportportal.exception.domain.EmailExistException;
import com.dev.neo.supportportal.exception.domain.ExceptionHandling;
import com.dev.neo.supportportal.exception.domain.UserNotFoundException;
import com.dev.neo.supportportal.exception.domain.UsernameExistException;
import com.dev.neo.supportportal.service.UserService;

@RestController
@RequestMapping(path = { "/", "/user" })
public class UserController extends ExceptionHandling
{

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException{
		User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUserName(), user.getEmail());
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
}
