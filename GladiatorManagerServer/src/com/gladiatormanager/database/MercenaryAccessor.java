package com.gladiatormanager.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gladiatormanager.database.Database.UnexpectedException;
import com.gladiatormanager.game.Mercenary;

public class MercenaryAccessor
{
  public static List<Mercenary> getMercenariesForAccount(int accId) throws UnexpectedException
  {
    try
    {
      PreparedStatement s1 = Database.getConnection().prepareStatement("SELECT * FROM Mercenaries WHERE account_id = ? ; ");
      s1.setInt(1, accId);
      ResultSet s1Res = s1.executeQuery();

      List<Mercenary> chars = new ArrayList<Mercenary>();

      while (s1Res.next())
      {
        int id = s1Res.getInt("id");
        String name = s1Res.getString("name");
        int level = s1Res.getInt("level");
        int xp = s1Res.getInt("xp");
        int age = s1Res.getInt("age");
        int strength = s1Res.getInt("strength");
        int agility = s1Res.getInt("agility");
        int constitution = s1Res.getInt("constitution");
        int intelligence = s1Res.getInt("intelligence");
        int willpower = s1Res.getInt("willpower");

        Mercenary c = new Mercenary(id, name, level, xp, age, strength, agility, constitution, intelligence, willpower);
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
