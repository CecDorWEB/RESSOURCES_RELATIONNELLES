package com.RESSOURCES_RELATIONNELLES.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "isActivated", nullable = true)
	private boolean isActivated;

	@Column(name = "isReported", nullable = true)
	private boolean isReported;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ressource_id")
	private Ressource ressource;

	@JoinColumn(name = "user_id")
	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Comment parent;

	public Comment getParent() {
		return parent;
	}

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private List<Comment> replies = new ArrayList<>();

	public List<Comment> getReplies() {
		return replies;
	}

	public void setReplies(List<Comment> replies) {
		this.replies = replies;
	}

	public void setParent(Comment parent) {
		this.parent = parent;
	}

	public Ressource getRessource() {
		return ressource;
	}

	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public boolean isReported() {
		return isReported;
	}

	public void setReported(boolean isReported) {
		this.isReported = isReported;
	}

	public Comment(String name, String content, boolean isActivated, boolean isReported) {
		super();
		this.name = name;
		this.content = content;
		this.isActivated = isActivated;
		this.isReported = isReported;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

}
