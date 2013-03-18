package server;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import server.db.Interaction;
import shared.XMLConverter;

public class ServerLogic {
	
	private Interaction inter;
	
	public ServerLogic(Interaction interaction) {
		this.inter = interaction;
	}
	
	public File handleRequest(File request) throws Exception {
		if (request == null) return null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(request);
		doc.getDocumentElement().normalize();

		String type = doc.getDocumentElement().getNodeName();
		
		if (type.equals("Login")) {
			//TODO db
			return XMLConverter.makeConfirmed(1);
			
		} else if (type.equals("Appointment")) {
			
			
			NodeList nodes = doc.getElementsByTagName("Appointment");
			Node node = nodes.item(0);

			Element element = (Element) node;

			String username = XMLConverter.getValue("Username", element);
			String spec = XMLConverter.getValue("Specification", element);
			
			if (spec.equals("new")) {
				return XMLConverter.makeConfirmed(0);
				//TODO
			} else if (spec.equals("delete")) {
				int aid = Integer.parseInt(XMLConverter.getValue("AID", element));
				if (inter.deleteAppointment(aid))
						return XMLConverter.makeConfirmed(aid);
				else
					return XMLConverter.makeFailed("Could not delete appointment"+aid);
			} else if (spec.equals("edit")) {
				//TODO
			} else if (spec.equals("view")) {
				//TODO
				//return request;
			} else if (spec.equals("viewOther")) {
				//TODO
			} else if (spec.equals("week")) {
				//TODO
			}
		} else if (type.equals("Invite")) {
			return XMLConverter.makeConfirmed(0);
			//TODO
		}
		/*
		System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
		NodeList nodes = doc.getElementsByTagName("Login");
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			System.out.println("Login Username: " + XMLConverter.getValue("Username", element));
			System.out.println("Login Password: " + XMLConverter.getValue("Password", element));
			}
		}
		*/

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
	
	/*private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}*/
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
	/*
	public static void main(String[] args) throws Exception {
		File f = new File("login.xml");
		ServerLogic.handleRequest(f);
	}*/
}