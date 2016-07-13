package com.gladiatormanager.httpsapi;

import static com.gladiatormanager.httpsapi.ContextHelpers.getValidRequest;
import static com.gladiatormanager.httpsapi.ContextHelpers.sendMessage;

import java.io.IOException;

import com.gladiatormanager.account.AccountHandler;
import com.gladiatormanager.account.comstructs.LoginRequest;
import com.gladiatormanager.account.comstructs.RegisterRequest;
import com.gladiatormanager.account.comstructs.ResetPasswordRequest;
import com.gladiatormanager.account.comstructs.SetTeamNameRequest;
import com.gladiatormanager.comstructs.ErrorResponse;
import com.gladiatormanager.comstructs.Request;
import com.gladiatormanager.comstructs.Response;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class AccountContextHandlers
{
  static class LoginHandler implements HttpHandler
  {
    @Override
    public void handle(HttpExchange t) throws IOException
    {
      LoginRequest req = (LoginRequest) getValidRequest(t, LoginRequest.class);
      if (req != null)
      {
        Response resp = AccountHandler.login(req);
        sendMessage(t, 200, new Gson().toJson(resp));
      }
      else
      {
        sendMessage(t, 200, new Gson().toJson(new ErrorResponse(false, "Request has wrong parameters")));
      }
    }
  }

  static class RegisterHandler implements HttpHandler
  {
    @Override
    public void handle(HttpExchange t) throws IOException
    {
      RegisterRequest req = (RegisterRequest) getValidRequest(t, RegisterRequest.class);
      if (req != null)
      {
        Response resp = AccountHandler.register(req);
        sendMessage(t, 200, new Gson().toJson(resp));
      }
      else
      {
        sendMessage(t, 200, new Gson().toJson(new ErrorResponse(false, "Request has wrong parameters")));
      }
    }
  }

  static class SendPasswordResetTokenHandler implements HttpHandler
  {
    @Override
    public void handle(HttpExchange t) throws IOException
    {
      Request req = getValidRequest(t, Request.class);
      if (req != null)
      {
        Response resp = AccountHandler.sendPasswordResetToken(req);
        sendMessage(t, 200, new Gson().toJson(resp));
      }
      else
      {
        sendMessage(t, 200, new Gson().toJson(new ErrorResponse(false, "Request has wrong parameters")));
      }
    }
  }

  static class ResetPasswordHandler implements HttpHandler
  {
    @Override
    public void handle(HttpExchange t) throws IOException
    {
      ResetPasswordRequest req = (ResetPasswordRequest) getValidRequest(t, ResetPasswordRequest.class);
      if (req != null)
      {
        Response resp = AccountHandler.resetPassword(req);
        sendMessage(t, 200, new Gson().toJson(resp));
      }
      else
      {
        sendMessage(t, 200, new Gson().toJson(new ErrorResponse(false, "Request has wrong parameters")));
      }
    }
  }

  static class SetTeamNameHandler implements HttpHandler
  {
    @Override
    public void handle(HttpExchange t) throws IOException
    {
      SetTeamNameRequest req = (SetTeamNameRequest) getValidRequest(t, SetTeamNameRequest.class);
      if (req != null)
      {
        Response resp = AccountHandler.setTeamName(req);
        sendMessage(t, 200, new Gson().toJson(resp));
      }
      else
      {
        sendMessage(t, 200, new Gson().toJson(new ErrorResponse(false, "Request has wrong parameters")));
      }
    }
  }
}
