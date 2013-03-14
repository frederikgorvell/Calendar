package client.net;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.net.InetSocketAddress;
import java.net.Socket;
//import java.net.SocketAddress;

//import javax.net.ssl.SSLSocket;
//import javax.net.ssl.SSLSocketFactory;

//import structs.Request;
//import structs.Response;

public class ClientConnection {	
//	private static final int CONNECTION_TIMEOUT = 3000;
	
	private String host;
	private int port;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private Socket socket;
//	private SSLSocket sslsocket;
	
	public ClientConnection(String host, int port) {	
		this.host = host;
		this.port = port;
	}
	
	public boolean openConnection() {
		try {		
//			SocketAddress socketaddress = new InetSocketAddress(host, port);
			socket = new Socket(host, port);
//			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//			sslsocket = (SSLSocket) sslsocketfactory.createSocket();
//			sslsocket.connect(socketaddress, CONNECTION_TIMEOUT);
			
//			String[] cipher = new String[1];
//          cipher[0]="TLS_DH_anon_WITH_AES_128_CBC_SHA";
//          sslsocket.setEnabledCipherSuites(cipher);
            
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
