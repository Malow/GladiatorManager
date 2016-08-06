package com.gladiatormanagerintegrationtests;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gladiatormanager.comstructs.account.LoginResponse;
import com.google.gson.Gson;

public class TestHelpers
{
  public static boolean isValidToken(String token)
  {
    if (token != null && token.length() > 0) { return true; }
    return false;
  }

  public static void resetDatabase() throws Exception
  {
    Class.forName("com.mysql.jdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost?" + "user=GMS&password=password&autoReconnect=true");
    String file = new String(Files.readAllBytes(Paths.get("../CreateDatabase.sql")));
    String[] statements = file.split("\\;");
    for (String statement : statements)
    {
      try
      {
        connection.prepareStatement(statement + ";").execute();
      }
      catch (Exception e)
      {

      }
    }
  }

  public static LoginResponse registerAccount(String email, String username, String password) throws Exception
  {
    String request = JsonRequests.register(email, username, password);
    return new Gson().fromJson(ServerConnection.sendMessage("/register", request), LoginResponse.class);
  }

  public static String getPasswordResetToken(String email) throws Exception
  {
    Class.forName("com.mysql.jdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/GladiatorManager?" + "user=GMS&password=password&autoReconnect=true");
    PreparedStatement s1 = connection.prepareStatement("SELECT * FROM Accounts WHERE email = ? ; ");
    s1.setString(1, email);
    ResultSet s1Res = s1.executeQuery();

    if (s1Res.next()) { return s1Res.getString("pw_reset_token"); }
    return null;
  }
}
