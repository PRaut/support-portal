package com.dev.neo.supportportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.neo.supportportal.constant.SecurityConstant;
import com.dev.neo.supportportal.domain.User;
import com.dev.neo.supportportal.domain.UserPrincipal;
import com.dev.neo.supportportal.exception.domain.EmailExistException;
import com.dev.neo.supportportal.exception.domain.ExceptionHandling;
import com.dev.neo.supportportal.exception.domain.UserNotFoundException;
import com.dev.neo.supportportal.exception.domain.UsernameExistException;
import com.dev.neo.supportportal.service.UserService;
import com.dev.neo.supportportal.utility.JWTTokenProvider;

@RestController
@RequestMapping(path = { "/", "/user" })
public class UserController extends ExceptionHandling
{

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	@PostMapping(value = "/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException{
		User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUserName(), user.getEmail());
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user){
		authenticate(user.getUserName(), user.getPassword()); // it validates entered Username, Password (if correct or not)
		User loginUser = userService.findByUserName(user.getUserName());
		UserPrincipal userPrincipal = new UserPrincipal(loginUser);
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal); // returns JWT Token with header
		return new ResponseEntity<User>(loginUser, jwtHeader, HttpStatus.OK);
	}

	private HttpHeaders getJwtHeader(UserPrincipal userPrincipal)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(userPrincipal));
		return headers;
	}

	private void authenticate(String userName, String password)
	{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
	}
}
