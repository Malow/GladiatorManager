package com.gladiatormanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import com.gladiatormanager.Password;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class SqlDatabase implements Database
{
  private Connection connection = null;

  public void start()
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      this.connection = DriverManager.getConnection("jdbc:mysql://localhost/GladiatorManager?" + "user=GMS&password=password");
    }
    catch (Exception e)
    {
      System.out.println("Error while trying to start SQL connection: " + e.toString());
      e.printStackTrace();
    }
  }

  public void close()
  {
    if (this.connection != null) try
    {
      this.connection.close();
    }
    catch (Exception e)
    {
      System.out.println("Error while trying to close SQL connection: " + e.toString());
      e.printStackTrace();
    }
  }

  public String loginAndGetAuthToken(String email, String password) throws UnexpectedException, WrongPasswordException
  {
    try
    {
      PreparedStatement accountStatement = this.connection.prepareStatement("SELECT * FROM Accounts WHERE email = ? ; ");
      accountStatement.setString(1, email);
      ResultSet accountResult = accountStatement.executeQuery();

      if (accountResult.next())
      {
        String dbpw = accountResult.getString("password");
        if (Password.checkPassword(password, dbpw))
        {
          String sessionId = UUID.randomUUID().toString();
          PreparedStatement setSessionStatement = this.connection.prepareStatement("UPDATE Accounts SET session_id = ? WHERE email = ? ; ");
          setSessionStatement.setString(1, sessionId);
          setSessionStatement.setString(2, email);
          setSessionStatement.executeUpdate();
          setSessionStatement.close();
          accountStatement.close();
          accountResult.close();
          return sessionId;
        }
      }
      accountStatement.close();
      accountResult.close();
    }
    catch (Exception e)
    {
      UnexpectedException ue = new UnexpectedException(e.toString());
      ue.setStackTrace(e.getStackTrace());
      throw ue;
    }
    throw new WrongPasswordException();
  }

  public String registerAndGetAuthToken(String email, String username, String password)
      throws UnexpectedException, EmailAlreadyExistsException, UsernameAlreadyExistsException
  {
    try
    {
      String sessionId = UUID.randomUUID().toString();
      PreparedStatement newAccountStatement = this.connection.prepareStatement("insert into GladiatorManager.Accounts values (default, ?, ?, ?, ?);");
      newAccountStatement.setString(1, username);
      newAccountStatement.setString(2, Password.hashPassword(password));
      newAccountStatement.setString(3, email);
      newAccountStatement.setString(4, sessionId);
      newAccountStatement.executeUpdate();
      newAccountStatement.close();
      return sessionId;
    }
    catch (MySQLIntegrityConstraintViolationException e)
    {
      if (e.toString().contains("'email'")) throw new EmailAlreadyExistsException();
      else if (e.toString().contains("'username'")) throw new UsernameAlreadyExistsException();
      else
      {
        UnexpectedException ue = new UnexpectedException(e.toString());
        ue.setStackTrace(e.getStackTrace());
        throw ue;
      }
    }
    catch (Exception e)
    {
      UnexpectedException ue = new UnexpectedException(e.toString());
      ue.setStackTrace(e.getStackTrace());
      throw ue;
    }
  }
}
