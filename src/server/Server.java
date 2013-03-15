package server;

import server.net.SocketServer;
 
public class Server {
	private final static int port = 12467;
	
	public static void main(String[] args) {	
		System.out.println("Starting server...");
		//DBConnection db = new DBConnection();
		SocketServer sc = new SocketServer(port/*, db*/);
		sc.start();
	}
}