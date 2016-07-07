package com.gladiatormanager.account;

public class Account
{
  public class State
  {
    public static final int IS_REGISTERED = 0;
    public static final int HAS_TEAMNAME = 1;
    public static final int IS_ACTIVE = 100;
  }

  public Integer id;
  public String username;
  public String password;
  public String email;
  public String teamName;
  public int state;
  public String authToken;
  public String pwResetToken;

  public Account()
  {

  }

  public Account(Integer id, String username, String password, String email, String teamName, int state, String authToken, String pwResetToken)
  {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.teamName = teamName;
    this.state = state;
    this.authToken = authToken;
    this.pwResetToken = pwResetToken;
  }
}
