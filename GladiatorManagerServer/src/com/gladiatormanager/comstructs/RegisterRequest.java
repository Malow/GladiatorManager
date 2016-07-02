package com.gladiatormanager.comstructs;

public class RegisterRequest
{
  public String email;
  public String username;
  public String password;

  public RegisterRequest(String email, String username, String password)
  {
    this.email = email;
    this.username = username;
    this.password = password;
  }

  public boolean validate()
  {
    if (this.email != null && this.username != null && this.password != null) return true;

    return false;
  }
}
