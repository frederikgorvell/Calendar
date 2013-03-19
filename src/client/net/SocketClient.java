package client.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	/*
	public boolean send(File file) {
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
		    
		    out.close();
		    fis.close();
		    bis.close();
		    /*
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
			System.out.println("File sent"); 
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
	/*public boolean send(File f) {
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
	}*/
	
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
//		        System.out.println(bytesReceived);
		        break;
		    }
		    out.flush();/////////////////////
		    return file;
		} catch (Exception e) {
			return null;
		}
	}
	
	/*
	public File receiveObject() {
		try {
			File f = new File("receive.xml");
			System.out.println("Client waiting for object...");
//			ois = new ObjectInputStream(socket.getInputStream());
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
			try {
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
		    bos.close();
		    is.close();
		    //
		    return f;
//	        return new File("calendar.xml");
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}*/
	
	
	
	/*public File receiveObject() {
		try {
			System.out.println("Client waiting for object...");
			ois = new ObjectInputStream(socket.getInputStream());
	        return (File) ois.readObject();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}*/
	
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
