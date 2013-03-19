package shared;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import shared.model.Appointment;

import client.model.Login;

public class XMLConverter {

	private static final String XML_VERSION_ENCODING = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";

	public static File toXML(Login login, String filename) {
		StringBuffer s = new StringBuffer();
		final String type = "Login";

		s.append(XML_VERSION_ENCODING);
		s.append("<" + type + ">");
		s = addTag(s, "Username", login.getUsername());
		s = addTag(s, "Password", login.getPassword());
		s.append("</" + type + ">\n");

		return toFile(s, filename);
	}

	public static File toXML(Appointment appointment, String filename, String spec) {
		StringBuffer s = new StringBuffer();
		final String type = "Appointment";

		s.append(XML_VERSION_ENCODING);
		s.append("<" + type + ">");
		s = addTag(s, "Username", appointment.getCreator());
		s = addTag(s, "Specification", spec);
		s = addTag(s, "AID", appointment.getAID() + "");
		s = addTag(s, "Name", appointment.getName());
		s = addTag(s, "Start", appointment.getStart());
		s = addTag(s, "End", appointment.getEnd());
		s = addTag(s, "Week", appointment.getWeek() + "");
		s = addTag(s, "Description", appointment.getDescription());
		s = addTag(s, "Location", appointment.getLocation());
		s.append("</" + type + ">");

		return toFile(s, filename);
	}
	
	public static File toXML(ArrayList<Appointment> appList, String filename) {
		StringBuffer s = new StringBuffer();
		final String type = "Appointment";

		s.append(XML_VERSION_ENCODING);
		for (Appointment appointment : appList) {
			s.append("<" + type + ">");
			s = addTag(s, "Username", appointment.getCreator());
			s = addTag(s, "Specification", "week");
			s = addTag(s, "AID", appointment.getAID() + "");
			s = addTag(s, "Name", appointment.getName());
			s = addTag(s, "Start", appointment.getStart());
			s = addTag(s, "End", appointment.getEnd());
			s = addTag(s, "Week", appointment.getWeek() + "");
			s = addTag(s, "Description", appointment.getDescription());
			s = addTag(s, "Location", appointment.getLocation());
			s = addTag(s, "Other", appointment.getOther());
			s.append("</" + type + ">");
		}
		return toFile(s, filename);
	}
	
	public static File makeInvite(int AID, String invitedUser) {
		StringBuffer s = new StringBuffer();
		final String type = "Invite";

		s.append(XML_VERSION_ENCODING);
		s.append("<" + type + ">");
		s = addTag(s, "AID", AID + "");
		s = addTag(s, "InvitedUser", invitedUser);
		s.append("</" + type + ">");

		return toFile(s, "invite.xml");
	}

	private static StringBuffer addTag(StringBuffer s, String tagName, String tagContent) {
		s.append("<" + tagName + ">");
		s.append(tagContent);
		s.append("</" + tagName + ">" + "\n");
		return s;
	}

	private static File toFile(StringBuffer s, String filename) {
		File f = new File(filename);
		try {
			FileWriter fstream = new FileWriter(filename); // Create file
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(s.toString());
			out.close();// Close the output stream
			return f;
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return f;
		}
	}
	
	public static File makeConfirmed(int AID) {
		StringBuffer s = new StringBuffer();
		final String type = "Confirmed";

		s.append(XML_VERSION_ENCODING);
		s.append("<" + type + ">");
		s = addTag(s, "AID", AID + "");
		s.append("</" + type + ">");

		return toFile(s, "confirmed.xml");
	}
	
	public static File makeFailed(String message) {
		StringBuffer s = new StringBuffer();
		final String type = "Failed";

		s.append(XML_VERSION_ENCODING);
		s.append("<" + type + ">");
		s = addTag(s, "Message", message);
		s.append("</" + type + ">");

		return toFile(s, "failed.xml");
	}
	
	Object readXMLintoObject (String filename){
		try {
//			FileReader fstream = new FileReader(filename); // Create file
//			BufferedReader in = new BufferedReader(fstream);
//			in.readLine();
//
//			in.close();// Close the input stream
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(filename);
		    System.out.println( document.getFirstChild().getTextContent() );
			
			return null;
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return null;
		}
	}
	
	public static boolean isConfirmed(File received) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(received);
			doc.getDocumentElement().normalize();
			String type = doc.getDocumentElement().getNodeName();
			
			if (type.equals("Confirmed")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static int getAID(File received) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(received);
			doc.getDocumentElement().normalize();
			String type = doc.getDocumentElement().getNodeName();
			
			if (type.equals("Confirmed")) {
				NodeList nodes = doc.getElementsByTagName("Confirmed");
				Node node = nodes.item(0);
				Element element = (Element) node;
				return Integer.parseInt(getValue("AID", element));
				
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static ArrayList<Appointment> makeAppointment(File received) {
		//liste kanskje
		try {
			ArrayList<Appointment> appList = new ArrayList<Appointment>();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(received);
			doc.getDocumentElement().normalize();

			NodeList nodes = doc.getElementsByTagName("Appointment");
	
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
		
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					
					int AID = Integer.parseInt(getValue("AID", element));
					String name = getValue("Name", element);
					String start = getValue("Start", element);
					String end = getValue("End", element);
					int week = Integer.parseInt(getValue("Week", element));
					String desc = getValue("Description", element);
					String loc = getValue("Location", element);
					
					Appointment a = new Appointment(name, start, end, desc, loc);
					a.setWeek(week);
					appList.add(a);
				}
			}
			return appList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getValue(String tag, Element element) {
		try {
			NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
			Node node = (Node) nodes.item(0);
			return node.getNodeValue();
		} catch (NullPointerException e) {
			return null;
		}
	}
}
