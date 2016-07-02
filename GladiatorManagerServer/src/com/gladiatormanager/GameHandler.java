package com.gladiatormanager;

import com.gladiatormanager.comstructs.ErrorResponse;
import com.gladiatormanager.comstructs.LoginRequest;
import com.gladiatormanager.comstructs.LoginResponse;
import com.gladiatormanager.comstructs.RegisterRequest;
import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.database.Database.EmailAlreadyExistsException;
import com.gladiatormanager.database.Database.UnexpectedException;
import com.gladiatormanager.database.Database.UsernameAlreadyExistsException;
import com.gladiatormanager.database.Database.WrongPasswordException;

public class GameHandler
{
  public static Response login(LoginRequest req)
  {
    if (req == null || !req.validate())
    {
      System.out.println("Failed Login-request, wrong parameters");
      return new ErrorResponse(false, "Request has wrong parameters");
    }

    try
    {
      System.out.println("New login request received for: " + req.email + " with pw: " + req.password);
      String authToken = Globals.database.loginAndGetAuthToken(req.email, req.password);
      return new LoginResponse(true, authToken);
    }
    catch (WrongPasswordException e)
    {
      return new ErrorResponse(false, "Wrong password");
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
    if (req == null || !req.validate())
    {
      System.out.println("Failed Register-request, wrong parameters");
      return new ErrorResponse(false, "Request has wrong parameters");
    }

    try
    {
      System.out.println("New register request received for: " + req.email + " with username: " + req.username
          + " with pw: " + req.password);
      String authToken = Globals.database.registerAndGetAuthToken(req.email, req.username, req.password);
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
      System.out.println("Unexpected error when trying to register: " + e.error);
      e.printStackTrace();
      return new ErrorResponse(false, "Unexpected error");
    }
  }
}
