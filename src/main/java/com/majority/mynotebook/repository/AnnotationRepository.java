package com.majority.mynotebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.majority.mynotebook.model.Annotation;

public interface AnnotationRepository extends JpaRepository<Annotation, Long>{

}
