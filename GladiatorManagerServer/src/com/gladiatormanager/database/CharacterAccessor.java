package com.gladiatormanager.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gladiatormanager.database.Database.UnexpectedException;
import com.gladiatormanager.game.Character;

public class CharacterAccessor
{
  public static List<Character> getCharactersForAccount(int accId) throws UnexpectedException
  {
    try
    {
      PreparedStatement s1 = Database.getConnection().prepareStatement("SELECT * FROM Characters WHERE account_id = ? ; ");
      s1.setInt(1, accId);
      ResultSet s1Res = s1.executeQuery();

      List<Character> chars = new ArrayList<Character>();

      while (s1Res.next())
      {
        int id = s1Res.getInt("id");
        String name = s1Res.getString("name");
        int level = s1Res.getInt("level");
        int xp = s1Res.getInt("xp");
        int age = s1Res.getInt("age");
        int strength = s1Res.getInt("strength");
        int dexterity = s1Res.getInt("dexterity");
        int constitution = s1Res.getInt("constitution");
        int intelligence = s1Res.getInt("intelligence");
        int willpower = s1Res.getInt("willpower");

        Character c = new Character(id, name, level, xp, age, strength, dexterity, constitution, intelligence, willpower);
        chars.add(c);
      }
      s1Res.close();
      s1.close();
      return chars;
    }
    catch (Exception e)
    {
      UnexpectedException ue = new UnexpectedException(e.toString());
      ue.setStackTrace(e.getStackTrace());
      throw ue;
    }
  }

}
