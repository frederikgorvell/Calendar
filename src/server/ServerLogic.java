package server;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ServerLogic {
	/**
	 * Handles all client requests based on the request field in the Request object.
	 * The response contains an 'error' if there are some problem of some sort that 
	 * hinders the server from returning the expected data.
	 * 
	 * @param request - Containing the relevant information regarding the request.
	 * @return Response - Containing relevant information to be returned to the client.
	 */
	public static File handleRequest(File request) throws Exception {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(request);
		doc.getDocumentElement().normalize();

		String type = doc.getDocumentElement().getNodeName();
		
		if (type.equals("Login")) {
			
		} else if (type.equals("Appointment")) {
			
		}
		
		System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
		NodeList nodes = doc.getElementsByTagName("Login");
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			System.out.println("Login Username: " + getValue("Username", element));
			System.out.println("Login Password: " + getValue("Password", element));
			}
		}


		/*
//		Response response = new Response();
		DbConnection dc = null;
				//new DbConnection("jdbc:mysql://mysql.stud.ntnu.no/chrlu_prosjekt1", "chrlu_prosjekt1", "general1");
		
		if(request == null) {
			response.addItem("error", "No request");
		} else if(!dc.connect()) {
			response.addItem("error", "Unable to connect to the database");
		} else {					
			switch (request.getRequest()) {
		        case Request.LOGIN:  
		            login(request, response, dc);
		            break;
		        case Request.ADD_APPOINTMENT:  
		        	//getAllSheep(request, response, dc);
		            break;
		        case Request.GET_UPDATE:  
//		        	getSheepByStatus(request, response, dc, 0);
		            break;
		        case Request.GET_USERS_APPOINTMENTS:  
//		        	getSheepByStatus(request, response, dc, 1);
		            break;
		        case Request.GET_USERS_NOTIFICATIONS:  
//		        	getSheepByStatus(request, response, dc, 2);
		            break;
		        default:
		        	response.addItem("error", "Unknown request");
		            
			}
		}	*/
//		dc.closeConnection();
		return null;		
	}
	
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
	

	
	/**
	 * This method is called when the client has requestet a login.
	 * The Request will then contain a username and a password that will be used to 
	 * authenticate the user.
	 * 
	 * If the authentication gets through the Response is loaded with the key "result" with the value "loginOK"
	 * and returned
	 * 
	 * @param request - The Request object containing the relevant information
	 * @param response - The Response object to be loaded with the response
	 * @param dc - The DbConnection object containing an open connection with the database
	 */
	/*
	private static void login(Request request, Response response, DbConnection dc) {
		String username = (String) request.getItem("username");
		String password = (String) request.getItem("password");
		
		try {
			byte[] salt = dc.getStoredHash(username, "Salt");
			byte[] hashedPassword = dc.getStoredHash(username, "Password");
			
			boolean correct = PasswordEncryption.checkPassword(password, hashedPassword, salt);
			if(correct)	
				response.addItem("result", "loginOK");
			else
				response.addItem("result", "loginFailed");
		} catch(Exception e) {
			response.addItem("error", e.toString());
		}
	}
	*/
	
	public static void main(String[] args) throws Exception {
		File f = new File("login.xml");
		ServerLogic.handleRequest(f);
	}
}