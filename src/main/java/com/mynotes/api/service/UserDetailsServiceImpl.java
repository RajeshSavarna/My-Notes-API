package com.mynotes.api.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mynotes.api.exception.BusinessException;
import com.mynotes.api.model.UserData;
import com.mynotes.api.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	

	private static final String USER_NOT_FOUND = "User not found";
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			try {
				Optional<UserData> optionalData = userRepo.getUserDataByEmail(username);
				if (!optionalData.isPresent()) throw new BusinessException("USER-0000", USER_NOT_FOUND, 404, null);
				UserData userData = optionalData.get();
				
				return new User(userData.getEmail(), userData.getPassword() , new ArrayList<>());
			} catch (BusinessException e) {
				throw new UsernameNotFoundException(USER_NOT_FOUND);
			}
	}
}
