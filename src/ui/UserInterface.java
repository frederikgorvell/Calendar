package ui;

import java.util.Scanner;

import model.Login;


public class UserInterface {
	
	public UserInterface() {
		boolean loginOK = loginPrompt();
	}
	
	private boolean loginPrompt() {
		Scanner term = new Scanner(System.in);
		System.out.println("Enter username: ");
		String username = term.nextLine();
		System.out.println("Enter password: ");
		String password = term.nextLine();
		Login log = new Login(username, password);
		if (log.isCorrect()) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		new UserInterface();
	}
}
