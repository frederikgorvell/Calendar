package server.net;

import java.net.Socket;
import java.net.ServerSocket;

import server.db.Interaction;

public class SocketServer extends Thread {	
	private int port;
	private Interaction interaction;
	
	public SocketServer(int port, Interaction interaction) {
		this.port = port;
		this.interaction = interaction;
	}
	
	public void run() {
		try {
			ServerSocket serversocket = new ServerSocket(port);
	        int id = 0;
	        System.out.println("Client handler started...");
	        while(true) {
	        	Socket socket = serversocket.accept();
	        	Session session = new Session(socket, id++, interaction);
	        	session.start();
	        }    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}