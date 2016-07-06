package com.gladiatormanager.game.comstructs;

import java.util.List;

import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.game.Mercenary;

public class GetMercenariesResponse extends Response
{
  public List<Mercenary> mercenaries;

  public GetMercenariesResponse(boolean result, List<Mercenary> mercenaries)
  {
    super(result);
    this.mercenaries = mercenaries;
  }
}
