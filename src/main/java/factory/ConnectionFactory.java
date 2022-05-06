package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	// Database connection variables
	private final static String URL = System.getenv("datachanURL") + "mydatabase";
	private final static String USERNAME = System.getenv("datachanUsername");
	private final static String PASSWORD = System.getenv("datachanPassword");
	
	static { // (this would normally go into your dao layer)
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Static block has failed me");
		}
	}
		
	public static Connection getConnection() throws SQLException {
		Connection conn =  DriverManager.getConnection(URL,USERNAME,PASSWORD);
		conn.setSchema("project1");
		return conn;
	}
	
	public static Connection getTestConnection() throws SQLException {
	    String url = "jdbc:h2:./src/test/resources/H2Folder/myMockDatabaseH2B";
	    String username = "sa";
	    String password = "sa";
		
		
		Connection conn =  DriverManager.getConnection(url,username,password);
		return conn;
	}
}
