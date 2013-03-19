package server.net;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import server.ServerLogic;
import server.db.Interaction;

public class Session extends Thread {
	private ServerLogic serverLogic;
	private Interaction interaction;
	private Socket socket;
	private int sessionID;
	private boolean running = true;
	
	ObjectInputStream objectInputStream;
	ObjectOutputStream objectOutputStream;
	
	public Session(Socket socket, int sessionID, Interaction interaction) {
		this.socket = socket;
		this.sessionID = sessionID;
		this.interaction = interaction;
		serverLogic = new ServerLogic(interaction);
	}

	public void run() {		
		try {
			System.out.println("Server - Connection Open...");
            System.out.println("Session ID: " + sessionID);
            
            while(running) {
            	File received = receiveObject();
            	if (received == null) {
            		running = false;
            		closeConnection();
            	} else {
            		File response = serverLogic.handleRequest(received);
            		sendObject(response);
            	}
            }
            
            System.out.println("Session: " + sessionID + " terminated");
            
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public File receiveObject() {
		try {
			System.out.println("Sesion: [" + sessionID + "] - Waiting for object...");
			objectInputStream = new ObjectInputStream(socket.getInputStream());
	        File f = (File) objectInputStream.readObject();
	        System.out.println(f.toString());
	        return f;
//			return (File) objectInputStream.readObject();
		} catch(Exception e) {
			running = false;
			return null;
		}
	}

	public boolean sendObject(File response) {
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(response);
			objectOutputStream.flush();
			return true;
		} catch(SocketException e) {
			return false; //host disconnect
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}	
	}

	public void closeConnection() {
		try {
			if(objectInputStream != null)
				objectInputStream.close();
			if(objectOutputStream != null)
				objectOutputStream.close();
			if(socket != null)
				socket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}