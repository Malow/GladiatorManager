package com.gladiatormanager.game.comstructs;

import java.util.List;

import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.game.Character;

public class GetCharactersResponse extends Response
{
  public List<Character> characters;

  public GetCharactersResponse(boolean result, List<Character> characters)
  {
    super(result);
    this.characters = characters;
  }
}
