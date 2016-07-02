package com.gladiatormanager.comstructs;

public class LoginRequest
{
  public String email;
  public String password;

  public LoginRequest(String email, String password)
  {
    this.email = email;
    this.password = password;
  }

  public boolean validate()
  {
    if (this.email != null && this.password != null) return true;

    return false;
  }
}
