package com.payfazz.hello.rest;

import java.util.List;

public class UserResponse <UserModel>{
	
    private List<UserModel> result;
	
	public List<UserModel> getResult() {
		return result;
	}
	public void setResult(List<UserModel> result) {
		this.result = result;
	}
    
    
}
