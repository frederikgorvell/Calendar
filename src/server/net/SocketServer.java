package server.net;

import java.net.Socket;
import java.net.ServerSocket;

public class SocketServer extends Thread {	
	public int port;
	
	public SocketServer(int port) {
		this.port = port;
	}
	
	public void run() {
		try {
			ServerSocket serversocket = new ServerSocket(port);
	        int id = 0;
	        System.out.println("Client handler started...");
	        while(true) {
	        	Socket socket = serversocket.accept();
	        	Session session = new Session(socket,id++);
	        	session.start();
	        }    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}