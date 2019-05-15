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
@Table(name= "review_question")
public class ReviewQuestionModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@JsonIgnoreProperties({"reviewee", "listReviewQuestion"})
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_review", referencedColumnName="id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private ReviewModel review;

	@JsonIgnoreProperties({"listQuestionReview"})	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question", referencedColumnName="id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private QuestionModel questionReview;
	
	@NotNull
    @Column(name = "score", nullable = false)
    private int score;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ReviewModel getReview() {
		return review;
	}

	public void setReview(ReviewModel review) {
		this.review = review;
	}

	public QuestionModel getQuestionReview() {
		return questionReview;
	}

	public void setQuestionReview(QuestionModel questionReview) {
		this.questionReview = questionReview;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
