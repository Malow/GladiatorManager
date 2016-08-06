package com.gladiatormanagerintegrationtests;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import com.mashape.unirest.http.Unirest;

public class ServerConnection
{
  private static final ServerConnection INSTANCE = new ServerConnection();

  private ServerConnection()
  {
    if (INSTANCE != null) { throw new IllegalStateException("Already instantiated"); }
    this.init();
  }

  public void init()
  {
    try
    {
      SSLContextBuilder builder = new SSLContextBuilder();
      builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
      Unirest.setHttpClient(httpclient);
    }
    catch (Exception e)
    {
      System.out.println("Failed to start server: " + e.toString());
      e.printStackTrace();
    }
  }

  public static String sendMessage(String path, String message) throws Exception
  {
    return Unirest.post("https://127.0.0.1:7000" + path).body(message).asJson().getBody().toString();
  }
}
