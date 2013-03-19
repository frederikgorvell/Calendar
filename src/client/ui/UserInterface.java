package client.ui;


import java.io.File;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import shared.XMLConverter;
import shared.model.Appointment;

import client.model.Login;
import client.net.SocketClient;

public class UserInterface {
	
	private Scanner scan;
	private String hostAddr;
	private int port;
	private SocketClient clientSocket;
	private String username;
	private int weekNr;
	
	public UserInterface(String hostAddr, int port) throws Exception /* CHANGE! */{
		this.hostAddr = hostAddr;
		this.port = port;
		GregorianCalendar time = new GregorianCalendar();
		weekNr = time.get(Calendar.WEEK_OF_YEAR);
//		xmlc = new XMLConverter();
		scan = new Scanner(System.in);
		System.out.println("Client started...");
		while(!connect()) {
			System.out.println("Could not connect, press any button to retry");
			scan.nextLine();
		}
		
		boolean loginOK = false;
		
		while (!loginOK) {
			loginOK = login();
//			loginOK = loginPrompt();
		}
		
//		ClientSocket cs = new ClientSocket();
		new Shell();
	}
	
	private boolean connect() {
		clientSocket = new SocketClient(hostAddr, port);
		boolean connection = clientSocket.openConnection();
		return connection;
	}
	
	private boolean login() {
		
		
		System.out.print("Enter username: ");
		username = scan.nextLine();
		System.out.print("Enter password: ");
		String password = scan.nextLine();
		
		Login login = new Login(username, password);
		File loginFile = XMLConverter.toXML(login, "calendar.xml");
//			File loginFile = xmlc.toXML(login, "login.xml");
		clientSocket.send(loginFile);
		File received = clientSocket.receiveObject();
		return XMLConverter.isConfirmed(received);
			
			/*
			Request request = new Request();
			request.setRequest(Request.LOGIN);
			request.addItem("username", "herpderp@gmail.com");
			request.addItem("password", "passord");
			cc.sendObject(request);
			Response response = cc.reciveObject();
			cc.closeConnection();
			System.out.println("error " + response.getItem("error"));
			System.out.println("result:" + " " + response.getItem("result"));
			*/
		
		
		
		
	}
	
	private boolean loginPrompt() {
		System.out.print("Enter username: ");
		String username = scan.nextLine();
		System.out.print("Enter password: ");
		String password = scan.nextLine();
		Login login = new Login(username, password);
		/*if (isCorrect(log)) {
			return true;
		}*/
		return true;
	}
	
	//public static void main(String[] args) throws Exception /* CHANGE! */ {
		//new UserInterface();
	//}
	
	class ReceiverThread extends Thread {
		//receive from server, alternate ask for updates on invites etc
		//don't know if i need multiple SocketClient
		public void run() {
			
		}
	}


	class UserInput {
		String command;
		String argument1;
		String argument2;
		UserInput(String command, String argument1, String argument2) {
			this.command = command;
			this.argument1 = argument1;
			this.argument2 = argument2;
		}
	}
	
	class CommandFeed {
		String command;
		String argument1;
		String argument2;

		Scanner terminal = new Scanner(System.in);
		boolean hasMoreCommands() {
			return scan.hasNextLine();
		}
		UserInput nextCommand() {
			String[] line = scan.nextLine().split(" ");

			
			if (line == null || line[0] == null || line[0].isEmpty()) {
				command = "help";
				argument1 = null;
				argument2 = null;
			} else if (line.length == 1) {
				command = line[0];
				argument1 = null;
				argument2 = null;
			} else if (line.length == 2) {
				command = line[0];
				argument1 = line[1];
				argument2 = null;
			} else if (line.length == 3) {
				command = line[0];
				argument1 = line[1];
				argument2 = line[2];
			} else {
				command = "help";
				argument1 = null;
				argument2 = null;
			}
			
			return new UserInput(command, argument1, argument2);
		}
	}
	
	class Shell {
		CommandFeed cli	= new CommandFeed();
		Shell() throws IOException {
			printInputPrefix();
			while(cli.hasMoreCommands()) {
				handleUserInput();
				printInputPrefix();
			}
		}
	
		void printInputPrefix() {
			System.out.print("Calendar> ");
			//GregorianCalendar gc = new GregorianCalendar(2013, 2, 30, 25, 61);
			//System.out.println(gc.get(Calendar.DAY_OF_MONTH));
		}
	
