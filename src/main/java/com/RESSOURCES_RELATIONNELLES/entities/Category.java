package com.RESSOURCES_RELATIONNELLES.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private int name;

	@OneToMany(mappedBy = "category")
	private List<Ressource> listRessources;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public List<Ressource> getListRessources() {
		return listRessources;
	}

	public void setListRessources(List<Ressource> listRessources) {
		this.listRessources = listRessources;
	}

	public Category(int name, List<Ressource> listRessources) {
		super();
		this.name = name;
		this.listRessources = listRessources;
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

}
