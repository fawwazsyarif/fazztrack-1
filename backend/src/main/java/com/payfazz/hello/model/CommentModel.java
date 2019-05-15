package com.payfazz.hello.model;

import java.io.Serializable;
import java.sql.Timestamp;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name= "comment")
public class CommentModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	//@JsonIgnoreProperties("listComment")
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_objective", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private ObjectiveModel commentObjective;
	
	//@JsonIgnoreProperties("")
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_commenter", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private UserModel commenter;
	
	@NotNull
    @Column(name = "description", nullable = false)
    private String description;
	
	@NotNull
    @Column(name = "date", nullable = false)
    private Timestamp date;
	
    @Column(name = "is_edited", nullable = true)
    private boolean isEdited;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ObjectiveModel getCommentObjective() {
		return commentObjective;
	}

	public void setCommentObjective(ObjectiveModel commentObjective) {
		this.commentObjective = commentObjective;
	}

	public UserModel getCommenter() {
		return commenter;
	}

	public void setCommenter(UserModel commenter) {
		this.commenter = commenter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public boolean isEdited() {
		return isEdited;
	}

	public void setEdited(boolean isEdited) {
		this.isEdited = isEdited;
	}
	
    
}
