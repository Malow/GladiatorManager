package com.gladiatormanager.game;

public class NameGenerator
{
  public static String[] FIRST_NAMES = { "Vallirn", "Esum", "Bren", "Danan", "Tavod" };
  public static String[] LAST_NAMES = { "Dirgewater", "Glowhorn", "Wolfoak", "Heim", "Raincleaver" };

  public static String FirstAndLastName()
  {
    String firstName = FIRST_NAMES[Dice.dx(FIRST_NAMES.length) - 1];
    String lastName = LAST_NAMES[Dice.dx(LAST_NAMES.length) - 1];
    return firstName + " " + lastName;
  }
}