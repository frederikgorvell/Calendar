// @author hengsti

package client.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public final class XMLConverter {

	FileWriter toXML(Login login, String filename) {
		StringBuffer s = new StringBuffer();

		s.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		s.append("<Login>\n");

		addTag(s, "Username", login.getUsername());
		addTag(s, "Password", login.getPassword());

		s.append("</Login>");

		return toFile(s, filename);
	}

	private void addTag(StringBuffer s, String tagName, String tagContent) {
		s.append("<" + tagName + ">");
		s.append(tagContent);
		s.append("</" + tagName + ">" + "\n");
	}

	private FileWriter toFile(StringBuffer s, String filename) {
		try {
			FileWriter fstream = new FileWriter(filename); // Create file
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(s.toString());
			out.close();// Close the output stream
			return fstream;
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return null;
		}
	}

	File toXML(Appointment app) {
		// TODO

		return null;
	}

}
