package com.payfazz.hello.model;


import java.util.Set;

public class LoggedInModel{

    private long id;
	private String name;
    private String username;
	private String role;
	private String password;
	private long idDepartment;
	private String departmentName;
	private long idManager;

	public long getId() {
		return id;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the idDepartment
	 */
	public long getIdDepartment() {
		return idDepartment;
	}

	/**
	 * @param idDepartment the idDepartment to set
	 */
	public void setIdDepartment(long idDepartment) {
		this.idDepartment = idDepartment;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String roleModel) {
		this.role = roleModel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getManager() {
		return idManager;
	}

	public void setManager(long idManager) {
		this.idManager = idManager;
	}

	
	
	
	
	

}
