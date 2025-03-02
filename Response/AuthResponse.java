package com.ProjectManagement.Application.Response;

import lombok.Data;

public class AuthResponse {
	
	private String message;
	private String jwt;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	@Override
	public String toString() {
		return "AuthResponse [message=" + message + ", jwt=" + jwt + "]";
	}
	public AuthResponse() {
		super();
	}
	
	
}
