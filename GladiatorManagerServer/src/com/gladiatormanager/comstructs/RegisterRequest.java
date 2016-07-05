package com.gladiatormanager.comstructs;

public class RegisterRequest implements Request
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

  @Override
  public boolean isValid()
  {
    if (this.email != null && this.username != null && this.password != null) return true;

    return false;
  }
}
