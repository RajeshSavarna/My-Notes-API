package com.mynotes.api.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mynotes.api.exception.BusinessException;
import com.mynotes.api.model.UserData;
import com.mynotes.api.pojo.UserPOJO;
import com.mynotes.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	UserService userService;

	@Autowired
	public UserController( UserService userService ) {
		this.userService = userService;
	}

	@Operation(description = "Create a new User", summary = "Create User", tags = "User")
	@PostMapping(path = "/create-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) 
	public UserData createUser(@RequestBody @Valid UserPOJO user) throws BusinessException {
		return userService.createUser(user);		
	}

	@Operation(description = "Get user by email", summary = "Get User", tags = "User")
	@GetMapping(path = "/get-user", produces = MediaType.APPLICATION_JSON_VALUE) 
	public UserData getUser(@RequestHeader("email") String email) throws BusinessException {
		return userService.getUser(email);		
	}

}