		void handleUserInput() throws IOException {
			UserInput userInput = cli.nextCommand();
			String a1 = userInput.argument1;
			String a2 = userInput.argument2;
			if(userInput.command.equals("week")) {
				if (viewWeek(weekNr)) {
					
				}
				
				//TODO
				/*
				if (a1 == null) {
					System.out.println("Provide a name for the person you want to add.");
				} else if (a2 == null) { 
					if (myList.addPerson(a1, "n/a")) {
						System.out.println("Added person " + a1 + ".");
					} else {	
						System.out.println(a1 + " already exists in the list.");
					}
				} else if (myList.addPerson(a1, a2)) {
					System.out.println("Added person " + a1 + " with phone " + a2 + ".");
				} else {
					System.out.println(a1 + " already exists in the list.");
				}*/
			} else if (userInput.command.equals("next")) {
				weekNr++;
				viewWeek(weekNr);
			} else if (userInput.command.equals("previous")) {
				weekNr--;
				viewWeek(weekNr);
			} else if (userInput.command.equals("new")) {
				if(newAppointment()) {
					System.out.println("Appointment created");
				} else {
					System.out.println("Error in creating an appointment");
				}
			} else if (userInput.command.equals("delete")) {
				try {
					if (deleteAppointment(Integer.parseInt(a1))) {
						System.out.println("Appointment deleted!");
					} else {
						System.out.println("Could not delete appointment!");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid appointmentID, must be a number!");
				}
			} else if (userInput.command.equals("view")) {
				try {
					Appointment a = getAppointment(Integer.parseInt(a1));
					if (a == null) {
						System.out.println("Can't view appointment with appointmentID=" + a1);
					}
					viewAppointment(a);
				} catch (NumberFormatException e) {
					System.out.println("Invalid appointmentID, must be a number!");
				}
			} else if (userInput.command.equals("edit")) {
				try {
					if (editAppointment(Integer.parseInt(a1))) {
						System.out.println("Appointment successfully edited!");
					} else {
						System.out.println("Could not edit appointment!");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid appointmentID, must be a number!");
				}
			} else if (userInput.command.equals("book")) {
				//TODO
			} else if (userInput.command.equals("invite")) {
				try {
					System.out.print("Username to invite: ");
					String invitedUser = scan.nextLine();
					if (invite(Integer.parseInt(a1), invitedUser)) {
						System.out.println(invitedUser + " invited to appointment " + a1);
					} else {
						System.out.println("Could not invite " + invitedUser + " to appointment " + a1);
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					System.out.println("Invalid appointmentID, must be a number!");
				}
				//TODO
			} else if (userInput.command.equals("help")) {
				System.out.println("Available commands:");
				System.out.println("  week					- view the current week with appointments");
				System.out.println("  next					- view next week");
				System.out.println("  previous					- view previous week");
				System.out.println("  new					- make a new appointment, options will be prompted");
				System.out.println("  delete <appointmentID>			- delete an appointment");
				System.out.println("  view <appointmentID>			- view information about an appointment");
				System.out.println("  edit <appointmentID>			- edit an appointment");
				System.out.println("  book <appointmentID> <extraSeats>		- make a reservation for a meeting room");
				System.out.println("  invite <appointmentID>			- invite users to a meeting");
				System.out.println("  help					- display this helpscreen");
				System.out.println("  exit					- exit program");
			} else if (userInput.command.equals("exit")) {
				clientSocket.closeConnection();
				System.out.println("Connection closed");
				System.exit(0);
			} else {
				System.out.println("Invalid command!");
			}
		}
		
		private boolean viewWeek(int week) {
			try {
				Appointment appointment = new Appointment();
				appointment.setCreator(username);
				appointment.setWeek(week);
				
				File sendFile = XMLConverter.toXML(appointment, "calendar.xml", "week");
				clientSocket.send(sendFile);
				File receiveFile = clientSocket.receiveObject();
				ArrayList<Appointment> appList = XMLConverter.makeAppointment(receiveFile);
				for (Appointment a : appList) {
					System.out.print("Week number: " + week);
					int dayNr = 1;
					while(dayNr <=7) {
						if (dayNr == 1) {
							System.out.print("\nMonday: ");
						} else if (dayNr == 2) {
							System.out.print("\nTuesday: ");
						} else if (dayNr == 3) {
							System.out.print("\nWednesday: ");
						} else if (dayNr == 4) {
							System.out.print("\nThursday: ");
						} else if (dayNr == 5) {
							System.out.print("\nFriday: ");
						} else if (dayNr == 6) {
							System.out.print("\nSaturday: ");
						} else if (dayNr == 7) {
							System.out.print("\nSunday: ");
						}
						if (equalsDay(a.getStart(), dayNr)) {
							System.out.print(a.getAID() + " " + a.getName() + " " + getTime(a.getStart()) + "-" + getTime(a.getEnd()) + "; ");
						} else {
							dayNr++;
						}
					}
				}
				return true;
			} catch (Exception e) {
				System.out.println("Could not view week");
				e.printStackTrace();
				return false;
			}
		}
		
		private boolean newAppointment() {
			//TODO
			System.out.print("Appointment name: ");
			String name = scan.nextLine();
			GregorianCalendar start = askUserStart(false);
			GregorianCalendar end = askUserEnd(false);
			while (!end.after(start)) {
				System.out.println("End time must be after start time!");
				start = askUserStart(false);
				end = askUserEnd(false);
			}
			System.out.print("Description: ");
			String description = scan.nextLine();
			System.out.print("Location: ");
			String location = scan.nextLine();
			System.out.println("week: " + start.get(Calendar.WEEK_OF_YEAR));
			System.out.println("start: " + makeDateString(start));
			Appointment a = new Appointment(name, makeDateString(start), makeDateString(end), description, location);
			a.setCreator(username);
			a.setWeek(start.get(Calendar.WEEK_OF_YEAR));
		
			File sendFile = XMLConverter.toXML(a, "calendar.xml", "new");
			clientSocket.send(sendFile);
			File receiveFile = clientSocket.receiveObject();
			a.setAID(XMLConverter.getAID(receiveFile));
			if (a.getAID() == -1) {
				System.out.println("AID = -1");/////////////////////////////////////
				return false;
			}
			return XMLConverter.isConfirmed(receiveFile);
			
		}
		
		private boolean deleteAppointment(int AID) {
			Appointment a = new Appointment(AID);
			File sendFile = XMLConverter.toXML(a, "calendar.xml", "delete");
			clientSocket.send(sendFile);
			File receiveFile = clientSocket.receiveObject();
			return XMLConverter.isConfirmed(receiveFile);
		}
		
		private Appointment getAppointment(int AID) {
			Appointment a = new Appointment(AID);
			
			File sendFile = XMLConverter.toXML(a, "calendar.xml", "view");
			clientSocket.send(sendFile);
			File receiveFile = clientSocket.receiveObject();
			a = XMLConverter.makeAppointment(receiveFile).get(0);
			return a;
		}
		
		private void viewAppointment(Appointment a) {
			if (a != null) {
				System.out.println("AppointmentID: " + a.getAID());
				System.out.println("Name: " + a.getName());
				System.out.println("Start: " + a.getStart());
				System.out.println("End: " + a.getEnd());
				System.out.println("Week: " + a.getWeek());
				System.out.println("Description: " + a.getDescription());
				System.out.println("Location: " + a.getLocation());
			}
		}
		
		private boolean editAppointment(int AID) {
			Appointment a = getAppointment(AID);
			if (a == null) return false;
			a.setCreator(username);
			viewAppointment(a);
			System.out.println("Submit attributes to be changed, just press enter for attributes not to be changed");
			System.out.print("Appointment name: ");
			String name = scan.nextLine();
			if (name != null) {
				a.setName(name);
			}
			GregorianCalendar start = askUserStart(true);
			if (start != null) {
				a.setStart(makeDateString(start));
			}
			GregorianCalendar end = askUserEnd(true);
			if (end != null) {
				a.setEnd(makeDateString(end));
			}
			while (!end.after(start)) {
				System.out.println("End time must be after start time!");
				start = askUserStart(true);
				if (start != null) {
					a.setStart(makeDateString(start));
				}
				end = askUserEnd(true);
				if (end != null) {
					a.setEnd(makeDateString(end));
				}
			}
			System.out.print("Description: ");
			String desc = scan.nextLine();
			if (desc != null) {
				a.setDescription(desc);
			}
			System.out.print("Location: ");
			String location = scan.nextLine();
			if (location != null) {
				a.setLocation(location);
			}
			if (start.get(Calendar.WEEK_OF_YEAR) != a.getWeek()) {
				a.setWeek(start.get(Calendar.WEEK_OF_YEAR));
			}
		
			File sendFile = XMLConverter.toXML(a, "calendar.xml", "edit");
			clientSocket.send(sendFile);
			File receiveFile = clientSocket.receiveObject();
			return XMLConverter.isConfirmed(receiveFile);
			/*
			a.setAID(XMLConverter.getAID(receiveFile));
			if (a.getAID() == -1) {
				System.out.println("AID = -1");/////////////////////////////////////
				return false;
			}
			return XMLConverter.isConfirmed(receiveFile);
			return false;*/
		}
		
		private boolean invite(int AID, String invitedUser) {
			File sendFile = XMLConverter.makeInvite(AID, invitedUser);
			clientSocket.send(sendFile);
			File receiveFile = clientSocket.receiveObject();
			return XMLConverter.isConfirmed(receiveFile);
		}
		
		private GregorianCalendar askUserStart(boolean edit) {
			//TODO
			System.out.print("Start time (dd.mm.yyyy hh:mm): ");
			String time = scan.nextLine();
			boolean dateOK = false;
			while (!dateOK /* || time = "cancel"*/) {
				if (edit && time == null) {
					return null;
				}
				if (isLegalTime(time)) {
					dateOK = true;
				} else {
					System.out.println("Wrong date format!");
					System.out.print("Start time (dd.mm.yyyy hh:mm): ");
					time = scan.nextLine();
				}
			}
			/* if time == "cancel" ...*/
			String[] rawList = time.split(" ");
			String[] date = rawList[0].split("\\.");
			String[] clock = rawList[1].split(":");
			int year = Integer.parseInt(date[2]);
			int month = Integer.parseInt(date[1]) - 1;
			int day = Integer.parseInt(date[0]);
			int hour = Integer.parseInt(clock[0]);
			int minute = Integer.parseInt(clock[1]);
			GregorianCalendar gc = new GregorianCalendar(year, month, day, hour, minute);
			gc.setMinimalDaysInFirstWeek(4);
			gc.setFirstDayOfWeek(Calendar.MONDAY);
			return gc;
		}
		
		private GregorianCalendar askUserEnd(boolean edit) {
			//TODO
			System.out.print("End time (dd.mm.yyyy hh:mm): ");
			String time = scan.nextLine();
			
			boolean dateOK = false;
			while (!dateOK /* || time = "cancel"*/) {
				if (edit && time == null) {
					return null;
				}
				if (isLegalTime(time)) {
					dateOK = true;
				} else {
					System.out.println("Wrong date format!");
					System.out.print("End time (dd.mm.yyyy hh:mm): ");
					time = scan.nextLine();
				}
			}
			/* if time == "cancel" ...*/
			String[] rawList = time.split(" ");
			String[] date = rawList[0].split("\\.");
			String[] clock = rawList[1].split(":");
			int year = Integer.parseInt(date[2]);
			int month = Integer.parseInt(date[1]) - 1;
			int day = Integer.parseInt(date[0]);
			int hour = Integer.parseInt(clock[0]);
			int minute = Integer.parseInt(clock[1]);
			GregorianCalendar gc = new GregorianCalendar(year, month, day, hour, minute);
			gc.setMinimalDaysInFirstWeek(4);
			gc.setFirstDayOfWeek(Calendar.MONDAY);
			return gc;
		}
		
		private boolean isLegalTime(String s) {
			try {
				String[] rawList = s.split(" ");
				String[] time = rawList[1].split(":");
				int hour = Integer.parseInt(time[0]);
				int minute = Integer.parseInt(time[1]);
				if (0 <= hour && hour < 24 && 0 <= minute && minute < 60 && isLegalDate(rawList[0])) {
					return true;
				}
				return false;
			} catch (Exception e) {
				return false;
			}
		}
		
		private boolean isLegalDate(String s) {
		    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		    sdf.setLenient(false);
		    return sdf.parse(s, new ParsePosition(0)) != null;
		}
		
		private String makeDateString(GregorianCalendar gc) {
			StringBuilder sb = new StringBuilder();
			sb.append(gc.get(Calendar.YEAR));
			sb.append("-");
			sb.append(gc.get(Calendar.MONTH));
			sb.append("-");
			sb.append(gc.get(Calendar.DAY_OF_MONTH));
			sb.append(" ");
			sb.append(gc.get(Calendar.HOUR));
			sb.append(":");
			sb.append(gc.get(Calendar.MINUTE));
			return sb.toString();
		}
		
		private String getTime(String dateTime) {
			return dateTime.split(" ")[1];
		}
		
		private boolean equalsDay(String date, int dayNr) {
			System.out.println(date);
			String[] dateRaw = date.split("")[0].split("-");
			int year = Integer.parseInt(dateRaw[0]);
			int month = Integer.parseInt(dateRaw[1]) - 1;
			int day = Integer.parseInt(dateRaw[2]);
			GregorianCalendar gc = new GregorianCalendar(year, month, day);
			
			return gc.get(Calendar.DAY_OF_WEEK) - 1 == dayNr;
		}
	
	}
}
