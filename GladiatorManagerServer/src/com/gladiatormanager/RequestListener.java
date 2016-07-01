package com.gladiatormanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.gladiatormanager.requests.LoginRequest;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class RequestListener {
	
	private HttpServer server;
	
	public RequestListener() {
	}
	
	public void Start(int port) {
		try {
			this.server = HttpServer.create(new InetSocketAddress(port), 0);
			this.server.createContext("/login", new LoginHandler());
			this.server.setExecutor(null); // creates a default executor
			this.server.start();
		} catch (IOException e) {
			System.out.println("Exception while starting RequestListener.");
			e.printStackTrace();
		}
	}
	
	public void Close() {
		server.stop(0);
	}
	
	// Handlers
	static class LoginHandler implements HttpHandler {
        public void handle(HttpExchange t) {
        	try {
            	String message = getMessage(t);
            	LoginRequest lr = new Gson().fromJson(message, LoginRequest.class);
            	System.out.println("LoginRequest received: Email: " + lr.email + " - Password: " + lr.password);
            	//Database.login()
				sendMessage(t, 200, "{\"result\":\"true\"}"); //g.toJson();
			} catch (IOException e) {
				System.out.println("Exception while handling LoginRequest.");
				e.printStackTrace();
			} 
        }
	}
	
	// Static helper methods
	public static String getMessage(HttpExchange t) throws IOException {
		InputStreamReader isr = new InputStreamReader(t.getRequestBody(),"utf-8");
		BufferedReader br = new BufferedReader(isr);
		int b;
		StringBuilder buf = new StringBuilder();
		while ((b = br.read()) != -1) {
		    buf.append((char) b);
		}
		br.close();
		isr.close();
		return buf.toString();
	}
	
	public static void sendMessage(HttpExchange t, int code, String response) throws IOException {
        t.sendResponseHeaders(code, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
	}
}
