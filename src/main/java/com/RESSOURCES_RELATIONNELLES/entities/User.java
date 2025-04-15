package com.RESSOURCES_RELATIONNELLES.entities;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", length = 100, nullable = false)
	private String email;

	@Column(name = "password", length = 255, nullable = false)
	private String password;

	@Column(name = "firstName", length = 80, nullable = false)
	private String firstName;

	@Column(name = "lastName", length = 80, nullable = false)
	private String lastName;

	@Column(name = "phoneNumber", length = 10, nullable = true)
	private String phoneNumber;

	@Column(name = "gender", length = 1, nullable = true)
	private Character gender;

	@Column(name = "birthday", length = 10, nullable = true)
	private Date birthday;

	@Column(name = "isActived", nullable = false)
	private boolean isActived = true;

	@Column(name = "creationDate")
	private Date creationDate = Date.valueOf(LocalDate.now());

	@Column(name = "lastLoginDate", nullable = true)
	private Date lastLoginDate;

	@OneToOne
	@JoinColumn(name = "adresse_id", nullable = true)
	private Adresse adresse;
	
	@OneToMany(mappedBy = "user")
	private List<Ressource> listRessources;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String email, String password, String firstName, String lastName, String phoneNumber, Character gender,
			Date birthday, boolean isActived, Date creationDate, Date lastLoginDate, Adresse adresse,
			List<Ressource> listRessources) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.birthday = birthday;
		this.isActived = isActived;
		this.creationDate = creationDate;
		this.lastLoginDate = lastLoginDate;
		this.adresse = adresse;
		this.listRessources = listRessources;
	}

	public List<Ressource> getListRessources() {
		return listRessources;
	}

	public void setListRessources(List<Ressource> listRessources) {
		this.listRessources = listRessources;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public boolean isActived() {
		return isActived;
	}

	public void setActived(boolean isActived) {
		this.isActived = isActived;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

}
