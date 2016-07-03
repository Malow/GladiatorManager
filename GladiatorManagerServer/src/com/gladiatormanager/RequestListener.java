package com.gladiatormanager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.gladiatormanager.comstructs.LoginRequest;
import com.gladiatormanager.comstructs.RegisterRequest;
import com.gladiatormanager.comstructs.Response;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

@SuppressWarnings("restriction")
public class RequestListener
{

  private HttpsServer server = null;

  public RequestListener()
  {
  }

  public void start(int port, String sslPassword)
  {
    this.initHttpsServer(port, sslPassword);
    this.server.createContext("/login", new LoginHandler());
    this.server.createContext("/register", new RegisterHandler());
    this.server.start();
  }

  public void close()
  {
    if (this.server != null) this.server.stop(0);
  }

  // Handlers
  static class LoginHandler implements HttpHandler
  {
    public void handle(HttpExchange t)
    {
      try
      {
        String message = getMessage(t);
        LoginRequest req = new Gson().fromJson(message, LoginRequest.class);
        Response resp = GameHandler.login(req);
        sendMessage(t, 200, new Gson().toJson(resp));
      }
      catch (Exception e)
      {
        System.out.println("Exception while handling Login request.");
        e.printStackTrace();
      }
    }
  }

  static class RegisterHandler implements HttpHandler
  {
    public void handle(HttpExchange t)
    {
      try
      {
        String message = getMessage(t);
        RegisterRequest req = new Gson().fromJson(message, RegisterRequest.class);
        Response resp = GameHandler.register(req);
        sendMessage(t, 200, new Gson().toJson(resp));
      }
      catch (Exception e)
      {
        System.out.println("Exception while handling Register request.");
        e.printStackTrace();
      }
    }
  }

  // Helper methods
  public static String getMessage(HttpExchange t) throws IOException
  {
    InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
    BufferedReader br = new BufferedReader(isr);
    int b;
    StringBuilder buf = new StringBuilder();
    while ((b = br.read()) != -1)
    {
      buf.append((char) b);
    }
    br.close();
    isr.close();
    return buf.toString();
  }

  public static void sendMessage(HttpExchange t, int code, String response) throws IOException
  {
    t.sendResponseHeaders(code, response.getBytes().length);
    OutputStream os = t.getResponseBody();
    os.write(response.getBytes());
    os.close();
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
