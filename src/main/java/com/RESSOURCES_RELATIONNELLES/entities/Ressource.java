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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ressource")
public class Ressource {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "headerImagePath", nullable = true)
	private String headerImagePath;

	@Column(name = "filePath", nullable = true)
	private String filePath;

	@Column(name = "content",columnDefinition="TEXT", nullable = false)
	private String content;

	@Column(name = "publicationDate", nullable = false)
	private Date publicationDate = Date.valueOf(LocalDate.now());

	@Column(name = "updateDate", nullable = true)
	private Date updateDate;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "isPublished", nullable = false)
	private Boolean isPublished = false;

	@Column(name = "isActived", nullable = false)
	private Boolean isActived = true;

	@OneToOne
	@JoinColumn(name = "statistic_id")
	private Statistic statistic;

	@ManyToOne
	private Category category;

	@ManyToOne
	private RessourceType ressourceType;
	
	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "ressource")
	private List<HaveRelationType> listRelationTypes;
	
	@OneToMany(mappedBy="ressource")
	private List<Favorite> listFavorite;
	
	@OneToMany(mappedBy="ressource")
	private List<SaveToConsult> listSaveToConsult;


	public Ressource() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ressource(String title, String headerImagePath, String filePath, String content, Date publicationDate,
			Date updateDate, String description, String status, Boolean isPublished, Boolean isActived,
			Statistic statistic, Category category, RessourceType ressourceType, User user,
			List<HaveRelationType> listRelationTypes, List<Favorite> listFavorite, List<SaveToConsult> listSaveToConsult) {
		super();
		this.title = title;
		this.headerImagePath = headerImagePath;
		this.filePath = filePath;
		this.content = content;
		this.publicationDate = publicationDate;
		this.updateDate = updateDate;
		this.description = description;
		this.status = status;
		this.isPublished = isPublished;
		this.isActived = isActived;
		this.statistic = statistic;
		this.category = category;
		this.ressourceType = ressourceType;
		this.user = user;
		this.listRelationTypes = listRelationTypes;
		this.listSaveToConsult = listSaveToConsult;
	}

	public List<SaveToConsult> getListSaveToConsult() {
		return listSaveToConsult;
	}

	public void setListSaveToConsult(List<SaveToConsult> listSaveToConsult) {
		this.listSaveToConsult = listSaveToConsult;
	}

	public List<Favorite> getListFavorite() {
		return listFavorite;
	}

	public void setListFavorite(List<Favorite> listFavorite) {
		this.listFavorite = listFavorite;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getHeaderImagePath() {
		return headerImagePath;
	}

	public void setHeaderImagePath(String headerImagePath) {
		this.headerImagePath = headerImagePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Boolean getIsActived() {
		return isActived;
	}

	public void setIsActived(Boolean isActived) {
		this.isActived = isActived;
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public RessourceType getRessourceType() {
		return ressourceType;
	}

	public void setRessourceType(RessourceType ressourceType) {
		this.ressourceType = ressourceType;
	}

	public List<HaveRelationType> getListRelationTypes() {
		return listRelationTypes;
	}

	public void setListRelationTypes(List<HaveRelationType> listRelationTypes) {
		this.listRelationTypes = listRelationTypes;
	}

}
