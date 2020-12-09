package com.dev.neo.supportportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.neo.supportportal.exception.domain.ExceptionHandling;
import com.dev.neo.supportportal.exception.domain.UserNotFoundException;

@RestController
@RequestMapping(value = "/user")
public class UserController extends ExceptionHandling
{

	@GetMapping(value = "/home")
	public String showUser() throws UserNotFoundException{
//		return "Welcome ...";
		throw new UserNotFoundException("User does not exist");
	}
}
