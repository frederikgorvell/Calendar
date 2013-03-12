package client.net;

import java.io.*;
import java.net.*;
import java.sql.Connection;

class ClientSocket {
	
	Socket clientSocket = null;

	
	public void send(File f) throws IOException{
		byte[] mybytearray = new byte[1024];
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));  
		int bytesRead = ((ObjectInput) out).read(mybytearray, 0, mybytearray.length);
		out.write(mybytearray, 0, bytesRead);
		out.close();
		clientSocket.close();
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
