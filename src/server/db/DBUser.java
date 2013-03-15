package server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import server.db.databaseConnection.DBConnection;
import client.model.User;

public class DBUser {
	private DBConnection db;
	
	public DBUser(DBConnection conn) {
		this.db = db;
	}
	
	public User createObject(ResultSet rs) {
		User user;
		try {
			int id = rs.getInt("participantID");
			String name = rs.getString("name");
			String email = rs.getString("email");
			String username = rs.getString("username");
			String password = rs.getString("password");
			
			user = new User(id,name,email,username,password);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public void addUser(int id, String name, String email, String username, String password) {
		String query = "INSERT INTO PERSON VALUES ('" + id + "','" + name + "','" + email + "','" + username + "','" + password + "')";
		db.makeSingleUpdate(query);
	}
	
	public void addUser(User u) {
		addUser(u.getId(), u.getName(), u.getEmail(), u.getUsername(), u.getPassword());
	}
	
	public void deleteUser(String username) {
		String query = "DELETE FROM PERSON WHERE username like '" + username + "'";
		db.makeSingleUpdate(query);
	}
	
	public void editUser(User editedUser) {
		String query = "UPDATE PERSON SET name = '" + editedUser.getName() +
				"', email = '" + editedUser.getEmail() +
				"', username = '"+ editedUser.getUsername() +
				"', password = '"+ editedUser.getPassword() + 
				"' WHERE username like '" + editedUser.getUsername()+ "'";
		db.makeSingleUpdate(query);
	}
	
	public User getUser(String username) {
		String query = "SELECT * FROM PERSON WHERE username like '"+ username + "'";
		ResultSet rs = db.makeSingleQuery(query);
		User user;
		try {
			if (rs.next())
				user = createObject(rs);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
