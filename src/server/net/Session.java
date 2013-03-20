package server.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import server.ServerLogic;
import server.db.Interaction;

public class Session extends Thread {
	private ServerLogic serverLogic;
	private Socket socket;
	private int sessionID;
	private boolean running = true;
	
	ObjectInputStream objectInputStream;
	ObjectOutputStream objectOutputStream;
	
	public Session(Socket socket, int sessionID, Interaction interaction) {
		this.socket = socket;
		this.sessionID = sessionID;
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
            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public File receiveObject() {
		try {
			File file = null;
	
		    InputStream input = socket.getInputStream();
	
		    file = new File("calendar.xml");
		    FileOutputStream out = new FileOutputStream(file);
	
		    byte[] buffer = new byte[1024*1024];
	
		    int bytesReceived = 0;
	
		    while((bytesReceived = input.read(buffer)) > 0) {
		        out.write(buffer, 0, bytesReceived);
		        break;
		    }
		    out.flush();//////////////////////////
		    return file;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public boolean sendObject(File file) {
		try {
		    OutputStream output = socket.getOutputStream();     
	
		    FileInputStream fileInputStream = new FileInputStream(file);
		    byte[] buffer = new byte[1024*1024];
		    int bytesRead = 0;
	
		    while((bytesRead = fileInputStream.read(buffer)) > 0) {
		        output.write(buffer, 0, bytesRead);
		    }
		    fileInputStream.close();
		    output.flush();/////////////////////////
		    return true;
		} catch (Exception e) {
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