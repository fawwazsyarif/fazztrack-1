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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name= "comp_month_summary")
public class CompMonthSumModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_objective", referencedColumnName = "id", nullable = false)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	private ObjectiveModel compObjective;

	@NotNull
    @Column(name = "date", nullable = false)
    private Timestamp date;
	
	@NotNull
    @Column(name = "avg_progress", nullable = false)
    private int avgProgress;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getAvgProgress() {
		return avgProgress;
	}

	public void setAvgProgress(int avgProgress) {
		this.avgProgress = avgProgress;
	}

	public ObjectiveModel getCompObjective() {
		return compObjective;
	}

	public void setCompObjective(ObjectiveModel compSumObjective) {
		this.compObjective = compSumObjective;
	}
	
}
