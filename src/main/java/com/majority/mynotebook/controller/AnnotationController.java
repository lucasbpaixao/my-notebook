package com.majority.mynotebook.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.majority.mynotebook.dto.AnnotationDto;
import com.majority.mynotebook.model.Annotation;
import com.majority.mynotebook.model.Category;
import com.majority.mynotebook.repository.AnnotationRepository;
import com.majority.mynotebook.repository.CategoryRepository;

@RestController
@RequestMapping("/annotation")
public class AnnotationController {

	@Autowired
	private AnnotationRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping
	public List<AnnotationDto> getAnnotations() {
		List<Annotation> queryResult = repository.findAll();

		List<AnnotationDto> annotations = new AnnotationDto().convertList(queryResult);
		return annotations;
	}

	@PostMapping
	@Transactional
	public ResponseEntity<Annotation> postAnnotation(@RequestBody Annotation annotation, UriComponentsBuilder uriBuilder) {
		
		Optional<Category> optional = categoryRepository.findByName(annotation.getCategory().getName());
		if(optional.isPresent()) {
			annotation.setCategory(optional.get());
		}
		
		annotation.setCreationDate(LocalDateTime.now());
		
		repository.save(annotation);

		URI uri = uriBuilder.path("/annotation/{id}").buildAndExpand(annotation.getId()).toUri();
		return ResponseEntity.created(uri).body(annotation);
	}

}
