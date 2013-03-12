package server.net;

import java.io.*;
import java.net.*;

class SocketServer {
	
	Socket welcomeSocket = null;
	
	public void send(File f) throws IOException{
		byte[] mybytearray = new byte[1024];
		
		try {
			ObjectInputStream ois = new ObjectInputStream(welcomeSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(welcomeSocket.getOutputStream()));  
		int bytesRead = ((ObjectInput) out).read(mybytearray, 0, mybytearray.length);
		out.write(mybytearray, 0, bytesRead);
		out.close();
		welcomeSocket.close();
	}
	
	public static void main(String[] args) throws Exception {
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(6789);
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
			welcomeSocket.close();
		}
	}
	
}
