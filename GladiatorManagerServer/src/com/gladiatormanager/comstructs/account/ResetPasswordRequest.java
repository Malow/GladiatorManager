package com.gladiatormanager.comstructs.account;

import com.gladiatormanager.comstructs.Request;

public class ResetPasswordRequest extends Request
{
  public String password;
  public String pwResetToken;

  public ResetPasswordRequest(String email, String password, String pwResetToken)
  {
    super(email);
    this.password = password;
    this.pwResetToken = pwResetToken;
  }

  @Override
  public boolean isValid()
  {
    if (super.isValid() && this.password != null && this.pwResetToken != null) return true;

    return false;
  }
}
