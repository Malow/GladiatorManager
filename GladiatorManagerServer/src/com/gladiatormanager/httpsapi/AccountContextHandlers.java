package com.gladiatormanager.httpsapi;

import static com.gladiatormanager.httpsapi.ContextHelpers.getRequest;
import static com.gladiatormanager.httpsapi.ContextHelpers.sendMessage;

import com.gladiatormanager.AccountHandler;
import com.gladiatormanager.comstructs.ErrorResponse;
import com.gladiatormanager.comstructs.LoginRequest;
import com.gladiatormanager.comstructs.RegisterRequest;
import com.gladiatormanager.comstructs.ResetPasswordRequest;
import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.comstructs.SendPasswordResetTokenRequest;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class AccountContextHandlers
{
  static class LoginHandler implements HttpHandler
  {
    @Override
    public void handle(HttpExchange t)
    {
      LoginRequest req = (LoginRequest) getRequest(t, LoginRequest.class);
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
    public void handle(HttpExchange t)
    {
      RegisterRequest req = (RegisterRequest) getRequest(t, RegisterRequest.class);
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
    public void handle(HttpExchange t)
    {
      SendPasswordResetTokenRequest req = (SendPasswordResetTokenRequest) getRequest(t, SendPasswordResetTokenRequest.class);
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
    public void handle(HttpExchange t)
    {
      ResetPasswordRequest req = (ResetPasswordRequest) getRequest(t, ResetPasswordRequest.class);
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
}
