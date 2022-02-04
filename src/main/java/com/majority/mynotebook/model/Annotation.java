package com.majority.mynotebook.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.majority.mynotebook.enums.Status;
import com.majority.mynotebook.model.form.AnnotationForm;

@Entity
public class Annotation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	private LocalDateTime creationDate;
	@ManyToOne(cascade = CascadeType.REMOVE)
	private Category category;
	@ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Tag> tags;
	private Enum status;
	private Long userId;
	
	public Annotation() {
	}
	
	public Annotation(AnnotationForm annotationForm) {
		this.title = annotationForm.getTitle();
		this.content = annotationForm.getContent();
		this.creationDate = LocalDateTime.now();
		this.status = Status.ACTIVE;
		this.tags = new ArrayList<Tag>();
	}
	
	public Annotation(Long id, String title, String content, LocalDateTime creationDate,Category category, List<Tag> tags, Long userId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.creationDate = creationDate;
		this.category = category;
		this.tags = tags;
		this.userId = userId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Enum getStatus() {
		return status;
	}

	public void setStatus(Enum status) {
		this.status = status;
	}
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
}
