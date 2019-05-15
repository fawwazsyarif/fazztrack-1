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
@Table(name= "department")
public class DepartmentModel implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@NotNull
    @Column(name = "name", nullable = false)
    private String name;

	@JsonIgnoreProperties("userDepartment") //kalo suatu saat butuh nampilin user dalem department tsb.
	@OneToMany(mappedBy = "userDepartment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserModel> listUser;
	
	@JsonIgnore
	@OneToMany(mappedBy = "historyDepartment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DeptMonthHistoryModel> listHistory;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserModel> getListUser() {
		return listUser;
	}

	public void setListUser(List<UserModel> listUser) {
		this.listUser = listUser;
	}

	public List<DeptMonthHistoryModel> getListHistory() {
		return listHistory;
	}

	public void setListHistory(List<DeptMonthHistoryModel> listHistory) {
		this.listHistory = listHistory;
	}
	
}
