package com.gladiatormanager.game;

public class Mercenary
{
  public int id;
  public String name;
  public int level;
  public int xp;
  public int age;
  public int strength;
  public int agility;
  public int constitution;
  public int intelligence;
  public int willpower;

  public Mercenary(int id, String name, int level, int xp, int age, int strength, int agility, int constitution, int intelligence, int willpower)
  {
    this.id = id;
    this.name = name;
    this.level = level;
    this.xp = xp;
    this.age = age;
    this.strength = strength;
    this.agility = agility;
    this.constitution = constitution;
    this.intelligence = intelligence;
    this.willpower = willpower;
  }

  public int GetStatScore()
  {
    return this.strength + this.agility + this.constitution + this.intelligence + this.willpower;
  }

  public static Mercenary GenerateRandom()
  {
    int id = -1;
    String name = NameGenerator.FirstAndLastName();
    int level = 1;
    int xp = 0;
    int age = Dice.dx(20) + 17;
    int strength = Dice.ydxdlz(4, 6, 1);
    int agility = Dice.ydxdlz(4, 6, 1);
    int constitution = Dice.ydxdlz(4, 6, 1);
    int intelligence = Dice.ydxdlz(4, 6, 1);
    int willpower = Dice.ydxdlz(4, 6, 1);
    return new Mercenary(id, name, level, xp, age, strength, agility, constitution, intelligence, willpower);
  }

  @Override
  public boolean equals(Object o)
  {
    return ((Mercenary) o).id == this.id;
  }
}
