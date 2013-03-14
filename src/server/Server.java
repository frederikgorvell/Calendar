package server;

import server.net.ServerConnection;
 
public class Server {
	private final static int port = 12467;
	
	public static void main(String[] args) {	
		System.out.println("Starting server...");
		ServerConnection sc = new ServerConnection(port);
		sc.start();
	}
}