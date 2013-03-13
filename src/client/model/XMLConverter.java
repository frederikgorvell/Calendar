// @author hengsti

package client.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class XMLConverter {

	private static final String XML_VERSION_ENCODING = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";

	File toXML(Login login, String filename) {
		StringBuffer s = new StringBuffer();
		final String type = "Login";

		s.append(XML_VERSION_ENCODING);
		s.append("<calendar>\n");
		s.append("<" + type + ">\n");
		addTag(s, "Username", login.getUsername());
		addTag(s, "Password", login.getPassword());
		s.append("</" + type + ">\n");
		s.append("</calendar>");

		return toFile(s, filename);
	}

	File toXML(Appointment appointment, String filename) {
		StringBuffer s = new StringBuffer();
		final String type = "Appointment";

		s.append(XML_VERSION_ENCODING);
		s.append("<calendar>\n");
		s.append("<" + type + ">");
		addTag(s, "Start", appointment.getStart().toString());
		addTag(s, "End", appointment.getEnd().toString());
		addTag(s, "Description", appointment.getDescription());
		addTag(s, "Location", appointment.getLocation());
		addTag(s, "Status", appointment.getStatus());
		s.append("</" + type + ">\n");
		s.append("</calendar>");

		return toFile(s, filename);
	}

	private void addTag(StringBuffer s, String tagName, String tagContent) {
		s.append("<" + tagName + ">");
		s.append(tagContent);
		s.append("</" + tagName + ">" + "\n");
	}

	private File toFile(StringBuffer s, String filename) {
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

	Object readXMLintoObject(String filename) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(filename);
			doc.getDocumentElement().normalize();

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());
			switch (doc.getDocumentElement().getNodeName()) {
			case "Login":
				return handlLogin(doc);

			case "Appointment":
				break;
			case "Notification":
				break;
			case "Alarm":
				break;
			default:
				break;

			}

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return null;
		}
	}

	private Object handlLogin(Document doc) {
		NodeList nList = doc.getElementsByTagName("Login");

		System.out.println("----------------------------");

		Node nNode = nList.item(0);
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
		Login login = null;
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
			System.out.println("Username : "
					+ eElement.getElementsByTagName("Username").item(0)
							.getTextContent());
			System.out.println("Password : "
					+ eElement.getElementsByTagName("Password").item(0)
							.getTextContent());
			login = new Login(eElement.getElementsByTagName("Username").item(0)
					.getTextContent(), eElement
					.getElementsByTagName("Password").item(0).getTextContent());
		}
		return login;
	}
}
