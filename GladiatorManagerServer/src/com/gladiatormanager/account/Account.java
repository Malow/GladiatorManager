package com.gladiatormanager.account;

public class Account
{
  public int id;
  public String username;
  public String password;
  public String email;
  public String authToken;
  public String pwResetToken;

  public Account(int id, String username, String password, String email, String authToken, String pwResetToken)
  {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.authToken = authToken;
    this.pwResetToken = pwResetToken;
  }
}
