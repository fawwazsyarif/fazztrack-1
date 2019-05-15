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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name= "review")
public class ReviewModel implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonIgnoreProperties({"userObjective","alignedObjective","approvalStatus","createdDate",
	"lastModifiedDate","listComment","listKR","listObjective", "listReviewerReview", "listRevieweeReview"})
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reviewer", referencedColumnName="id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private UserModel reviewer;
	
	@JsonIgnoreProperties({"userObjective","alignedObjective","approvalStatus","createdDate",
	"lastModifiedDate","listComment","listKR","listObjective", "listReviewerReview", "listRevieweeReview"})
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reviewee", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private UserModel reviewee;
	
	@NotNull
    @Column(name = "avg_score", nullable = false)
    private Float avgScore;
	
	@NotNull
    @Column(name = "type", nullable = false)
    private String type;
	
	@NotNull
    @Column(name = "date", nullable = false)
	private Timestamp date;
	
	@NotNull
	@Column(name = "status", nullable= false)
	private String status;

	@OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ReviewQuestionModel> listReviewQuestion;


	public ReviewModel() {
	}

	public ReviewModel(long id, UserModel reviewer, UserModel reviewee, Float avgScore, String type, Timestamp date, String status, List<ReviewQuestionModel> listReviewQuestion) {
		this.id = id;
		this.reviewer = reviewer;
		this.reviewee = reviewee;
		this.avgScore = avgScore;
		this.type = type;
		this.date = date;
		this.status = status;
		this.listReviewQuestion = listReviewQuestion;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserModel getReviewer() {
		return this.reviewer;
	}

	public void setReviewer(UserModel reviewer) {
		this.reviewer = reviewer;
	}

	public UserModel getReviewee() {
		return this.reviewee;
	}

	public void setReviewee(UserModel reviewee) {
		this.reviewee = reviewee;
	}

	public Float getAvgScore() {
		return this.avgScore;
	}

	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ReviewQuestionModel> getListReviewQuestion() {
		return this.listReviewQuestion;
	}

	public void setListReviewQuestion(List<ReviewQuestionModel> listReviewQuestion) {
		this.listReviewQuestion = listReviewQuestion;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", reviewer='" + getReviewer() + "'" +
			", reviewee='" + getReviewee() + "'" +
			", avgScore='" + getAvgScore() + "'" +
			", type='" + getType() + "'" +
			", date='" + getDate() + "'" +
			", status='" + getStatus() + "'" +
			", listReviewQuestion='" + getListReviewQuestion() + "'" +
			"}";
	}
	

	
}
