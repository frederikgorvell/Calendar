package server;

import server.db.Interaction;
import server.net.SocketServer;
 
public class Server {
	private final static int port = 12467;
	
	public static void main(String[] args) {	
		System.out.println("Starting server...");
		Interaction i = new Interaction();
		SocketServer sc = new SocketServer(port, i);
		sc.start();
	}
}