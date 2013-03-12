package client.model;

public class User {
	
	private int id;
	private String name;
	private String email;
	private int phonenumber;
	
	public User(String name, String email, int phonenumber) {
		
		this.name = name;
		this.email = email;
		this.phonenumber = phonenumber;
		
	}
	
	public void createProfile() {
		
	}
	
	public void editProfile() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(int phonenumber) {
		this.phonenumber = phonenumber;
	}

}
