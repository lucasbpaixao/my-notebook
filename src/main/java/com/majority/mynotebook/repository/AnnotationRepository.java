package com.majority.mynotebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.majority.mynotebook.model.Annotation;

public interface AnnotationRepository extends JpaRepository<Annotation, Long>{
	public List<Annotation> findByStatus(String status);
	
	public List<Annotation> findByUserId(Long userId);
}
