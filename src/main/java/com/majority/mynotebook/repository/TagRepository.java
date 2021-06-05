package com.majority.mynotebook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.majority.mynotebook.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

	public Optional<Tag> findByTag(String tag);
}
