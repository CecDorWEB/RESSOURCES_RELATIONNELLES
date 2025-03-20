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
@Table(name = "ressourceType")
public class RessourceType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "ressourceType")
	private List<Ressource> listRessources;

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

	public List<Ressource> getListRessources() {
		return listRessources;
	}

	public void setListRessources(List<Ressource> listRessources) {
		this.listRessources = listRessources;
	}

	public RessourceType(String name, List<Ressource> listRessources) {
		super();
		this.name = name;
		this.listRessources = listRessources;
	}

	public RessourceType() {
		super();
		// TODO Auto-generated constructor stub
	}

}
