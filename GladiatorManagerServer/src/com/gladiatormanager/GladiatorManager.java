package com.gladiatormanager;

import java.util.Scanner;

import com.gladiatormanager.database.Database;
import com.gladiatormanager.httpsapi.RequestListener;

public class GladiatorManager
{
  public static void main(String[] args)
  {
    Globals.email = new Email();
    Globals.email.start();
    Database.init();

    int port = 7000;
    String sslPassword = "password";

    System.out.println("Starting GladiatorManagerServer in directory " + System.getProperty("user.dir"));
    System.out.println("Using port " + port);
    RequestListener listener = new RequestListener();
    listener.start(port, sslPassword);

    String input = "";
    Scanner in = new Scanner(System.in);
    while (!input.equals("Exit"))
    {
      System.out.print("> ");
      input = in.next();
    }
    in.close();

    listener.close();
    Database.close();

    System.out.println("Server closed successfully");
  }

}
