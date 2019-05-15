package com.payfazz.hello.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name= "objective")
public class ObjectiveModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@JsonIgnoreProperties({"userObjective","alignedObjective","approvalStatus","createdDate",
	"lastModifiedDate","listComment","listKR","listObjective"})
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	private UserModel userObjective;
	
	@JsonIgnoreProperties({"userObjective","alignedObjective","approvalStatus","createdDate",
	"lastModifiedDate","listComment","listKR","listObjective"})
	@ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "id_aligned_objective", referencedColumnName="id",nullable = true)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private ObjectiveModel alignedObjective;
	
	@JsonIgnoreProperties("listObjective")
	// @JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_approval_status", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private ApprovalStatusModel approvalStatus;
	
	@NotNull
    @Column(name = "description", nullable = false)
    private String description;
	
	@NotNull
    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;
	
	@NotNull
    @Column(name = "last_modified_date", nullable = false)
    private Timestamp lastModifiedDate;

	@JsonIgnoreProperties("commentObjective")
	@OneToMany(mappedBy = "commentObjective", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CommentModel> listComment;
	
	@JsonIgnoreProperties("objectiveKR")
	@OneToMany(mappedBy = "objectiveKR", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<KeyResultModel> listKR;
	
	//@JsonIgnoreProperties("alignedObjective") //kalo suatu saat butuh nampilin list objektif2 anak2nya
	@JsonIgnore
	@OneToMany(mappedBy = "alignedObjective", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ObjectiveModel> listObjective;
	
	@JsonIgnore
	@OneToMany(mappedBy = "compObjective", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CompMonthSumModel> listObjectiveSum;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public ObjectiveModel getAlignedObjective() {
		return alignedObjective;
	}

	public void setAlignedObjective(ObjectiveModel alignedObjective) {
		this.alignedObjective = alignedObjective;
	}

	public ApprovalStatusModel getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalStatusModel approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public UserModel getUserObjective() {
		return userObjective;
	}

	public void setUserObjective(UserModel userObjective) {
		this.userObjective = userObjective;
	}

	public List<CommentModel> getListComment() {
		return listComment;
	}

	public void setListComment(List<CommentModel> listComment) {
		this.listComment = listComment;
	}

	public List<KeyResultModel> getListKR() {
		return listKR;
	}

	public void setListKR(List<KeyResultModel> listKR) {
		this.listKR = listKR;
	}

	public List<ObjectiveModel> getListObjective() {
		return listObjective;
	}

	public void setListObjective(List<ObjectiveModel> listObjective) {
		this.listObjective = listObjective;
	}

	public List<CompMonthSumModel> getListObjectiveSum() {
		return listObjectiveSum;
	}

	public void setListObjectiveSum(List<CompMonthSumModel> listObjectiveSum) {
		this.listObjectiveSum = listObjectiveSum;
	}

	
	

}
