package com.gladiatormanager;

import java.util.Scanner;

public class GladiatorManager {

	public static void main(String[] args) {
		RequestListener listener = new RequestListener();
		listener.Start(7000);
		
		String input = "";
		Scanner in = new Scanner(System.in);
		while(!input.equals("Exit"))
		{
			System.out.print("> ");
			input = in.next();
		}
		in.close();
		
		listener.Close();
		
		System.out.println("Server closed successfully");
	}

}
