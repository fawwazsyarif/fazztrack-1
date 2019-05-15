package com.payfazz.hello.service;


import com.payfazz.hello.model.DepartmentModel;
import com.payfazz.hello.model.ObjectiveModel;
import com.payfazz.hello.model.RoleModel;
import com.payfazz.hello.repository.DepartmentDB;
import com.payfazz.hello.repository.RoleDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.payfazz.hello.model.UserModel;
import com.payfazz.hello.repository.UserDB;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDB userDb;

	@Autowired
	private DepartmentDB departmentDB;

	@Autowired
	private RoleDB roleDB;

	@Override
	public void addUser(UserModel user) {
		userDb.save(user);
	}

	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
	public List<UserModel> findAll() {
		return userDb.findAll();
	}

	@Override
	public UserModel findUserByUsername(String username) {
		return userDb.findByUsername(username);
	}

	@Override
	public List<UserModel> findManagerByDept(long idDept){
		DepartmentModel department = departmentDB.findById(idDept).get();
		RoleModel manager = roleDB.findById(Long.parseLong("2")).get();
		List<UserModel> userByDept = userDb.findByUserDepartment(department);
		List<UserModel> deptMng = new ArrayList<>();

		for (UserModel user : userByDept){
			if (user.getRoles().contains(manager)){
				deptMng.add(user);
			}
		}
		return deptMng;
	}

	@Override
	public UserModel findUserById(long id) {
		return userDb.findById(id);
	}

	@Override
	public void deleteUser(UserModel user){
		userDb.delete(user);
	}


	@Override
	public boolean isValidPassword(String passwordInput, String username) {
		UserModel user = userDb.findByUsername(username);
		
//		Pakai ini untuk security, password telah diencrypt (jangan dihapus)
//		String passwordInputEncrypt = this.encrypt(passwordInput);
//		if(passwordInputEncrypt.equals(user.getPassword())) {
//			return true;
//		}
		
//		Pakai ini untuk password non-encrypt
		if(passwordInput.equals(user.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public List<ObjectiveModel> findUserObjective(long userId) {
		UserModel user = userDb.getOne(userId);
		return user.getListObjective();
	}
	
	
	
}

