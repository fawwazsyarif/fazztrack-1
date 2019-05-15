package com.payfazz.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payfazz.hello.model.LoggedInModel;
import com.payfazz.hello.model.LoginModel;
import com.payfazz.hello.model.UserModel;
import com.payfazz.hello.repository.UserDB;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.rest.LoginResponse;
import com.payfazz.hello.rest.UserResponse;
import com.payfazz.hello.service.DepartmentService;
import com.payfazz.hello.service.UserService;

@CrossOrigin
@RestController
public class MainController {

	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	UserService userService;

	@Autowired
	UserDB userDb;
	
	@GetMapping(value="/ping")
	public BaseResponse<String> view(){
		BaseResponse<String> response = new BaseResponse<String>();
		response.setStatus(200);
		response.setMessage("Success");
		response.setResult("pong");
		return response;
		
	}
	
	@PostMapping(value="/login")
	public Object login(@RequestBody LoginModel login){
		LoginResponse<LoggedInModel> response = new LoginResponse<>();
		if(userService.findUserByUsername(login.getUsername())!= null){
			if(userService.isValidPassword(login.getPassword(),login.getUsername())){
				UserModel user = userService.findUserByUsername(login.getUsername());
				LoggedInModel logged = new LoggedInModel();
				logged.setId(user.getId());
				logged.setRole(user.getRoles().iterator().next().getName());
				logged.setName(user.getName());
				logged.setUsername(user.getUsername());
				logged.setPassword(user.getPassword());
				logged.setIdDepartment(user.getUserDepartment().getId());
				logged.setDepartmentName(user.getUserDepartment().getName());
				// System.out.println(user.getUserManager().equals(null));
				if(user.getUserManager() != null){
					logged.setManager(user.getUserManager().getId());
				}
				response.setStatus(200);
				response.setMessage("Success");
				response.setResult(logged);
				return response;
			}
			else{
				response.setResult(null);
				response.setStatus(401);
				response.setMessage("Username atau Password Salah");
				return response;
			}
		}
		return response;
	}
}
