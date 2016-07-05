package com.gladiatormanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class SqlDatabase implements Database
{
  private Connection connection = null;

  @Override
  public void start()
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      this.connection = DriverManager.getConnection("jdbc:mysql://localhost/GladiatorManager?" + "user=GMS&password=password&autoReconnect=true");
    }
    catch (Exception e)
    {
      System.out.println("Error while trying to start SQL connection: " + e.toString());
      e.printStackTrace();
    }
  }

  @Override
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

  @Override
  public String getPasswordForAccount(String email) throws UnexpectedException, AccountNotFoundException
  {
    try
    {
      PreparedStatement s1 = this.connection.prepareStatement("SELECT * FROM Accounts WHERE email = ? ; ");
      s1.setString(1, email);
      ResultSet s1Res = s1.executeQuery();

      if (s1Res.next())
      {
        String dbpw = s1Res.getString("password");
        s1Res.close();
        s1.close();
        return dbpw;
      }
    }
    catch (Exception e)
    {
      UnexpectedException ue = new UnexpectedException(e.toString());
      ue.setStackTrace(e.getStackTrace());
      throw ue;
    }
    throw new AccountNotFoundException();
  }

  @Override
  public boolean createAccount(String email, String username, String password, String authToken)
      throws UnexpectedException, EmailAlreadyExistsException, UsernameAlreadyExistsException
  {
    try
    {
      PreparedStatement s = this.connection.prepareStatement("insert into GladiatorManager.Accounts values (default, ?, ?, ?, ?, null);");
      s.setString(1, username);
      s.setString(2, password);
      s.setString(3, email);
      s.setString(4, authToken);
      s.executeUpdate();
      s.close();
      return true;
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

  @Override
  public boolean setPasswordResetTokenForAccount(String email, String pwResetToken) throws UnexpectedException, AccountNotFoundException
  {
    try
    {
      PreparedStatement s1 = this.connection.prepareStatement("UPDATE Accounts SET pw_reset_token = ? WHERE email = ?;");
      s1.setString(1, pwResetToken);
      s1.setString(2, email);
      int rowCount = s1.executeUpdate();
      s1.close();

      if (rowCount == 1) return true;
    }
    catch (Exception e)
    {
      UnexpectedException ue = new UnexpectedException(e.toString());
      ue.setStackTrace(e.getStackTrace());
      throw ue;
    }
    throw new AccountNotFoundException();
  }

  @Override
  public boolean setAuthTokenForAccount(String email, String authToken) throws UnexpectedException, AccountNotFoundException
  {
    try
    {
      PreparedStatement s1 = this.connection.prepareStatement("UPDATE Accounts SET auth_token = ? WHERE email = ? ; ");
      s1.setString(1, authToken);
      s1.setString(2, email);
      int rowCount = s1.executeUpdate();
      s1.close();

      if (rowCount == 1) return true;
    }
    catch (Exception e)
    {
      UnexpectedException ue = new UnexpectedException(e.toString());
      ue.setStackTrace(e.getStackTrace());
      throw ue;
    }
    throw new AccountNotFoundException();
  }

  @Override
  public String getPasswordResetTokenForAccount(String email) throws UnexpectedException, AccountNotFoundException
  {
    try
    {
      PreparedStatement s1 = this.connection.prepareStatement("SELECT * FROM Accounts WHERE email = ? ; ");
      s1.setString(1, email);
      ResultSet s1Res = s1.executeQuery();

      if (s1Res.next())
      {
        String pwResetToken = s1Res.getString("pw_reset_token");
        s1Res.close();
        s1.close();
        return pwResetToken;
      }
    }
    catch (Exception e)
    {
      UnexpectedException ue = new UnexpectedException(e.toString());
      ue.setStackTrace(e.getStackTrace());
      throw ue;
    }
    throw new AccountNotFoundException();
  }

  @Override
  public boolean setPasswordForAccount(String email, String password, String authToken) throws UnexpectedException, AccountNotFoundException
  {
    try
    {
      PreparedStatement s1 = this.connection
          .prepareStatement("UPDATE Accounts SET password = ?, auth_token = ?, pw_reset_token = null WHERE email = ?;");
      s1.setString(1, password);
      s1.setString(2, authToken);
      s1.setString(3, email);
      int rowCount = s1.executeUpdate();
      s1.close();

      if (rowCount == 1) return true;
    }
    catch (Exception e)
    {
      UnexpectedException ue = new UnexpectedException(e.toString());
      ue.setStackTrace(e.getStackTrace());
      throw ue;
    }
    throw new AccountNotFoundException();
  }
}
