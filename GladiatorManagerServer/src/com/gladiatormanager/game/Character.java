package com.gladiatormanager.game;

public class Character
{
  int id;
  public String name;
  public int level;
  public int xp;
  public int age;
  public int strength;
  public int dexterity;
  public int constitution;
  public int intelligence;
  public int willpower;

  public Character(int id, String name, int level, int xp, int age, int strength, int dexterity, int constitution, int intelligence, int willpower)
  {
    this.id = id;
    this.name = name;
    this.level = level;
    this.xp = xp;
    this.age = age;
    this.strength = strength;
    this.dexterity = dexterity;
    this.constitution = constitution;
    this.intelligence = intelligence;
    this.willpower = willpower;
  }
}
