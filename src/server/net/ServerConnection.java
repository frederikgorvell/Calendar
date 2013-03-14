package server.net;

import java.net.Socket;
import java.net.ServerSocket;

//import javax.net.ssl.SSLServerSocket;
//import javax.net.ssl.SSLServerSocketFactory;
//import javax.net.ssl.SSLSocket;


public class ServerConnection extends Thread
{	
	public static final int port = 12467;
	
	public void run() {
		try {
			ServerSocket serversocket = new ServerSocket(port);
//			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
//	        SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(PORT);
	
//	        String[] newcipher = new String[1];
//	        newcipher[0]="TLS_DH_anon_WITH_AES_128_CBC_SHA";
	        
	        int id = 0;
	        System.out.println("Client handler started...");
	        while(true) {
	        	Socket socket = serversocket.accept();
//	        	SSLSocket sslsocket;
//				sslsocket = (SSLSocket) sslserversocket.accept();
	
//	        	sslsocket.setEnabledCipherSuites(newcipher);
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