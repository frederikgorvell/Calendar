package server;

import server.net.ServerConnection;
 
public class Server {
	public static void main(String[] args) {	
		System.out.println("Starting server...");
		ServerConnection sc = new ServerConnection();
		sc.start();
	}
}