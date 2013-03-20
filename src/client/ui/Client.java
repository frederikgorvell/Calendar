package client.ui;

public class Client {
	private final static int port = 12467;
	
	public static void main(String[] args) throws Exception {
//		new UserInterface("localhost", port);
		new UserInterface("78.91.22.190", port);
	}
}
