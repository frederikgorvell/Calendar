package client.model;


import java.io.File;
import java.io.FileWriter;

import shared.XMLConverter;


public class XMLtestdriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Login l = new Login("martin","12345678");
		XMLConverter con = new XMLConverter();
		File f = con.toXML(l,"login.xml");

	}

}
