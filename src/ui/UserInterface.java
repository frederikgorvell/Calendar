package ui;

import java.util.Scanner;

public class UserInterface {
	
	public UserInterface() {
		boolean loginOK = loginPrompt();
	}
	
	private boolean loginPrompt() {
		Scanner term = new Scanner(System.in);
		System.out.println("Enter username: ");
		String username = term.nextLine();
		return false;
	}
	
	public static void main(String[] args) {
		
	}
}
