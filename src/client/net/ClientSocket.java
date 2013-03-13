package client.net;

import java.io.*;
import java.net.*;
import java.sql.Connection;

import javax.net.SocketFactory;
import javax.xml.ws.Response;

class ClientSocket {
	
	Socket clientSocket = null;
	
	
	private String host;
	private int port;
	ObjectOutputStream ous;
	ObjectInputStream ois;
	
	public ClientSocket(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	public boolean connect() throws Exception{
		try {
			SocketAddress clientAddress = new InetSocketAddress(host,port);
			SocketFactory factory = SocketFactory.getDefault();
			clientSocket = factory.createSocket();
			clientSocket.connect(clientAddress);
			System.out.println("Client connection open");
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
	}
	}

	
	public boolean send(Object f) throws IOException{
		try {
			ous = new ObjectOutputStream(clientSocket.getOutputStream());
			ous.writeObject(f);
			ous.flush();
			System.out.println("File sent");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public Response receiveObject() throws Exception{
		try{
			System.out.println("Client waiting for object");
			ois = new ObjectInputStream(clientSocket.getInputStream());
			return (Response) ois.readObject();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public void closeConnection(){
		try{
			if (ois != null)
				ois.close();
			if(ous != null)
				ous.close();
			if (clientSocket != null)
				clientSocket.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		String sentence;
		String modifiedSentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		Socket clientSocket = new Socket("localhost", 6789);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		sentence = inFromUser.readLine();
		outToServer.writeBytes(sentence + '\n');
		modifiedSentence = inFromServer.readLine();
		System.out.println("FROM SERVER: " + modifiedSentence);
		clientSocket.close();
	}
}
