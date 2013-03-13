package client.model;

import java.io.File;
import java.io.FileWriter;

public class XMLtestdriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Login login = new Login("martin","12345678");
		Appointment app = new Appointment();
		XMLConverter con = new XMLConverter();
		File fLogin = con.toXML(login,"login.xml");
		//File fApp = con.toXML(app, "appointment.xml");
		
		Login returnedLogin = (Login) con.readXMLintoObject("login.xml");
		System.out.print(returnedLogin.getUsername()+ " " +returnedLogin.getPassword());
	}

}
