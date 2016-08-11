package com.gladiatormanager.comstructs.game;

import java.util.List;

import com.gladiatormanager.comstructs.AuthorizedRequest;

public class ChooseInitialMercenariesRequest extends AuthorizedRequest
{
  public List<Integer> mercenariesIds;

  public ChooseInitialMercenariesRequest(String email, String authToken, List<Integer> mercenariesIds)
  {
    super(email, authToken);
    this.mercenariesIds = mercenariesIds;
  }

}
