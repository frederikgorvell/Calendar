package server.db;

import java.sql.*;

public class MySQLAccess{

	//Database and login information
    private Connection conn = null;
    private String url = "jdbc:mysql://localhost:3306/";
    private String dbName = "db2";
    private String driver = "com.mysql.jdbc.Driver";
    private String user = "root";
    private int userID = 99;

	private String password = "";
    
	public void initialize() throws ClassNotFoundException, SQLException {
	    try {
	        Class.forName(driver).newInstance();
	        conn = DriverManager.getConnection(url+dbName,user,password);
	        System.out.println("Connected to the database.");
	        } 
	    catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
	public void makeSingleUpdate(String sql) throws SQLException {
		Statement st = conn.createStatement();
		st.executeUpdate(sql);
	}

	public ResultSet makeSingleQuery(String sql) throws SQLException {
		Statement st = conn.createStatement();
		return st.executeQuery(sql);
	}

	public PreparedStatement preparedStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}

	public void close() throws SQLException {
		conn.close();
		System.out.println("Connection closed.");
	}

    public String getUser() {
		return this.user;
	}
    
	public void setUser(String user) {
		this.user = user;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
} 