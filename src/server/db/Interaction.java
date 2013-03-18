package server.db;

import java.sql.ResultSet;


public class Interaction {

	MySQLAccess access = new MySQLAccess();

	public boolean addGroup(int participantid, String name, String email, int groupid) {

		String groupSQL = "INSERT INTO groups  VALUES (" + participantid + ",'" + name + "','" 
			+ email + "','" + groupid + ");";

		try {
			access.initialize();
			access.makeSingleUpdate(groupSQL);
			System.out.println("Group was added.");
			access.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
			//System.out.println("Was not able to insert group.");
		}

	}

	public boolean joinGroup(int groupid, int participantid ) {

		String groupSQL = "INSERT INTO partof  VALUES (" + participantid + "," + groupid + ");";

		try {
			access.initialize();
			access.makeSingleUpdate(groupSQL);
			System.out.println("You are now a part of group: " + groupid);
			access.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
			//System.out.println("Was not able to join group.");
		}

	}

	public boolean attendMeeting(int id) {

		String attendSQL = "INSERTO INTO attending VALUES (" + id + "," + access.getUserID() + ";";

		try {
			access.initialize();
			access.makeSingleUpdate(attendSQL);
			System.out.println("You are now attending.");
			access.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
			//System.out.println("Was not able to insert.");
		}


	}

	public boolean addMeeting(int id, String start, String end, String descr, int roomid, String date) {

		String appointmentSQL = "INSERT INTO appointment VALUES (" + id + ",'" + start + "','" 
		+ end + "','" + descr + "','" + access.getUser() + "'," + roomid + ",'" + date +"');";

		String attendSQL = "INSERT INTO attending VALUES (" + id + "," + access.getUserID() + ";";

		try {
			access.initialize();
			access.makeSingleUpdate(appointmentSQL);
			access.makeSingleUpdate(attendSQL);
			System.out.println("Meeting was summoned.");
			access.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
			//System.out.println("Was not able to insert.");
		}

	}

	public boolean deleteAppointment(int id) {
		String appointmentSQL = "DELETE FROM appointment WHERE AID = " + id +	 ";";

		try {
			access.initialize();
			access.makeSingleUpdate(appointmentSQL);
			System.out.println("Appointment was deleted.");
			access.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
			//System.out.println("Was not able to delete.");
		}

	}
	
	public int getMaxAID() {
		String sql = "SELECT MAX(AID) FROM appointment";
		try {
			access.initialize();
			access.makeSingleQuery(sql);
			ResultSet rs = access.makeSingleQuery(sql);
			rs.next();
			String aid = rs.getString(1);
			return Integer.parseInt(aid);
			/*while(rs.next())
			{
	//			String aid = rs.getString(1);
				String start = rs.getString(2);
				String end = rs.getString(3);
				String descr = rs.getString(4);
				String status = rs.getString(5);
				String room = rs.getString(7);
	
				System.out.println(start + " " + end + " " + descr + " " + status + " " + room );
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	//NY!
	public boolean addAppointment(int id, String name, String username, int start, int end, String descr, String loc) {

		String appointmentSQL = "INSERT INTO appointment VALUES (" + id + ",'" + name + ",'" + start + "','" 
			+ end + "','" + descr + "','" + username + "'," + loc +"');";

		try {
			access.initialize();
			access.makeSingleUpdate(appointmentSQL);
			System.out.println("Appointment was added.");
			access.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
			//System.out.println("Was not able to insert.");
		}

	}

	public boolean addAppointment(int id, String start, String end, String descr, int roomid, String date) {

		String appointmentSQL = "INSERT INTO appointment VALUES (" + id + ",'" + start + "','" 
			+ end + "','" + descr + "','" + access.getUser() + "'," + roomid + ",'" + date +"');";

		try {
			access.initialize();
			access.makeSingleUpdate(appointmentSQL);
			System.out.println("Appointment was added.");
			access.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
			//System.out.println("Was not able to insert.");
		}

	}

	//The user must enter all appointment data over again (for simplicity).
	public boolean editAppointment(int id, String start, String end, String descr, int roomid, String date) {
		String appointmentSQL = "UPDATE appointment SET starttime = '" 
			+ start + "', endtime = '" 
			+ end + "', description = '" + descr + "', roomID = " + roomid + ", date = '" 
			+ date +"' WHERE AID = " + id + ";";

		try {
			access.initialize();
			access.makeSingleUpdate(appointmentSQL);
			System.out.println("Appointment successfully edited.");
			access.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
			//System.out.println("Was not able to update.");
		}
	}

	public ResultSet getAllAppointments() {
		String sql = "SELECT * FROM appointment";
		try {
			access.initialize();
			access.makeSingleQuery(sql);
			ResultSet rs = access.makeSingleQuery(sql);
			//System.out.println("ID START END DESCRIPTION STATUS ROOM");
			/*while(rs.next())
			{
				String aid = rs.getString(1);
				String start = rs.getString(2);
				String end = rs.getString(3);
				String descr = rs.getString(4);
				String status = rs.getString(5);
				String room = rs.getString(7);
	
				System.out.println(aid + " " + start + " " + end + " " + descr + " " + status + " " + room );
	//			System.out.println(String.format("%s %s %s %s %s %s", aid, start, end, descr, status, room));
			}*/
			access.close();
			return rs;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
			//System.out.println("Was not able to get appointments.");
		}

	}

	public ResultSet getUserCalendar() {

		String sql = "SELECT * FROM appointment WHERE username = " + access.getUser() + ";";
		try {
			access.initialize();
			access.makeSingleQuery(sql);
			ResultSet rs = access.makeSingleQuery(sql);
			System.out.println("ID START END DESCRIPTION STATUS ROOM");
			/*while(rs.next())
			{
				String aid = rs.getString(1);
				String start = rs.getString(2);
				String end = rs.getString(3);
				String descr = rs.getString(4);
				String status = rs.getString(5);
				String room = rs.getString(7);
	
				System.out.println(aid + " " + start + " " + end + " " + descr + " " + status + " " + room );
	//			System.out.println(String.format("%s %s %s %s %s %s", aid, start, end, descr, status, room));
			}*/
			access.close();
			return rs;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
			//System.out.println("Was not able to get appointments.");
		}

	}

	public ResultSet getAppointment(int id) {
		String sql = "SELECT * FROM appointment WHERE AID = " + id;
		try {
			access.initialize();
			access.makeSingleQuery(sql);
			ResultSet rs = access.makeSingleQuery(sql);
			/*while(rs.next())
			{
	//			String aid = rs.getString(1);
				String start = rs.getString(2);
				String end = rs.getString(3);
				String descr = rs.getString(4);
				String status = rs.getString(5);
				String room = rs.getString(7);
	
				System.out.println(start + " " + end + " " + descr + " " + status + " " + room );
			}*/
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not find appointment.");
			return null;
		}
	}



}
