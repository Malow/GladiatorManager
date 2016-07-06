package com.gladiatormanager.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database
{
  public static class UnexpectedException extends Exception
  {
    private static final long serialVersionUID = 1L;
    public String error = null;

    public UnexpectedException(String error)
    {
      this.error = error;
    }

    @Override
    public String toString()
    {
      return this.error;
    }
  }

  private Connection connection = null;
  private static final Database INSTANCE = new Database();

  private Database()
  {
    if (INSTANCE != null) { throw new IllegalStateException("Already instantiated"); }
  }

  public static Connection getConnection()
  {
    return INSTANCE.connection;
  }

  public static void init()
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      INSTANCE.connection = DriverManager.getConnection("jdbc:mysql://localhost/GladiatorManager?" + "user=GMS&password=password&autoReconnect=true");
    }
    catch (Exception e)
    {
      System.out.println("Error while trying to start SQL connection: " + e.toString());
      e.printStackTrace();
    }
  }

  public static void close()
  {
    if (INSTANCE.connection != null) try
    {
      INSTANCE.connection.close();
    }
    catch (Exception e)
    {
      System.out.println("Error while trying to close SQL connection: " + e.toString());
      e.printStackTrace();
    }
  }
}
