package com.majority.mynotebook.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.majority.mynotebook.dto.AnnotationDto;
import com.majority.mynotebook.model.Annotation;
import com.majority.mynotebook.model.Category;
import com.majority.mynotebook.model.Tag;
import com.majority.mynotebook.model.form.AnnotationForm;
import com.majority.mynotebook.repository.AnnotationRepository;
import com.majority.mynotebook.service.AnnotationService;

@RestController
@RequestMapping("/annotation")
public class AnnotationController {

	@Autowired
	private AnnotationRepository repository;
	
	@Autowired
	private AnnotationService annotationService;
	
	@GetMapping
	public List<AnnotationDto> getAnnotations() {
		Long userId = annotationService.getUserId();
		
		List<Annotation> queryResult = repository.findByUserId(userId);

		List<AnnotationDto> annotations = new AnnotationDto().convertList(queryResult);
		return annotations;
	}
	
	
	@GetMapping("/{status}")
	public List<AnnotationDto> getAnnotationsByStatus(@PathVariable String status) {
		List<Annotation> queryResult = repository.findByStatus(status);

		List<AnnotationDto> annotations = new AnnotationDto().convertList(queryResult);
		return annotations;
	}

	
	@PostMapping
	@Transactional
	public ResponseEntity<Annotation> postAnnotation(@RequestBody AnnotationForm annotationForm, UriComponentsBuilder uriBuilder) {
		
		Annotation annotation = new Annotation(annotationForm);
		
		annotationService.insertCategory(annotation, annotationForm.getCategory());
		annotationService.insertTags(annotation, annotationForm.getTags());
		annotationService.insertUserId(annotation);
		
		repository.saveAndFlush(annotation);

		URI uri = uriBuilder.path("/annotation/{id}").buildAndExpand(annotation.getId()).toUri();
		return ResponseEntity.created(uri).body(annotation);
	}
	
	
	@DeleteMapping("/trash/{id}")
	@Transactional
	public ResponseEntity<Annotation> trash(@PathVariable Long id){
		
		Optional<Annotation> annotationOptional =  repository.findById(id);
		
		if(annotationOptional.isPresent()){
			
			Annotation annotation = annotationOptional.get();
			annotation.setStatus("TRASH");
			
			repository.flush();
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@DeleteMapping("/definitive-delete/{id}")
	@Transactional
	public ResponseEntity<Annotation> definitiveDelete(@PathVariable Long id){
		
		Optional<Annotation> annotationOptional =  repository.findById(id);
		
		if(annotationOptional.isPresent()){
			Annotation annotation = annotationOptional.get();
			
			annotation.setTags(new ArrayList<Tag>());
			annotation.setCategory(new Category());
			
			repository.flush();
			
			repository.deleteById(id);
			
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/clean-trash")
	@Transactional
	public ResponseEntity<Annotation> cleanTrash(){
		
		List<Annotation> annotations =  repository.findByStatus("TRASH");
		
		for (Annotation annotation : annotations) {
			annotation.setTags(new ArrayList<Tag>());
			annotation.setCategory(new Category());
			
			repository.deleteById(annotation.getId());
			
			repository.flush();
		}
		
		return ResponseEntity.ok().build();
	}
	
	//Fazer resore all e funções de arquivar antes de montar a interface grafica
	
	@PutMapping("/restore/{id}")
	@Transactional
	public ResponseEntity<Annotation> restore(@PathVariable Long id){
		Optional<Annotation> annotationOptional = repository.findById(id);
		
		if(annotationOptional.isPresent()){
			
			Annotation annotation = annotationOptional.get();
			annotation.setStatus("ACTIVE");
			
			repository.flush();
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PutMapping("/restore-all")
	@Transactional
	public ResponseEntity<Annotation> restoreAll(){
		
		List<Annotation> annotations =  repository.findByStatus("TRASH");
		
		for (Annotation annotation : annotations) {
			annotation.setStatus("ACTIVE");
			
			repository.flush();
		}
		
		return ResponseEntity.ok().build();
	}
}
