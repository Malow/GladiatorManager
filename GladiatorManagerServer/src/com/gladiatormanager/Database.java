package com.gladiatormanager;

import com.gladiatormanager.comstructs.ErrorResponse;
import com.gladiatormanager.comstructs.LoginRequest;
import com.gladiatormanager.comstructs.LoginResponse;
import com.gladiatormanager.comstructs.Response;

public class Database {
	public static Response login(LoginRequest req) {
		if(req != null)
		{
			System.out.println("New request received for: " + req.email + " with pw: " + req.password);
			return new LoginResponse(true, "ASDFG");
		}
		else
		{
			System.out.println("Failed Login-request, request missing parameters");
			return new ErrorResponse(false, "Request missing parameters");
		}
	}
}
