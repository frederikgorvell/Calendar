package server;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import server.db.Interaction;
import shared.XMLConverter;
import shared.model.Appointment;

public class ServerLogic {
	
	private Interaction inter;

	public ServerLogic(Interaction inter) {
		this.inter = inter;
	}
	
	public File handleRequest(File request) throws Exception {
		if (request == null) return null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(request);
		doc.getDocumentElement().normalize();

		String type = doc.getDocumentElement().getNodeName();
		
		if (type.equals("Login")) {
			NodeList nodes = doc.getElementsByTagName("Login");
			Node node = nodes.item(0);
			Element element = (Element) node;
			String username = XMLConverter.getValue("Username", element);
			String password = XMLConverter.getValue("Password", element);
			ResultSet rs = inter.getPerson(username);
			if (rs != null) {
				rs.next();
				if (password.equals(rs.getString(5)))
					return XMLConverter.makeConfirmed(0);
				else 
					return XMLConverter.makeFailed("Could not login");
			} else {
				return XMLConverter.makeFailed("Could not login");
			}
			
		} else if (type.equals("Appointment")) {
			
			
			NodeList nodes = doc.getElementsByTagName("Appointment");
			Node node = nodes.item(0);

			Element element = (Element) node;

			String username = XMLConverter.getValue("Username", element);
			String spec = XMLConverter.getValue("Specification", element);
			
			if (spec.equals("new")) {
				int aid = inter.getMaxAID() + 1;
				if (aid == -1) return XMLConverter.makeFailed("Could not make new appointment");
				String name = XMLConverter.getValue("Name", element);
				String start = makeDateNumber(XMLConverter.getValue("Start", element));
				String end = makeDateNumber(XMLConverter.getValue("End", element));
				String week = XMLConverter.getValue("Week", element);
				String desc = XMLConverter.getValue("Description", element);
				String loc = XMLConverter.getValue("Location", element);
				if (inter.addAppointment(aid, name, start, end, week, desc, loc, username, 0)) {
					return XMLConverter.makeConfirmed(aid);
				} else {
					return XMLConverter.makeFailed("Could not make new appointment");
				}
				//TODO
			} else if (spec.equals("delete")) {
				int aid = Integer.parseInt(XMLConverter.getValue("AID", element));
				if (inter.deleteAppointment(aid))
					return XMLConverter.makeConfirmed(aid);
				else
					return XMLConverter.makeFailed("Could not delete appointment " + aid);
			} else if (spec.equals("edit")) {
				int aid = Integer.parseInt(XMLConverter.getValue("AID", element));
				String name = XMLConverter.getValue("Name", element);
				String start = makeDateNumber(XMLConverter.getValue("Start", element));
				String end = makeDateNumber(XMLConverter.getValue("End", element));
				String week = XMLConverter.getValue("Week", element);
				String desc = XMLConverter.getValue("Description", element);
				String loc = XMLConverter.getValue("Location", element);
				if (inter.editAppointment(aid, name, start, end, week, desc, loc)) {
					return XMLConverter.makeConfirmed(aid);
				} else {
					return XMLConverter.makeFailed("Could not make new appointment");
				}
				//TODO
			} else if (spec.equals("view")) {
				int aid = Integer.parseInt(XMLConverter.getValue("AID", element));
				ResultSet rs = inter.getAppointment(aid);
				if (rs != null) {
					rs.next();
					Appointment a = new Appointment();
					a.setAID(Integer.parseInt(rs.getString(1)));
					a.setName(rs.getString(2));
					a.setStart(makeDateString(rs.getString(3)));
					a.setEnd(makeDateString(rs.getString(4)));
					a.setWeek(Integer.parseInt(rs.getString(5)));
					a.setDescription(rs.getString(6));
					//HVA GJ¯R DATABASEN?
					a.setLocation(rs.getString(7));
					return XMLConverter.toXML(a,/* "appointment.xml",*/ "view");
				} else {
					return XMLConverter.makeFailed("Could not view appointment " + aid);
				}
			} else if (spec.equals("viewOther")) {
				String week = XMLConverter.getValue("Week", element);
				ResultSet rs = inter.getUserCalendar(username, week);
				if (rs != null) {
					ArrayList<Appointment> appList = new ArrayList<Appointment>();
					Appointment a;
					while(rs.next()) {
						a = new Appointment();
						a.setAID(Integer.parseInt(rs.getString(1)));
						a.setName(rs.getString(2));
						a.setStart(makeDateString(rs.getString(3)));
						a.setEnd(makeDateString(rs.getString(4)));
						a.setWeek(Integer.parseInt(rs.getString(5)));
						a.setDescription(rs.getString(6));
						//HVA GJ¯R DATABASEN?
						a.setLocation(rs.getString(7));
						appList.add(a);
					}
					return XMLConverter.toXML(appList/*, "appointments.xml"*/);
				} else {
					return XMLConverter.makeFailed("Could not view week");
				}
			} else if (spec.equals("week")) {
				String week = XMLConverter.getValue("Week", element);
				ResultSet rs = inter.getUserCalendar(username, week);
				if (rs != null) {
					ArrayList<Appointment> appList = new ArrayList<Appointment>();
					Appointment a;
					while(rs.next()) {
						a = new Appointment();
						a.setAID(rs.getInt(1));
						a.setName(rs.getString(2));
						a.setStart(makeDateString(rs.getString(3)));
						a.setEnd(makeDateString(rs.getString(4)));
						a.setWeek(rs.getInt(5));
						a.setDescription(rs.getString(6));
						//HVA GJ¯R DATABASEN?
						a.setLocation(rs.getString(7));
						appList.add(a);
					}
					return XMLConverter.toXML(appList/*, "calendar.xml"*/);
				} else {
					return XMLConverter.makeFailed("Could not view week");
				}
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
	
	private String makeDateNumber(String dateString) {
		StringBuffer sb = new StringBuffer();
		String[] raw = dateString.split(" ");
		String[] rawDate = raw[0].split("-");
		String[] rawTime = raw[1].split(":");
		sb.append(rawDate[0]);
		sb.append(addZero(rawDate[1]));
		sb.append(addZero(rawDate[2]));
		sb.append(addZero(rawTime[0]));
		sb.append(addZero(rawTime[1]));
		return sb.toString();
	}
	
	private String addZero(String number) {
		if (number.length() > 1)
			return number;
		else
			return "0" + number;
	}
	
	private String makeDateString(String dateNumber) {
		StringBuffer sb = new StringBuffer();
		sb.append(dateNumber.substring(0,4));
		sb.append("-");
		sb.append(dateNumber.substring(4, 6));
		sb.append("-");
		sb.append(dateNumber.substring(6,8));
		sb.append(" ");
		sb.append(dateNumber.substring(8,10));
		sb.append(":");
		sb.append(dateNumber.substring(10,12));
		return sb.toString();
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
