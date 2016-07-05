package com.gladiatormanager;

import java.util.UUID;

import com.gladiatormanager.comstructs.ErrorResponse;
import com.gladiatormanager.comstructs.LoginRequest;
import com.gladiatormanager.comstructs.LoginResponse;
import com.gladiatormanager.comstructs.RegisterRequest;
import com.gladiatormanager.comstructs.ResetPasswordRequest;
import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.comstructs.SendPasswordResetTokenRequest;
import com.gladiatormanager.database.Database;
import com.gladiatormanager.database.Database.AccountNotFoundException;
import com.gladiatormanager.database.Database.EmailAlreadyExistsException;
import com.gladiatormanager.database.Database.UnexpectedException;
import com.gladiatormanager.database.Database.UsernameAlreadyExistsException;

public class AccountHandler
{
  public static Response login(LoginRequest req)
  {
    try
    {
      String authToken = UUID.randomUUID().toString();
      String dbpw = Globals.database.getPasswordForAccount(req.email);
      if (Password.checkPassword(req.password, dbpw))
      {
        Globals.database.setAuthTokenForAccount(req.email, authToken);
        return new LoginResponse(true, authToken);
      }
      return new ErrorResponse(false, "Wrong account details");
    }
    catch (AccountNotFoundException e)
    {
      return new ErrorResponse(false, "Wrong account details");
    }
    catch (UnexpectedException e)
    {
      System.out.println("Unexpected error when trying to login: " + e.toString());
      e.printStackTrace();
      return new ErrorResponse(false, "Unexpected error");
    }
  }

  public static Response register(RegisterRequest req)
  {
    try
    {
      String authToken = UUID.randomUUID().toString();
      Globals.database.createAccount(req.email, req.username, Password.hashPassword(req.password), authToken);
      return new LoginResponse(true, authToken);
    }
    catch (EmailAlreadyExistsException e)
    {
      return new ErrorResponse(false, "Email already in use");
    }
    catch (UsernameAlreadyExistsException e)
    {
      return new ErrorResponse(false, "Username already taken");
    }
    catch (Database.UnexpectedException e)
    {
      System.out.println("Unexpected Database error when trying to register: " + e.error);
      e.printStackTrace();
      return new ErrorResponse(false, "Unexpected error");
    }
    catch (Exception e)
    {
      System.out.println("Unexpected exception when trying to register: " + e.toString());
      e.printStackTrace();
      return new ErrorResponse(false, "Unexpected error");
    }
  }

  public static Response sendPasswordResetToken(SendPasswordResetTokenRequest req)
  {
    try
    {
      String pwResetToken = UUID.randomUUID().toString();
      Globals.database.setPasswordResetTokenForAccount(req.email, pwResetToken);
      Globals.email.sendMail(req.email, "Your password reset for GladiatorManager", pwResetToken);
      return new Response(true);
    }
    catch (AccountNotFoundException e)
    {
      return new ErrorResponse(false, "No account found for that email-address");
    }
    catch (UnexpectedException e)
    {
      System.out.println("Unexpected error when trying to sendPasswordResetToken: " + e.toString());
      e.printStackTrace();
      return new ErrorResponse(false, "Unexpected error");
    }
  }

  public static Response resetPassword(ResetPasswordRequest req)
  {
    try
    {
      String dbPwResetToken = Globals.database.getPasswordResetTokenForAccount(req.email);
      if (dbPwResetToken.equals(req.pwResetToken))
      {
        String authToken = UUID.randomUUID().toString();
        Globals.database.setPasswordForAccount(req.email, Password.hashPassword(req.password), authToken);
        return new LoginResponse(true, authToken);
      }
      return new ErrorResponse(false, "Bad password-reset token.");
    }
    catch (AccountNotFoundException e)
    {
      return new ErrorResponse(false, "No account found for that email-address");
    }
    catch (UnexpectedException e)
    {
      System.out.println("Unexpected error when trying to sendPasswordResetToken: " + e.toString());
      e.printStackTrace();
      return new ErrorResponse(false, "Unexpected error");
    }
  }
}
