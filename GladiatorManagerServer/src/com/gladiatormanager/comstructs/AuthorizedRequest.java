package com.gladiatormanager.comstructs;

import com.gladiatormanager.database.AccountAccessor;

public class AuthorizedRequest extends Request
{
  public String authToken;

  public AuthorizedRequest(String email, String authToken)
  {
    super(email);
    this.authToken = authToken;
  }

  private boolean isAuthorized()
  {
    try
    {
      if (AccountAccessor.read(this.email).authToken.equals(this.authToken)) return true;
    }
    catch (Exception e)
    {
      System.out.println("Unexpected error when trying to authorize request: " + e.toString());
      e.printStackTrace();
    }
    System.out.println("Un-Authorized request recived for: " + this.email);
    return false;
  }

  @Override
  public boolean isValid()
  {
    if (super.isValid() && this.authToken != null && this.isAuthorized()) return true;

    return false;
  }

}
