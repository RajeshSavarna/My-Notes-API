package com.mynotes.api.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mynotes.api.exception.BusinessException;
import com.mynotes.api.logging.LogAround;
import com.mynotes.api.model.NameDetails;
import com.mynotes.api.model.UserData;
import com.mynotes.api.pojo.UserPOJO;
import com.mynotes.api.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserServiceImpl implements UserService {

	private static final String USER_NOT_FOUND = "User not found";
	
	UserRepository userRepo;

	@Autowired
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	/**
	 * For creating a new user in the collection
	 */
	@LogAround
	@Override
	public UserData createUser(UserPOJO user) throws BusinessException {	
		try {
			NameDetails nameDetails = new NameDetails(user.getNameDetails().getSalutation(), user.getNameDetails().getFirstName(), 
					user.getNameDetails().getMiddleName(), user.getNameDetails().getLastName());
			UserData userData = userRepo.save(new UserData(null, nameDetails, user.getMobile(), user.getEmail(), 
					user.getGender(), user.getDob(), new ArrayList<>()));
			log.info("User added : {}", userData);
			return userData;
		} catch (Exception ex) {
			throw new BusinessException("USER-2111", null != ex.getCause() ? ex.getCause().toString() : ex.getClass().getSimpleName(), 500, ex);
		}
	}

	/**
	 * Fetch userData from the collection by email id
	 */
	@LogAround
	@Override
	public UserData getUser(String email) throws BusinessException {
		try {
			Optional<UserData> optionalData = userRepo.getUserDataByEmail(email);
			if (!optionalData.isPresent()) throw new BusinessException("USER-2121", USER_NOT_FOUND, 404, null);
			log.info("User fetched -> email - {} response : {}", email, optionalData.get());
			return optionalData.get();
		} catch (BusinessException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new BusinessException("USER-2122", null != ex.getCause() ? ex.getCause().toString() : ex.getClass().getSimpleName(), 500, ex);
		}
	}

}
