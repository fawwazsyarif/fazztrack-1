package com.payfazz.hello.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name= "user.user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserModel implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@NotNull
    @Column(name = "name", nullable = false)
    private String name;
	
	@NotNull
	@Size(max=50)
    @Column(name = "username", unique=true, nullable = false)
    private String username;
	
	@NotNull
    @Column(name = "password", nullable = false)
    private String password;
	
	@JsonIgnoreProperties("listUser")
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_department", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private DepartmentModel userDepartment;
	
	@JsonIgnoreProperties({"listManager","listReviewerReview", "listRevieweeReview"})
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_manager", referencedColumnName = "id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private UserModel userManager;
	
	@JsonIgnoreProperties("users")
    @ManyToMany
    private Set<RoleModel> roles;
    
	@NotNull
    @Column(name = "is_active", nullable = false)
    private int isActive;
	
	@NotNull
    @Column(name = "NIK", unique=true, nullable = false)
    private String nik;

	// @JsonIgnore
	@JsonIgnoreProperties({"userObjective", "approvalStatus", "listComment"}) //kalo suatu saat butuh list objective dari user tsb. ini di on-in
	@OneToMany(mappedBy = "userObjective", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ObjectiveModel> listObjective;
	
	// @JsonIgnore
	@JsonIgnoreProperties("reviewer") //kalo suatu saat butuh list objective dari user tsb. ini di on-in
	@OneToMany(mappedBy = "reviewer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ReviewModel> listReviewerReview;

	// @JsonIgnore
	@JsonIgnoreProperties("reviewee") //kalo suatu saat butuh list objective dari user tsb. ini di on-in
	@OneToMany(mappedBy = "reviewee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ReviewModel> listRevieweeReview;
	
	@JsonIgnore
	// @JsonIgnoreProperties("userManager") //kalo suatu saat butuh list
	@OneToMany(mappedBy = "userManager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserModel> listManager;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userObjective", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<FeedbackModel> listFeedback;
	

	public UserModel() {
	}

	public UserModel(long id, String name, String username, String password, DepartmentModel userDepartment, UserModel userManager, Set<RoleModel> roles, int isActive, String nik, List<ObjectiveModel> listObjective, List<ReviewModel> listReviewerReview, List<ReviewModel> listRevieweeReview, List<UserModel> listManager, List<FeedbackModel> listFeedback) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.userDepartment = userDepartment;
		this.userManager = userManager;
		this.roles = roles;
		this.isActive = isActive;
		this.nik = nik;
		this.listObjective = listObjective;
		this.listReviewerReview = listReviewerReview;
		this.listRevieweeReview = listRevieweeReview;
		this.listManager = listManager;
		this.listFeedback = listFeedback;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DepartmentModel getUserDepartment() {
		return this.userDepartment;
	}

	public void setUserDepartment(DepartmentModel userDepartment) {
		this.userDepartment = userDepartment;
	}

	public UserModel getUserManager() {
		return this.userManager;
	}

	public void setUserManager(UserModel userManager) {
		this.userManager = userManager;
	}

	public Set<RoleModel> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getNik() {
		return this.nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	public List<ObjectiveModel> getListObjective() {
		return this.listObjective;
	}

	public void setListObjective(List<ObjectiveModel> listObjective) {
		this.listObjective = listObjective;
	}

	public List<ReviewModel> getListReviewerReview() {
		return this.listReviewerReview;
	}

	public void setListReviewerReview(List<ReviewModel> listReviewerReview) {
		this.listReviewerReview = listReviewerReview;
	}

	public List<ReviewModel> getListRevieweeReview() {
		return this.listRevieweeReview;
	}

	public void setListRevieweeReview(List<ReviewModel> listRevieweeReview) {
		this.listRevieweeReview = listRevieweeReview;
	}

	public List<UserModel> getListManager() {
		return this.listManager;
	}

	public void setListManager(List<UserModel> listManager) {
		this.listManager = listManager;
	}

	public List<FeedbackModel> getListFeedback() {
		return this.listFeedback;
	}

	public void setListFeedback(List<FeedbackModel> listFeedback) {
		this.listFeedback = listFeedback;
	}

	public UserModel id(long id) {
		this.id = id;
		return this;
	}

	public UserModel name(String name) {
		this.name = name;
		return this;
	}

	public UserModel username(String username) {
		this.username = username;
		return this;
	}

	public UserModel password(String password) {
		this.password = password;
		return this;
	}

	public UserModel userDepartment(DepartmentModel userDepartment) {
		this.userDepartment = userDepartment;
		return this;
	}

	public UserModel userManager(UserModel userManager) {
		this.userManager = userManager;
		return this;
	}

	public UserModel roles(Set<RoleModel> roles) {
		this.roles = roles;
		return this;
	}

	public UserModel isActive(int isActive) {
		this.isActive = isActive;
		return this;
	}

	public UserModel nik(String nik) {
		this.nik = nik;
		return this;
	}

	public UserModel listObjective(List<ObjectiveModel> listObjective) {
		this.listObjective = listObjective;
		return this;
	}

	public UserModel listReviewerReview(List<ReviewModel> listReviewerReview) {
		this.listReviewerReview = listReviewerReview;
		return this;
	}

	public UserModel listRevieweeReview(List<ReviewModel> listRevieweeReview) {
		this.listRevieweeReview = listRevieweeReview;
		return this;
	}

	public UserModel listManager(List<UserModel> listManager) {
		this.listManager = listManager;
		return this;
	}

	public UserModel listFeedback(List<FeedbackModel> listFeedback) {
		this.listFeedback = listFeedback;
		return this;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", name='" + getName() + "'" +
			", username='" + getUsername() + "'" +
			", password='" + getPassword() + "'" +
			", userDepartment='" + getUserDepartment() + "'" +
			", userManager='" + getUserManager() + "'" +
			", roles='" + getRoles() + "'" +
			", isActive='" + getIsActive() + "'" +
			", nik='" + getNik() + "'" +
			", listObjective='" + getListObjective() + "'" +
			", listReviewerReview='" + getListReviewerReview() + "'" +
			", listRevieweeReview='" + getListRevieweeReview() + "'" +
			", listManager='" + getListManager() + "'" +
			", listFeedback='" + getListFeedback() + "'" +
			"}";
	}
	
}
