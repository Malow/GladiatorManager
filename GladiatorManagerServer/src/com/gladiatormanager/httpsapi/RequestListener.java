package com.gladiatormanager.httpsapi;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.gladiatormanager.httpsapi.AccountContextHandlers.LoginHandler;
import com.gladiatormanager.httpsapi.AccountContextHandlers.RegisterHandler;
import com.gladiatormanager.httpsapi.AccountContextHandlers.ResetPasswordHandler;
import com.gladiatormanager.httpsapi.AccountContextHandlers.SendPasswordResetTokenHandler;
import com.gladiatormanager.httpsapi.GameContextHandlers.GetCharactersHandler;
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
    // AccountContextHandlers
    this.server.createContext("/login", new LoginHandler());
    this.server.createContext("/register", new RegisterHandler());
    this.server.createContext("/sendpwresettoken", new SendPasswordResetTokenHandler());
    this.server.createContext("/resetpw", new ResetPasswordHandler());
    this.server.createContext("/getcharacters", new GetCharactersHandler());
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
