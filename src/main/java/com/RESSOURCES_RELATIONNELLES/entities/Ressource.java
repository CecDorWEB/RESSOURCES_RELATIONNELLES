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

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "publicationDate", nullable = false)
	private Date publicationDate = Date.valueOf(LocalDate.now());

	@Column(name = "updateDate", nullable = true)
	private Date updateDate;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "status", nullable = true)
	private String status;

	@Column(name = "privacy", nullable = false)
	private Boolean privacy;

	@Column(name = "isActive", nullable = false)
	private Boolean isActive;

	@OneToOne
	@JoinColumn(name = "statistic_id")
	private Statistic statistic;

	@ManyToOne
	private Category category;

	@ManyToOne
	private RessourceType ressourceType;

	@OneToMany(mappedBy = "ressource")
	private List<HaveRelationType> listRelationTypes;

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

	public Boolean getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Boolean privacy) {
		this.privacy = privacy;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Ressource(String title, String headerImagePath, String filePath, String content, Date publicationDate,
			Date updateDate, String description, String status, Boolean privacy, Boolean isActive, Statistic statistic,
			Category category, RessourceType ressourceType, List<HaveRelationType> listRelationTypes) {
		super();
		this.title = title;
		this.headerImagePath = headerImagePath;
		this.filePath = filePath;
		this.content = content;
		this.publicationDate = publicationDate;
		this.updateDate = updateDate;
		this.description = description;
		this.status = status;
		this.privacy = privacy;
		this.isActive = isActive;
		this.statistic = statistic;
		this.category = category;
		this.ressourceType = ressourceType;
		this.listRelationTypes = listRelationTypes;
	}

	public Ressource() {
		super();
		// TODO Auto-generated constructor stub
	}

}
