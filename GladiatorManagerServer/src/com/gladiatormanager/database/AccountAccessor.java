package com.gladiatormanager.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gladiatormanager.account.Account;
import com.gladiatormanager.database.Database.UnexpectedException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class AccountAccessor
{
  // Exceptions
  public static class EmailAlreadyExistsException extends Exception
  {
    private static final long serialVersionUID = 2L;
  }

  public static class UsernameAlreadyExistsException extends Exception
  {
    private static final long serialVersionUID = 3L;
  }

  public static class WrongAuthentificationTokenException extends Exception
  {
    private static final long serialVersionUID = 4L;
  }

  public static class AccountNotFoundException extends Exception
  {
    private static final long serialVersionUID = 5L;
  }

  // CRUD Methods
  public static Account read(String email) throws UnexpectedException, AccountNotFoundException
  {
    try
    {
      PreparedStatement s1 = Database.getConnection().prepareStatement("SELECT * FROM Accounts WHERE email = ? ; ");
      s1.setString(1, email);
      ResultSet s1Res = s1.executeQuery();

      if (s1Res.next())
      {
        int id = s1Res.getInt("id");
        String username = s1Res.getString("username");
        String password = s1Res.getString("password");
        String db_email = s1Res.getString("email");
        String db_teamName = s1Res.getString("team_name");
        int db_state = s1Res.getInt("state");
        String authToken = s1Res.getString("auth_token");
        String pwResetToken = s1Res.getString("pw_reset_token");

        Account acc = new Account(id, username, password, db_email, db_teamName, db_state, authToken, pwResetToken);
        s1Res.close();
        s1.close();
        return acc;
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

  public static boolean create(Account acc) throws UnexpectedException, EmailAlreadyExistsException, UsernameAlreadyExistsException
  {
    try
    {
      PreparedStatement s = Database.getConnection().prepareStatement("insert into GladiatorManager.Accounts values (default, ?, ?, ?, ?, ?);");
      int i = 1;
      s.setString(i++, acc.username);
      s.setString(i++, acc.password);
      s.setString(i++, acc.email);
      s.setString(i++, acc.teamName);
      s.setInt(i++, acc.state);
      s.setString(i++, acc.authToken);
      s.setString(i++, acc.pwResetToken);
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

  public static boolean update(Account acc) throws UnexpectedException, AccountNotFoundException
  {
    try
    {
      PreparedStatement s1 = Database.getConnection().prepareStatement(
          "UPDATE Accounts SET username = ?, password = ?, email = ?, team_name = ?, state = ?, auth_token = ?, pw_reset_token = ? WHERE id = ?;");
      int i = 1;
      s1.setString(i++, acc.username);
      s1.setString(i++, acc.password);
      s1.setString(i++, acc.email);
      s1.setString(i++, acc.teamName);
      s1.setInt(i++, acc.state);
      s1.setString(i++, acc.authToken);
      s1.setString(i++, acc.pwResetToken);
      s1.setInt(i++, acc.id);
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

  // Optimized specific methods
  public static boolean setPasswordResetToken(String email, String pwResetToken) throws UnexpectedException, AccountNotFoundException
  {
    try
    {
      PreparedStatement s1 = Database.getConnection().prepareStatement("UPDATE Accounts SET pw_reset_token = ? WHERE email = ?;");
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

  public static boolean setAuthToken(String email, String authToken) throws UnexpectedException, AccountNotFoundException
  {
    try
    {
      PreparedStatement s1 = Database.getConnection().prepareStatement("UPDATE Accounts SET auth_token = ? WHERE email = ? ; ");
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

  public static boolean setPassword(String email, String password, String authToken) throws UnexpectedException, AccountNotFoundException
  {
    try
    {
      PreparedStatement s1 = Database.getConnection()
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
