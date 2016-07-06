package com.gladiatormanager.game;

import java.util.List;

import com.gladiatormanager.comstructs.AuthorizedRequest;
import com.gladiatormanager.comstructs.ErrorResponse;
import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.database.AccountAccessor;
import com.gladiatormanager.database.MercenaryAccessor;
import com.gladiatormanager.game.comstructs.GetMercenariesResponse;

public class GameHandler
{
  public static Response getMercenaries(AuthorizedRequest req)
  {
    try
    {
      int accId = AccountAccessor.read(req.email).id; // TODO: Maybe store id's for emails in memory to avoid having to do lookups in DB
      List<Mercenary> mercenaries = MercenaryAccessor.getMercenariesForAccount(accId);
      return new GetMercenariesResponse(true, mercenaries);
    }
    catch (Exception e)
    {
      System.out.println("Unexpected error when trying to get mercenaries: " + e.toString());
      e.printStackTrace();
      return new ErrorResponse(false, "Unexpected error");
    }
  }

}
