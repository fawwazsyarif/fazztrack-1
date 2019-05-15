package com.payfazz.hello.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name= "key_result")
public class KeyResultModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@JsonIgnoreProperties("listKR")
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_objective", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private ObjectiveModel objectiveKR;
	
	@NotNull
    @Column(name = "description", nullable = false)
    private String description;
	
	@NotNull
    @Column(name = "progress", nullable = false)
    private int progress;
	
    @Column(name = "document", nullable = true)
    private String document;
    
    @NotNull
    @Column(name = "last_modified_date", nullable = false)
    private Timestamp lastModifiedDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ObjectiveModel getObjectiveKR() {
		return objectiveKR;
	}

	public void setObjectiveKR(ObjectiveModel objectiveKR) {
		this.objectiveKR = objectiveKR;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}	
}
