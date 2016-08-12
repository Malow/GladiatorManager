package com.gladiatormanager.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Dice
{

  public static int dx(int x)
  {
    Random rand = new Random();
    return rand.nextInt(x) + 1;
  }

  public static int ydx(int y, int x)
  {
    Random rand = new Random();
    int value = 0;
    for (int i = 0; i < y; i++)
    {
      value += rand.nextInt(x) + 1;
    }
    return value;
  }

  public static int ydxdlz(int y, int x, int z)
  {
    Random rand = new Random();
    LinkedList<Integer> values = new LinkedList<Integer>();
    for (int i = 0; i < y; i++)
    {
      values.add(rand.nextInt(x) + 1);
    }
    Collections.sort(values);
    for (int i = 0; i < z; i++)
    {
      values.removeFirst();
    }
    int totalValue = 0;
    for (Integer value : values)
    {
      totalValue += value;
    }
    return totalValue;
  }
}