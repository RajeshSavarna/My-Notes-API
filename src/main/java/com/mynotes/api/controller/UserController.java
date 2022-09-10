package com.mynotes.api.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mynotes.api.exception.BusinessException;
import com.mynotes.api.model.UserData;
import com.mynotes.api.pojo.AuthenticateRequest;
import com.mynotes.api.pojo.UserPOJO;
import com.mynotes.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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

	@SecurityRequirement(name = "my-notes-api")
	@Operation(description = "Get user", summary = "Get User", tags = "User")
	@GetMapping(path = "/get-user", produces = MediaType.APPLICATION_JSON_VALUE) 
	public UserData getUser(
			/* @RequestHeader("Authorization") String authToken, */ HttpServletRequest request) throws BusinessException {
		return userService.getUser(request.getHeader("Authorization"));		
	}

	@Operation(description = "Authenticate user and generate token", summary = "Generate token", tags = "User")
	@PostMapping(path = "/user-authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) 
	public Object authenticate(@RequestBody @Valid AuthenticateRequest req) throws BusinessException { 
		return userService.userAuthenticate(req);
	}

}
