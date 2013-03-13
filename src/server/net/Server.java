package server.net;

public class Server {

	
	public static void main(String[] args) {
		System.out.println("Starting server");
		ServerConnection sc = new ServerConnection();
		sc.start();
	}
}
