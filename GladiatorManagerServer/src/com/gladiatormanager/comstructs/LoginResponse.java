package com.gladiatormanager.comstructs;

public class LoginResponse extends Response
{
  public String authToken;

  public LoginResponse(boolean result, String authToken)
  {
    super(result);
    this.authToken = authToken;
  }
}
