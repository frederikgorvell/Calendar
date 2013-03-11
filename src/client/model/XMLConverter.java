// @author hengsti

package client.model;

import java.io.File;

public class XMLConverter {

	XMLConverter(){
		
	}
	
	String toXML(Login login){
		StringBuffer s = new StringBuffer();
		s.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		s.append("<Login>\n");
		
		s.append("<Username>\n");
		s.append(login.getUsername()+"\n");
		s.append("</Username>\n");
		
		s.append("<Password>\n");
		s.append(login.getUsername()+"\n");
		s.append("</Password>\n");
		
		s.append("</Login>");
		return s.toString();
	}
	
	File toXML(Appointment app){
		//TODO
		
		return null;
	}
	
}
