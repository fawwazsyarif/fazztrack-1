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
@Table(name= "question")
public class QuestionModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@NotNull
    @Column(name = "question", nullable = false)
    private String question;
	
	@JsonIgnore
	@OneToMany(mappedBy = "questionReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ReviewQuestionModel> listQuestionReview;

	public List<ReviewQuestionModel> getListQuestionReview() {
		return listQuestionReview;
	}

	public void setListQuestionReview(List<ReviewQuestionModel> listQuestionReview) {
		this.listQuestionReview = listQuestionReview;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
}
