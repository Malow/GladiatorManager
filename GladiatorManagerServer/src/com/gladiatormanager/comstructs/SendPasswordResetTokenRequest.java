package com.gladiatormanager.comstructs;

public class SendPasswordResetTokenRequest implements Request
{
  public String email;

  public SendPasswordResetTokenRequest(String email)
  {
    this.email = email;
  }

  @Override
  public boolean isValid()
  {
    if (this.email != null) return true;

    return false;
  }
}
