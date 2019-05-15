package com.payfazz.hello.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name= "approval_status")
public class ApprovalStatusModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@NotNull
    @Column(name = "status", nullable = false)
    private String status;

	//@JsonIgnore
	@JsonIgnoreProperties("approvalStatus") //kalo suatu saat butuh nampilin list objektif
	@OneToMany(mappedBy = "approvalStatus", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ObjectiveModel> listObjective;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ObjectiveModel> getListObjective() {
		return listObjective;
	}

	public void setListObjective(List<ObjectiveModel> listObjective) {
		this.listObjective = listObjective;
	}
	
	
}
