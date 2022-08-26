package com.mynotes.api.service;

import org.springframework.stereotype.Service;

import com.mynotes.api.exception.BusinessException;
import com.mynotes.api.model.UserData;
import com.mynotes.api.pojo.UserPOJO;

@Service
public interface UserService {
	
	public UserData createUser(UserPOJO user) throws BusinessException;

	public UserData getUser(String email) throws BusinessException;
}
