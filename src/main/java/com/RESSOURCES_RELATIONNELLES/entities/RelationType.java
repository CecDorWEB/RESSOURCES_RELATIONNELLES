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
@Table(name = "relationType")
public class RelationType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "relationType")
	private List<HaveRelationType> listRelationTypes;

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

	public List<HaveRelationType> getListRelationTypes() {
		return listRelationTypes;
	}

	public void setListRelationTypes(List<HaveRelationType> listRelationTypes) {
		this.listRelationTypes = listRelationTypes;
	}

	public RelationType(String name, List<HaveRelationType> listRelationTypes) {
		super();
		this.name = name;
		this.listRelationTypes = listRelationTypes;
	}

	public RelationType() {
		super();
		// TODO Auto-generated constructor stub
	}
}
