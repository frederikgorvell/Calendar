package client.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
	
	public boolean send(File file) {
		try {
		    OutputStream output = socket.getOutputStream();     
	
		    FileInputStream fileInputStream = new FileInputStream(file);
		    byte[] buffer = new byte[1024*1024];
		    int bytesRead = 0;
	
		    while((bytesRead = fileInputStream.read(buffer)) > 0) {
		        output.write(buffer, 0, bytesRead);
		    }
		    fileInputStream.close();
		    output.flush();/////////////////
		    return true;
		} catch (Exception e) {
			return false;
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
		    out.flush();/////////////////////
		    return file;
		} catch (Exception e) {
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
