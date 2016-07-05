package com.gladiatormanager.account;

import java.util.UUID;

import com.gladiatormanager.Globals;
import com.gladiatormanager.Password;
import com.gladiatormanager.account.comstructs.LoginRequest;
import com.gladiatormanager.account.comstructs.LoginResponse;
import com.gladiatormanager.account.comstructs.RegisterRequest;
import com.gladiatormanager.account.comstructs.ResetPasswordRequest;
import com.gladiatormanager.comstructs.ErrorResponse;
import com.gladiatormanager.comstructs.Request;
import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.database.AccountAccessor;
import com.gladiatormanager.database.AccountAccessor.AccountNotFoundException;
import com.gladiatormanager.database.AccountAccessor.EmailAlreadyExistsException;
import com.gladiatormanager.database.AccountAccessor.UsernameAlreadyExistsException;
import com.gladiatormanager.database.Database.UnexpectedException;

public class AccountHandler
{
  public static Response login(LoginRequest req)
  {
    try
    {
      String authToken = UUID.randomUUID().toString();
      String dbpw = AccountAccessor.read(req.email).password;
      if (Password.checkPassword(req.password, dbpw))
      {
        AccountAccessor.setAuthToken(req.email, authToken);
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
      AccountAccessor.create(req.email, req.username, Password.hashPassword(req.password), authToken);
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
    catch (UnexpectedException e)
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

  public static Response sendPasswordResetToken(Request req)
  {
    try
    {
      String pwResetToken = UUID.randomUUID().toString();
      AccountAccessor.setPasswordResetToken(req.email, pwResetToken);
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
      String dbPwResetToken = AccountAccessor.read(req.email).pwResetToken;
      if (dbPwResetToken.equals(req.pwResetToken))
      {
        String authToken = UUID.randomUUID().toString();
        AccountAccessor.setPassword(req.email, Password.hashPassword(req.password), authToken);
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
