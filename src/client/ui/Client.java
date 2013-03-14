package client.ui;

import client.net.ClientConnection;

public class Client {

	public static void main(String[] args) throws Exception
	{
		new UserInterface("localhost", 12467);
		//login();
	}

	/*public static void login() 
	{
		System.out.println("client started...");
		ClientConnection cc = new ClientConnection(HOST, PORT);

		boolean connection = cc.openConnection();

		if (connection) {
			Request request = new Request();
			request.setRequest(Request.LOGIN);
			request.addItem("username", "herpderp@gmail.com");
			request.addItem("password", "passord");
			cc.sendObject(request);
			Response response = cc.reciveObject();
			cc.closeConnection();
			System.out.println("error " + response.getItem("error"));
			System.out.println("result:" + " " + response.getItem("result"));
		}
	}*/
}
