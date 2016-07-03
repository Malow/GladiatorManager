package com.gladiatormanager.comstructs;

public class SendPasswordResetTokenRequest
{
  public String email;

  public SendPasswordResetTokenRequest(String email)
  {
    this.email = email;
  }

  public boolean validate()
  {
    if (this.email != null) return true;

    return false;
  }
}
