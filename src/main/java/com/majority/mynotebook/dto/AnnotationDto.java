package com.majority.mynotebook.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.majority.mynotebook.model.Annotation;
import com.majority.mynotebook.model.Category;
import com.majority.mynotebook.model.Tag;

public class AnnotationDto {

	private Long id;
	private String title;
	private String content;
	private LocalDateTime creationDate;
	private Category category;
	private List<Tag> tags;
	private Enum status;
	
	public AnnotationDto(Annotation annotation) {
		this.id = annotation.getId();
		this.title = annotation.getTitle();
		this.content = annotation.getContent();
		this.creationDate = annotation.getCreationDate();
		this.category = annotation.getCategory();
		this.tags = annotation.getTags();
		this.status = annotation.getStatus();
	}
	
	
	public AnnotationDto() {
	}
	
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public Category getCategory() {
		return category;
	}
	public List<Tag> getTags() {
		return tags;
	}
	
	public Enum getStatus() {
		return status;
	}
	
	public List<AnnotationDto> convertList(List<Annotation> annotations){
		
		return annotations.stream().map(AnnotationDto::new).collect(Collectors.toList());
	}
}
