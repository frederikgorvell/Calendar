package server.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
			//
			InputStream is = null;
			int bufferSize = 0;
			FileOutputStream fos = null;
			BufferedOutputStream bos = null;
			try {
		        is = socket.getInputStream();

		        bufferSize = socket.getReceiveBufferSize();
		        System.out.println("Buffer size: " + bufferSize);
		    } catch (IOException ex) {
		        System.out.println("Can't get socket input stream. ");
		    }
			File f = null;
			try {
				f = new File("receive.xml");
		        fos = new FileOutputStream("receive.xml");
		        bos = new BufferedOutputStream(fos);

		    } catch (FileNotFoundException ex) {
		        System.out.println("File not found. ");
		    }

		    byte[] bytes = new byte[bufferSize];

		    int count;

		    while ((count = is.read(bytes)) > 0) {
		        bos.write(bytes, 0, count);
		    }

		    bos.flush();
		    //
		    return f;
//	        return new File("calendar.xml");
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	public File receiveObject() {
		try {
			System.out.println("Sesion: [" + sessionID + "] - Waiting for object...");
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			return (File) objectInputStream.readObject();
		} catch(Exception e) {
			running = false;
			return null;
		}
	}*/

	public boolean sendObject(File file) {
		try {
//			File file = new File("M:\\test.xml");
		    // Get the size of the file
		    long length = file.length();
		    if (length > Integer.MAX_VALUE) {
		        System.out.println("File is too large.");
		    }
		    byte[] bytes = new byte[(int) length];
		    FileInputStream fis = new FileInputStream(file);
		    BufferedInputStream bis = new BufferedInputStream(fis);
		    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

		    int count;

		    while ((count = bis.read(bytes)) > 0) {
		        out.write(bytes, 0, count);
		    }

		    out.flush();
		    /*
		    out.close();
		    fis.close();
		    bis.close();
		    socket.close();
			*/
		    
		    
			/*
			DataOutputStream out = null;
		    DataInputStream in = null;    

		    out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		    in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

		    //File file = new File("C:\\test.xml");
		    InputStream is = new FileInputStream(f);
		    // Get the size of the file
		    long length = f.length();
		    if (length > Integer.MAX_VALUE) {
		        System.out.println("File is too large.");
		    }
		    byte[] bytes = new byte[(int) length];

		    out.write(bytes);
		    //System.out.println(bytes);
		    /*
		    out.close();
		    in.close();
		    socket.close();
			*/
			
			
			/*oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(f);
			oos.flush();
			System.out.println("File sent"); */
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*public boolean sendObject(File response) {
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
	}*/

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