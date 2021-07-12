package com.majority.mynotebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.majority.mynotebook.config.security.TokenService;
import com.majority.mynotebook.model.Annotation;
import com.majority.mynotebook.model.Category;
import com.majority.mynotebook.model.Tag;
import com.majority.mynotebook.repository.CategoryRepository;
import com.majority.mynotebook.repository.TagRepository;
import com.majority.mynotebook.repository.UserRepository;

@Service
public class AnnotationService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	public void insertCategory(Annotation annotation, String categoryName) {
		Optional<Category> optional = categoryRepository.findByName(categoryName);

		if (optional.isPresent()) {
			annotation.setCategory(optional.get());
		} else {
			Category category = new Category();
			category.setName(categoryName);
			annotation.setCategory(category);
			
			categoryRepository.save(category);
		}
	}

	public void insertTags(Annotation annotation, List<String> tags) {
		for (String tagName : tags) {

			Optional<Tag> optionalTag = tagRepository.findByTag(tagName);

			if (optionalTag.isPresent()) {
				annotation.addTag(optionalTag.get());
			} else {
				Tag tag = new Tag();
				tag.setTag(tagName);
				annotation.addTag(tag);
				
				tagRepository.save(tag);
			}
		}

	}

	public void insertUserId(Annotation annotation) {
		Long userId = getUserId();

		annotation.setUserId(userId);
	}
	
	public Long getUserId() {
		String username = tokenService.getUsername();

		Long userId = userRepository.findByUsername(username).get().getId();
		
		return userId;
	}

}
