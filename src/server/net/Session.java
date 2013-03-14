package server.net;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import server.ServerLogic;

//import javax.net.ssl.SSLSocket;

//import structs.Request;
//import structs.Response;

public class Session extends Thread {
	private Socket socket;
	private int sessionID;
	boolean running = true;
	
	ObjectInputStream objectInputStream;
	ObjectOutputStream objectOutputStream;
	
	/**
	 * Constructor for the Session class
	 * 
	 * @param sslsocket - The socket that holds the connection
	 * @param sessionID - The ID of the session
	 */
	public Session(Socket socket, int sessionID) {
		this.socket = socket;
		this.sessionID = sessionID;
	}

	
	/**
	 * Starts the session
	 * Starts a loop that waits for requests from the client
	 * 
	 */
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
	
	
	/**
	 * Waits for an object from the server or for the connection to be closed.
	 * When the connection is closed an exception is thrown and the boolean value of 'running'
	 * is set to false.
	 * @return Request - The request object sent from the client or NULL if the connection has been closed.
	 */
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

	/**
	 * Returns an object to the client after an request has been received.
	 * @param response - The object to be returned to the client.
	 * @return boolean - True - the transfer completed, False - the transfer failed.
	 */
	public boolean sendObject(File response) {
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(response);
			objectOutputStream.flush();
			return true;
		} catch(SocketException e) {
			return false;
		}//Host disconected, dont do anything
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}	
	}

	/**
	 * Cleanly closes all open streams and the socket.
	 */
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