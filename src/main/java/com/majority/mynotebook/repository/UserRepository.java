package com.majority.mynotebook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.majority.mynotebook.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{

	Optional<UserModel> findByUsername(String username);
	
}
