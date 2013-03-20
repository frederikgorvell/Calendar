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
					return XMLConverter.makeFailed("Could not edit appointment");
				}
				//TODO
			} else if (spec.equals("view")) {
				int aid = Integer.parseInt(XMLConverter.getValue("AID", element));
				ResultSet rs = inter.getAppointment(aid);
				if (rs != null) {
					rs.next();
					Appointment a = new Appointment();
					a.setAID(aid);
					a.setName(rs.getString(2));
					a.setStart(makeDateString(rs.getString(3)));
					a.setEnd(makeDateString(rs.getString(4)));
					a.setWeek(rs.getInt(5));
					a.setDescription(rs.getString(6));
					a.setLocation(rs.getString(8));
					return XMLConverter.toXML(a, "view");
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
						a.setAID(rs.getInt(1));
						a.setName(rs.getString(2));
						a.setStart(makeDateString(rs.getString(3)));
						a.setEnd(makeDateString(rs.getString(4)));
						a.setWeek(rs.getInt(5));
						a.setDescription(rs.getString(6));
						a.setLocation(rs.getString(8));
						appList.add(a);
					}
					return XMLConverter.toXML(appList);
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
						a.setLocation(rs.getString(8));
						appList.add(a);
					}
					return XMLConverter.toXML(appList);
				} else {
					return XMLConverter.makeFailed("Could not view week");
				}
			}
		} else if (type.equals("Invite")) {
			//TODO
			return XMLConverter.makeConfirmed(0);
		}
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
}
