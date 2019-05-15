package com.payfazz.hello.service;

import java.util.List;

import org.springframework.stereotype.Service;

import java.util.Optional;

import com.payfazz.hello.model.ObjectiveModel;
import com.payfazz.hello.model.UserModel;

import javax.validation.constraints.Size;
import java.util.List;

@Service
public interface UserService {
	void addUser(UserModel user);
	List<UserModel> findAll();
	UserModel findUserByUsername(String username);
	List<UserModel> findManagerByDept(long idDept);
	UserModel findUserById(long id);
	void deleteUser(UserModel user);
	String encrypt(String password);
	public boolean isValidPassword(String passwordInputan, String username);
	List<ObjectiveModel> findUserObjective(long userId);
}

