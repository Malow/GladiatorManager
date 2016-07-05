package com.gladiatormanager.comstructs;

public class ResetPasswordRequest implements Request
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

  @Override
  public boolean isValid()
  {
    if (this.email != null && this.password != null && this.pwResetToken != null) return true;

    return false;
  }
}
