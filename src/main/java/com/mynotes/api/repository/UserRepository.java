package com.mynotes.api.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mynotes.api.model.UserData;

@Repository
public interface UserRepository extends MongoRepository<UserData, String> {

	Optional<UserData> getUserDataByEmail(String email);
	
	@Query("{ 'id' : ?0 }")
    Optional<UserData> findById(String id);

}
