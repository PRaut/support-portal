package com.dev.neo.supportportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/user")
public class UserController
{

	@GetMapping(value = "/home")
	public String showUser() {
		return "Welcome ...";
	}
}
