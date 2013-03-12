package client.ui;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import client.model.Login;



public class UserInterface {
	
	public UserInterface() throws Exception /* CHANGE! */{
		boolean loginOK = false;
		while (!loginOK) {
			loginOK = loginPrompt();
		}
		new Shell();
	}
	
	private boolean loginPrompt() {
		Scanner term = new Scanner(System.in);
		System.out.print("Enter username: ");
		String username = term.nextLine();
		System.out.print("Enter password: ");
		String password = term.nextLine();
		Login log = new Login(username, password);
		/*if (log.isCorrect()) {
			return true;
		}*/
		return true;
	}
	
	public static void main(String[] args) throws Exception /* CHANGE! */ {
		new UserInterface();
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
		return terminal.hasNextLine();
	}
	UserInput nextCommand() {
		String[] line = terminal.nextLine().split(" ");
		
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
			System.out.println("Unvalid command.");
		}
	}
	
	private void newAppointment() {
		//TODO
		Date start = askUserStart();
		Date end = askUserEnd();
		
		//Appointment a = new Appointment(start, end, "normal");
		/*
		a.setName
		System.out.print("name: ");
		String name = sc.nextLine();
		while (name == null) {
			System.out.println("Appointment must have a name!");
			name = sc.nextLine();
		}
		a.name = name;
		System.out.print("start time: ");
		int startTime = sc.nextLine();
		while (startTime == null) {
			System.out.println("Appointment must have a name!");
			name = sc.nextLine();
		}
		*/
	}
	
	private Date askUserStart() {
		//TODO
		Scanner sc = new Scanner(System.in);
		System.out.print("Start time (dd.mm.yyyy hh:mm): ");
		String[] rawList = sc.nextLine().split(" ");
		String date = rawList[0];
		return null;
		
	}
	private Date askUserEnd() {
		//TODO
		return null;
	}

}
