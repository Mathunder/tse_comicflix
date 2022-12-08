package app.services;

import java.sql.Statement;
import app.entities.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService {

	private Connection connect() {
		
		String path = "jdbc:sqlite:src\\main\\resources\\db\\app.db";
		
		Connection conn = null;
        try {
            conn = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
	}
	
	public static void createNewDatabase(String fileName) {
		
		String path = "jdbc:sqlite:src\\main\\resources\\db\\" + fileName;
		
		try(Connection conn = DriverManager.getConnection(path)) {
			if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public static void createNewUserTable() {
		String path = "jdbc:sqlite:src\\main\\resources\\db\\app.db";
		
		//SQL Statement 
		String sql = "CREATE TABLE IF NOT EXISTS users (\n"
				+ " 	user_id integer PRIMARY KEY,\n"
				+ " 	first_name text NOT NULL,\n"
				+ "		last_name text NOT NULL,\n"
				+ " 	username text NOT NULL,\n"
				+ "		password text\n"
				+ ");";
		
		try (Connection conn = DriverManager.getConnection(path); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addNewUserAccount(String first_name, String last_name, String username, String password) {
		String sql = "INSERT INTO users(first_name,last_name,username,password) VALUES(?,?,?,?)";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, first_name);
			pstmt.setString(2, last_name);
			pstmt.setString(3, username);
			pstmt.setString(4, password);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getAllUsers() {
		String sql = "SELECT * FROM users";
		
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while (rs.next()) {
				System.out.println(rs.getInt("user_id") + "\t" +
								   rs.getString("first_name") + "\t" +
								   rs.getString("last_name") + "\t" +
								   rs.getString("username") + "\t" + 
								   rs.getString("password"));
			}
			
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	
	public User getUserFromUsername(String username, String password) {
		String sql = "SELECT first_name, last_name, username FROM users WHERE username=" 
				+ '"' +  username + '"' 
				+ " AND password=" + '"' + password + '"'
				+ " LIMIT 1";
		
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			return new User(true,rs.getString("username"),rs.getString("first_name"),rs.getString("last_name"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		
	}

}
