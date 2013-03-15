package server.net;

import java.net.Socket;
import java.net.ServerSocket;

public class SocketServer extends Thread {	
	private int port;
//	private DBConnection db;
	
	public SocketServer(int port/*, DBConnection db*/) {
		this.port = port;
//		this.db = db;
	}
	
	public void run() {
		try {
			ServerSocket serversocket = new ServerSocket(port);
	        int id = 0;
	        System.out.println("Client handler started...");
	        while(true) {
	        	Socket socket = serversocket.accept();
	        	Session session = new Session(socket, id++/*, db*/);
	        	session.start();
	        }    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}