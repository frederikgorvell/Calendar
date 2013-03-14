package server.net;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import server.ServerLogic;

public class Session extends Thread {
	private Socket socket;
	private int sessionID;
	boolean running = true;
	
	ObjectInputStream objectInputStream;
	ObjectOutputStream objectOutputStream;
	
	public Session(Socket socket, int sessionID) {
		this.socket = socket;
		this.sessionID = sessionID;
	}

	public void run() {		
		try {
			System.out.println("Server - Connection Open...");
            System.out.println("Session ID: " + sessionID);
            
            while(running) {
            	File response = ServerLogic.handleRequest(reciveObject());
            	sendObject(response);
            }
            
            System.out.println("Session: " + sessionID + " terminated");
            
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public File reciveObject() {
		try {
			System.out.println("Sesion: [" + sessionID + "] - Waiting for object...");
			objectInputStream = new ObjectInputStream(socket.getInputStream());
	        return (File) objectInputStream.readObject();
		}
		catch(Exception e)
		{
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