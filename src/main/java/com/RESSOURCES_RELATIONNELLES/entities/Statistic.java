package com.RESSOURCES_RELATIONNELLES.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "statistic")
public class Statistic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nbConsult")
	private int nbConsult;

	@Column(name = "nbFav")
	private int nbFav;

	@Column(name = "nbExploit")
	private int nbExploit;

	@Column(name = "nbComment")
	private int nbComment;

	@OneToOne(mappedBy = "statistic")
	private Ressource ressource;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNbConsult() {
		return nbConsult;
	}

	public void setNbConsult(int nbConsult) {
		this.nbConsult = nbConsult;
	}

	public int getNbFav() {
		return nbFav;
	}

	public void setNbFav(int nbFav) {
		this.nbFav = nbFav;
	}

	public int getNbExploit() {
		return nbExploit;
	}

	public void setNbExploit(int nbExploit) {
		this.nbExploit = nbExploit;
	}

	public int getNbComment() {
		return nbComment;
	}

	public void setNbComment(int nbComment) {
		this.nbComment = nbComment;
	}

	public Ressource getRessource() {
		return ressource;
	}

	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}

	public Statistic(int nbConsult, int nbFav, int nbExploit, int nbComment, Ressource ressource) {
		super();
		this.nbConsult = nbConsult;
		this.nbFav = nbFav;
		this.nbExploit = nbExploit;
		this.nbComment = nbComment;
		this.ressource = ressource;
	}

	public Statistic() {
		super();
		// TODO Auto-generated constructor stub
	}

}
