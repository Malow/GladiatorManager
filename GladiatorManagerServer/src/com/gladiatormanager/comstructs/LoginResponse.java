package com.gladiatormanager.comstructs;

public class LoginResponse implements Response {
	public boolean result;
	public String authToken;
	
	public LoginResponse(boolean result, String authToken) {
		this.result = result;
		this.authToken = authToken;
	}
}
