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
@Table(name= "dept_month_history")
public class DeptMonthHistoryModel implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_objective", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private ObjectiveModel objective;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_department", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private DepartmentModel historyDepartment;
	
	@NotNull
    @Column(name = "date", nullable = false)
    private Timestamp date;
	
	@NotNull
    @Column(name = "progress", nullable = false)
    private int progress;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ObjectiveModel getObjective() {
		return objective;
	}

	public void setObjective(ObjectiveModel objective) {
		this.objective = objective;
	}

	public DepartmentModel getDepartment() {
		return historyDepartment;
	}

	public void setDepartment(DepartmentModel department) {
		this.historyDepartment = department;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public DepartmentModel getHistoryDepartment() {
		return historyDepartment;
	}

	public void setHistoryDepartment(DepartmentModel historyDepartment) {
		this.historyDepartment = historyDepartment;
	}
	
	
}
