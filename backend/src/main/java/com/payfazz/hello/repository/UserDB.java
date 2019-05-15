package com.payfazz.hello.repository;

import com.payfazz.hello.model.DepartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.payfazz.hello.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDB extends JpaRepository<UserModel, Long> {
	UserModel findByUsername(String username);
	UserModel findById(long id);
	List<UserModel> findByUserDepartment(DepartmentModel dept);
}
