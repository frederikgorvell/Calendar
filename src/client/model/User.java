package client.model;


public class User {
	
	private final int id;
	private String name;
	private String email;
	private int phonenumber;
	private String username;
	private String password;
	XMLConverter converter = new XMLConverter();
	
	public User(int id, String name, String email, int phonenumber, String username, String password) {	
		this.id = id;
		this.name = name;
		this.email = email;
		this.phonenumber = phonenumber;
		this.username = username;
		this.password = password;
		converter.toXML(this);
	}
	
	public int getId() {
		return id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
