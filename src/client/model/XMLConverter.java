// @author hengsti

package client.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public final class XMLConverter {

	private static final String XML_VERSION_ENCODING = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";

	File toXML(Login login, String filename) {
		StringBuffer s = new StringBuffer();
		final String type = "Login";

		s.append(XML_VERSION_ENCODING);
		s.append("<" + type + ">");
		addTag(s, "Username", login.getUsername());
		addTag(s, "Password", login.getPassword());
		s.append("</" + type + ">\n");

		return toFile(s, filename);
	}

	File toXML(Appointment appointment, String filename) {
		StringBuffer s = new StringBuffer();
		final String type = "Appointment";

		s.append(XML_VERSION_ENCODING);
		s.append("<" + type + ">");
		addTag(s, "Start", appointment.getStart().toString());
		addTag(s, "End", appointment.getEnd().toString());
		addTag(s, "Description", appointment.getDescription());
		addTag(s, "Location", appointment.getLocation());
		addTag(s, "Status", appointment.getStatus());
		s.append("</" + type + ">");

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

}
