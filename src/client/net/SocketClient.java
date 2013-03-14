package client.net;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient {	
	
	private String host;
	private int port;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private Socket socket;
	
	public SocketClient(String host, int port) {	
		this.host = host;
		this.port = port;
	}
	
	public boolean openConnection() {
		try {		
			socket = new Socket(host, port);

            System.out.println("Client - Conection open");
            return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean send(File f) {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(f);
			oos.flush();
			System.out.println("File sent"); 
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public File receiveObject() {
		try {
			System.out.println("Client waiting for object...");
			ois = new ObjectInputStream(socket.getInputStream());
	        return (File) ois.readObject();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void closeConnection() {
		try {
			if(ois != null)
				ois.close();
			if(oos != null)
				oos.close();
			if(socket != null)
				socket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
