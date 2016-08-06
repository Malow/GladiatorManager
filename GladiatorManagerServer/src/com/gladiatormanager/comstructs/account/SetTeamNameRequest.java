package com.gladiatormanager.comstructs.account;

import com.gladiatormanager.comstructs.AuthorizedRequest;

public class SetTeamNameRequest extends AuthorizedRequest
{
  public String teamName;

  public SetTeamNameRequest(String email, String authToken, String teamName)
  {
    super(email, authToken);
    this.teamName = teamName;
  }

  @Override
  public boolean isValid()
  {
    if (super.isValid() && this.teamName != null) return true;

    return false;
  }
}