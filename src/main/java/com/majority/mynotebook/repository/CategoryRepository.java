package com.majority.mynotebook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.majority.mynotebook.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	public Optional<Category> findByName(String name);
}
