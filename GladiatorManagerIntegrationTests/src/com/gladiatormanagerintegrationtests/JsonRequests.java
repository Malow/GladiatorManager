package com.gladiatormanagerintegrationtests;

import com.gladiatormanager.comstructs.AuthorizedRequest;
import com.gladiatormanager.comstructs.Request;
import com.gladiatormanager.comstructs.account.LoginRequest;
import com.gladiatormanager.comstructs.account.RegisterRequest;
import com.gladiatormanager.comstructs.account.ResetPasswordRequest;
import com.google.gson.Gson;

public class JsonRequests
{
  public static String register(String email, String username, String password)
  {
    return new Gson().toJson(new RegisterRequest(email, username, password));
  }

  public static String login(String email, String password)
  {
    return new Gson().toJson(new LoginRequest(email, password));
  }

  public static String sendPasswordReset(String email)
  {
    return new Gson().toJson(new Request(email));
  }

  public static String resetPassword(String email, String password, String pwResetToken)
  {
    return new Gson().toJson(new ResetPasswordRequest(email, password, pwResetToken));
  }

  public static String getMercenaries(String email, String authToken)
  {
    return new Gson().toJson(new AuthorizedRequest(email, authToken));
  }
}
