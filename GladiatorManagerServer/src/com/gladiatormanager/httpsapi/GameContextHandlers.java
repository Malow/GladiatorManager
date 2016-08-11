package com.gladiatormanager.httpsapi;

import static com.gladiatormanager.httpsapi.ContextHelpers.getValidRequest;
import static com.gladiatormanager.httpsapi.ContextHelpers.sendMessage;

import com.gladiatormanager.comstructs.AuthorizedRequest;
import com.gladiatormanager.comstructs.ErrorResponse;
import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.comstructs.game.ChooseInitialMercenariesRequest;
import com.gladiatormanager.game.GameHandler;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GameContextHandlers
{
  static class GetMercenariesHandler implements HttpHandler
  {
    @Override
    public void handle(HttpExchange t)
    {
      AuthorizedRequest req = (AuthorizedRequest) getValidRequest(t, AuthorizedRequest.class);
      if (req != null)
      {
        Response resp = GameHandler.getMercenaries(req);
        sendMessage(t, 200, new Gson().toJson(resp));
      }
      else
      {
        sendMessage(t, 200, new Gson().toJson(new ErrorResponse(false, "Request has wrong parameters")));
      }
    }
  }

  static class ChooseInitialMercenariesHandler implements HttpHandler
  {
    @Override
    public void handle(HttpExchange t)
    {
      ChooseInitialMercenariesRequest req = (ChooseInitialMercenariesRequest) getValidRequest(t, ChooseInitialMercenariesRequest.class);
      if (req != null)
      {
        Response resp = GameHandler.chooseInitialMercenaries(req);
        sendMessage(t, 200, new Gson().toJson(resp));
      }
      else
      {
        sendMessage(t, 200, new Gson().toJson(new ErrorResponse(false, "Request has wrong parameters")));
      }
    }
  }
}
