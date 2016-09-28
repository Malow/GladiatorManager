package com.gladiatormanager.game;

import java.util.ArrayList;
import java.util.List;

import com.gladiatormanager.Globals;
import com.gladiatormanager.account.Account;
import com.gladiatormanager.comstructs.AuthorizedRequest;
import com.gladiatormanager.comstructs.ErrorResponse;
import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.comstructs.game.ChooseInitialMercenariesRequest;
import com.gladiatormanager.comstructs.game.GetMercenariesResponse;
import com.gladiatormanager.database.AccountAccessor;
import com.gladiatormanager.database.MercenaryAccessor;

public class GameHandler
{
  public static Response getMercenaries(AuthorizedRequest req)
  {
    try
    {
      int accId = AccountAccessor.read(req.email).id;
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

  public static Response chooseInitialMercenaries(ChooseInitialMercenariesRequest req)
  {
    try
    {
      if (req.mercenariesIds.size() != Globals.Config.STARTER_MERCENARIES_TO_BE_CHOSEN)
        throw new Exception("Amount of mercenariesIds does not match the correct amount.");

      Account acc = AccountAccessor.read(req.email);
      if (acc.state == Account.State.HAS_TEAMNAME)
      {
        List<Mercenary> mercenaries = MercenaryAccessor.getMercenariesForAccount(acc.id);
        List<Mercenary> mercenariesToBeKept = new ArrayList<Mercenary>();
        for (Integer mercenaryId : req.mercenariesIds)
        {
          boolean found = false;
          for (Mercenary mercenary : mercenaries)
          {
            if (mercenary.id == mercenaryId)
            {
              if (found) throw new Exception("Found multiple mercenaries for an id.");
              mercenariesToBeKept.add(mercenary);
              found = true;
            }
          }
          if (!found) throw new Exception("Didn't find any mercenaries for an id.");
        }

        List<Mercenary> mercenariesToBeDeleted = new ArrayList<Mercenary>(mercenaries);
        for (Mercenary mercenary : mercenariesToBeKept)
        {
          mercenariesToBeDeleted.remove(mercenary);
        }

        for (Mercenary mercenary : mercenariesToBeDeleted)
        {
          MercenaryAccessor.delete(mercenary);
        }

        acc.state = Account.State.IS_ACTIVE;
        AccountAccessor.update(acc);

        return new Response(true);
      }
      else
      {
        throw new Exception("Account state is incorrect for this action: " + acc.state);
      }
    }
    catch (Exception e)
    {
      System.out.println("Unexpected error when trying to pick initial mercenaries: " + e.toString());
      e.printStackTrace();
      return new ErrorResponse(false, "Unexpected error");
    }
  }

}
