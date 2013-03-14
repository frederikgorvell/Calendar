package client.ui;

import java.io.File;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import client.model.*;
import client.net.*;

public class UserInterface {
	
	private XMLConverter xmlc;
	private Scanner scan;
	private String hostAddr;
	private int port;
	
	public UserInterface(String hostAddr, int port) throws Exception /* CHANGE! */{
		this.hostAddr = hostAddr;
		this.port = port;
		xmlc = new XMLConverter();
		scan = new Scanner(System.in);
		boolean loginOK = false;
		while (!loginOK) {
			loginOK = login();
		}
		ClientSocket cs = new ClientSocket();
		new Shell(cs);
	}
	
	private boolean login() {
		System.out.println("Client started...");
		ClientConnection cc = new ClientConnection(hostAddr, port);

		boolean connection = cc.openConnection();

		if (connection) {
			System.out.print("Enter username: ");
			String username = scan.nextLine();
			System.out.print("Enter password: ");
			String password = scan.nextLine();
			
			Login login = new Login(username, password);
			File send = xmlc.toXML(login, "login.xml");
			cc.sendObject(send);
			File recieve = cc.receiveObject();
			return true;
			
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
		return false;
		
		
	}
	
	private boolean loginPrompt() {
		System.out.print("Enter username: ");
		String username = scan.nextLine();
		System.out.print("Enter password: ");
		String password = scan.nextLine();
		Login log = new Login(username, password);
		/*if (isCorrect(log)) {
			return true;
		}*/
		return true;
	}
	
	//public static void main(String[] args) throws Exception /* CHANGE! */ {
		//new UserInterface();
	//}


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
		private XMLConverter xmlC;
		private ClientSocket clientSocket;
	
		Shell(ClientSocket clientSocket) throws IOException {
			this.clientSocket = clientSocket;
			xmlC = new XMLConverter();
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
				//TODO
			} else if (userInput.command.equals("previous")) {
				//TODO
			} else if (userInput.command.equals("new")) {
				newAppointment();
			} else if (userInput.command.equals("delete")) {
				//TODO
			} else if (userInput.command.equals("view")) {
				//TODO
			} else if (userInput.command.equals("edit")) {
				//TODO
			} else if (userInput.command.equals("book")) {
				//TODO
			} else if (userInput.command.equals("invite")) {
				//TODO
			} else if (userInput.command.equals("help")) {
				System.out.println("Available commands:");
				System.out.println("  week								- view the current week with appointments");
				System.out.println("  next								- view next week");
				System.out.println("  previous							- view previous week");
				System.out.println("  new								- make a new appointment, options will be prompted");
				System.out.println("  delete <appointmentID>			- delete an appointment");
				System.out.println("  view <appointmentID>				- view information about an appointment");
				System.out.println("  edit <appointmentID>				- edit an appointment");
				System.out.println("  book <appointmentID> <extraSeats>	- make a reservation for a meeting room");
				System.out.println("  invite <appointmentID>			- invite users to a meeting");
				System.out.println("  help								- display this helpscreen");
				/*System.out.println("  exit						        - exit program");
			} else if (userInput.command.equals("exit")) {
				System.exit(0);*/
			} else {
				System.out.println("Invalid command!");
			}
		}
		
		private void newAppointment() {
			//TODO

			System.out.print("Appointment name: ");
			String name = scan.nextLine();
			GregorianCalendar start = askUserStart();
			GregorianCalendar end = askUserEnd();
			while (!end.after(start)) {
				System.out.println("End time must be after start time!");
				start = askUserStart();
				end = askUserEnd();
			}
			System.out.print("Description: ");
			String description = scan.nextLine();
			System.out.print("Location: ");
			String location = scan.nextLine();
			System.out.println("week: " + start.get(Calendar.WEEK_OF_YEAR));
			System.out.println("start: " + makeDateString(start));
			Appointment a = new Appointment(name, makeDateString(start), makeDateString(end), description, location);
			a.setWeek(start.get(Calendar.WEEK_OF_YEAR));
			
			File f = xmlC.toXML(a, "appointment");
			clientSocket.send(f);
		}
		
		private GregorianCalendar askUserStart() {
			//TODO
			System.out.print("Start time (dd.mm.yyyy hh:mm): ");
			String time = scan.nextLine();
			boolean dateOK = false;
			while (!dateOK /* || time = "cancel"*/) {
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
		
		private GregorianCalendar askUserEnd() {
			//TODO
			System.out.print("End time (dd.mm.yyyy hh:mm): ");
			String time = scan.nextLine();
			boolean dateOK = false;
			while (!dateOK /* || time = "cancel"*/) {
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
	
	}
}

