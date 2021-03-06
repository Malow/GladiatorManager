package com.gladiatormanager.httpsapi;

import static com.gladiatormanager.httpsapi.ContextHelpers.sendMessage;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.gladiatormanager.comstructs.Response;
import com.gladiatormanager.httpsapi.AccountContextHandlers.LoginHandler;
import com.gladiatormanager.httpsapi.AccountContextHandlers.RegisterHandler;
import com.gladiatormanager.httpsapi.AccountContextHandlers.ResetPasswordHandler;
import com.gladiatormanager.httpsapi.AccountContextHandlers.SendPasswordResetTokenHandler;
import com.gladiatormanager.httpsapi.AccountContextHandlers.SetTeamNameHandler;
import com.gladiatormanager.httpsapi.GameContextHandlers.ChooseInitialMercenariesHandler;
import com.gladiatormanager.httpsapi.GameContextHandlers.GetMercenariesHandler;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

public class HttpsApiServer
{
  static class TestHandler implements HttpHandler
  {
    @Override
    public void handle(HttpExchange t)
    {
      sendMessage(t, 200, new Gson().toJson(new Response(true)));
    }
  }

  private HttpsServer server = null;

  public HttpsApiServer()
  {
  }

  public void start(int port, String sslPassword)
  {
    this.initHttpsServer(port, sslPassword);
    this.server.createContext("/test", new TestHandler());
    // AccountContextHandlers
    this.server.createContext("/login", new LoginHandler());
    this.server.createContext("/register", new RegisterHandler());
    this.server.createContext("/sendpwresettoken", new SendPasswordResetTokenHandler());
    this.server.createContext("/resetpw", new ResetPasswordHandler());
    this.server.createContext("/setteamname", new SetTeamNameHandler());

    //GameContextHandlers
    this.server.createContext("/getmercenaries", new GetMercenariesHandler());
    this.server.createContext("/chooseinitialmercenaries", new ChooseInitialMercenariesHandler());
    this.server.start();
  }

  public void close()
  {
    if (this.server != null) this.server.stop(0);
  }

  public void initHttpsServer(int port, String sslPassword)
  {
    try
    {
      this.server = HttpsServer.create(new InetSocketAddress(port), 0);
      SSLContext sslContext = SSLContext.getInstance("TLS");
      char[] password = sslPassword.toCharArray();
      KeyStore ks = KeyStore.getInstance("JKS");
      FileInputStream fis = new FileInputStream("https_key.jks");
      ks.load(fis, password);
      KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
      kmf.init(ks, password);
      TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
      tmf.init(ks);
      sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
      this.server.setHttpsConfigurator(new HttpsConfigurator(sslContext)
      {
        @Override
        public void configure(HttpsParameters params)
        {
          try
          {
            SSLContext c = SSLContext.getDefault();
            SSLEngine engine = c.createSSLEngine();
            params.setNeedClientAuth(false);
            params.setCipherSuites(engine.getEnabledCipherSuites());
            params.setProtocols(engine.getEnabledProtocols());
            SSLParameters defaultSSLParameters = c.getDefaultSSLParameters();
            params.setSSLParameters(defaultSSLParameters);
          }
          catch (Exception ex)
          {
            System.out.println("Failed to create HTTPS port");
          }
        }
      });
      this.server.setExecutor(null); // creates a default executor
    }
    catch (Exception e)
    {
      System.out.println("Exception while starting RequestListener.");
      e.printStackTrace();
    }
  }
}
