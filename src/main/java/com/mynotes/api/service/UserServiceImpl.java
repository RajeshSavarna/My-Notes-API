package com.mynotes.api.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.mynotes.api.configuration.JwtUtilsClass;
import com.mynotes.api.exception.BusinessException;
import com.mynotes.api.logging.LogAround;
import com.mynotes.api.model.NameDetails;
import com.mynotes.api.model.UserData;
import com.mynotes.api.pojo.AuthenticateRequest;
import com.mynotes.api.pojo.UserPOJO;
import com.mynotes.api.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserServiceImpl implements UserService {

	@Value("${app.auth.ttl}")
	private long ttl;
	
	private static final String USER_NOT_FOUND = "User not found";
	
	UserRepository userRepo;
	
	BCryptPasswordEncoder bCrypt;
	
	AuthenticationManager authenticationManager;
	
	JwtUtilsClass jwtUtils;
	
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	public UserServiceImpl(UserRepository userRepo, BCryptPasswordEncoder bCrypt, AuthenticationManager authenticationManager,
			JwtUtilsClass jwtUtils, UserDetailsServiceImpl userDetailsService) {
		this.userRepo = userRepo;
		this.bCrypt = bCrypt;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
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
					bCrypt.encode(user.getPassword()), user.getGender(), user.getDob(), new ArrayList<>()));
			userData.setPassword(null);
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
	public UserData getUser(String authToken) throws BusinessException {
		try {
			String userId = jwtUtils.extractUserId(authToken.substring(7));
			Optional<UserData> optionalData = userRepo.findById(userId);
			if (!optionalData.isPresent()) throw new BusinessException("USER-2121", USER_NOT_FOUND, 404, null);
			optionalData.get().setPassword(null);
			log.info("User fetched -> email - {} response : {}", userId, optionalData.get());
			return optionalData.get();
		} catch (BusinessException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new BusinessException("USER-2122", null != ex.getCause() ? ex.getCause().toString() : ex.getClass().getSimpleName(), 500, ex);
		}
	}
	
	/**
	 * Authenticate the user and generate a new jwt auth token
	 */
	@LogAround
	@Override
	public Object userAuthenticate(AuthenticateRequest req) throws BusinessException {
		try {
						
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
			);
			
//			final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getEmail());
			
			UserData userDetails = userRepo.getUserDataByEmail(req.getEmail()).get();
			
			HashMap<String, String> tokenRes = new HashMap<>();
			tokenRes.put("status", "CREATED");
			tokenRes.put("authToken", jwtUtils.generateToken(userDetails));
			tokenRes.put("ttlMinutes", TimeUnit.MILLISECONDS.toMinutes(ttl) + " minutes");
			tokenRes.put("createdAt", Date.from(Instant.now()).toString());
			
			return tokenRes;
			
		} catch (BadCredentialsException ex) {
			throw new BusinessException("USER-2131", "Authentication failed. Please check your email and password.", 401, ex);
		} catch (Exception ex) {
			throw new BusinessException("USER-2132", null != ex.getCause() ? ex.getCause().toString() : ex.getClass().getSimpleName(), 500, ex);
		}
	}
}
