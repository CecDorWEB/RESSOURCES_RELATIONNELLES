package com.RESSOURCES_RELATIONNELLES.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "haveRelationType")
public class HaveRelationType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne
	private Ressource ressource;

	@JsonIgnore
	@ManyToOne
	private RelationType relationType;

	public Ressource getRessource() {
		return ressource;
	}

	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}

	public RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

	public HaveRelationType(Ressource ressource, RelationType relationType) {
		super();
		this.ressource = ressource;
		this.relationType = relationType;
	}

	public HaveRelationType() {
		super();
		// TODO Auto-generated constructor stub
	}

}
