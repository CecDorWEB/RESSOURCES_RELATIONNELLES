package com.RESSOURCES_RELATIONNELLES.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 80, nullable = false)
	private String name;

	@Column(length = 255, nullable = true)
	private String description;

	@OneToMany
	@JoinColumn(name = "role_id")
	private List<User> listeDeUser;

	public Role() {
		super();
	}

	public Role(String name, String description, List<User> listeDeUser) {
		super();
		this.name = name;
		this.description = description;
		this.listeDeUser = listeDeUser;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getListeDeUser() {
		return listeDeUser;
	}

	public void setListeDeUser(List<User> listeDeUser) {
		this.listeDeUser = listeDeUser;
	}

}
