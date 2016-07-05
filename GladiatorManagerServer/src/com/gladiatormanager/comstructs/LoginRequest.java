package com.gladiatormanager.comstructs;

public class LoginRequest implements Request
{
  public String email;
  public String password;

  public LoginRequest(String email, String password)
  {
    this.email = email;
    this.password = password;
  }

  @Override
  public boolean isValid()
  {
    if (this.email != null && this.password != null) return true;

    return false;
  }
}
