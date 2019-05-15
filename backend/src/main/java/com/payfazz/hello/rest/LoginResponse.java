package com.payfazz.hello.rest;

public class LoginResponse<LoggedInModel> {
	private int status;
    private String message;
    private LoggedInModel result;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LoggedInModel getResult() {
		return result;
	}
	public void setResult(LoggedInModel result) {
		this.result = result;
	}
    
    
}
