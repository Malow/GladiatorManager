package com.gladiatormanager.comstructs;

public class ResetPasswordRequest
{
  public String email;
  public String password;
  public String pwResetToken;

  public ResetPasswordRequest(String email, String newPassword, String pwResetToken)
  {
    this.email = email;
    this.password = newPassword;
    this.pwResetToken = pwResetToken;
  }

  public boolean validate()
  {
    if (this.email != null && this.password != null && this.pwResetToken != null) return true;

    return false;
  }
}
