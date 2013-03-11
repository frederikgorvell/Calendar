package client.model;

import java.io.FileWriter;

public class XMLtestdriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Login l = new Login("martin","12345678");
		XMLConverter con = new XMLConverter();
		FileWriter f = con.toXML(l,"login.xml");
//		FileReader fr = f;
	}

}
