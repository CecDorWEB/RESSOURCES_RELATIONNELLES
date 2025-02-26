package com.RESSOURCES_RELATIONNELLES.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "adresse")
public class Adresse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 80, nullable = false)
	private String country;

	@Column(length = 80, nullable = false)
	private String city;

	@Column(length = 5, nullable = false)
	private Integer numStreet;

	@Column(length = 100, nullable = false)
	private String street;

	@OneToOne(mappedBy = "adresse", cascade = CascadeType.ALL, orphanRemoval = true)
	private User user;

	public Adresse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Adresse(String country, String city, Integer numStreet, String street) {
		super();
		this.country = country;
		this.city = city;
		this.numStreet = numStreet;
		this.street = street;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getNumStreet() {
		return numStreet;
	}

	public void setNumStreet(Integer numStreet) {
		this.numStreet = numStreet;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
