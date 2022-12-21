package app.services;

import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import app.dto.ImageResultDto;
import app.entities.Issue;
import app.entities.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
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
	
	//NOT USE IN THE PROGRAM JUST FOR DEV
	private static void createNewDatabase(String fileName) {

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
	
	//NOT USE IN THE PROGRAM JUST FOR DEV
	private static void createNewTable() {
		String path = "jdbc:sqlite:src\\main\\resources\\db\\app.db";
		
		//SQL Statement 
		String sql = "CREATE TABLE IF NOT EXISTS favorites (\n"
				+ "     favorite_id integer PRIMARY KEY,\n"
				+ " 	issue_id integer,\n"
				+ "     user_id integer,\n"
				+ "		FOREIGN KEY(issue_id) REFERENCES issues(issue_id),\n"
				+ "     FOREIGN KEY(user_id) REFERENCES users(user_id)\n"
				+ ");";
		
		try (Connection conn = DriverManager.getConnection(path); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Users table methods
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
		String sql = "SELECT user_id, first_name, last_name, username FROM users WHERE username=" 
				+ '"' +  username + '"' 
				+ " AND password=" + '"' + password + '"'
				+ " LIMIT 1";
		
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			return new User(true,rs.getInt("user_id"), rs.getString("username"),rs.getString("first_name"),rs.getString("last_name"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		
	}
	
	// Issues table methods
	public void addNewIssue(int issue_id, int issue_number, String issue_name, String api_detail_url, String image_url, String store_date) {
		
		// FIRST CHECK IF THE ENTRY DOESNT ALREADY EXIST
		boolean isTupleAlreadyExists = false;
		
		String sql = "SELECT * FROM issues WHERE issue_id = ?";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, issue_id);
			ResultSet rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				//tuple doesn't exist yet
				isTupleAlreadyExists = false;
			}
			else {
				// tuple already exists
				isTupleAlreadyExists = true;
				System.out.println("Tuple already exist !!!"); 
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// THEN ADD TUPLE IF NOT ALREAY EXISTS	
		if(!isTupleAlreadyExists) {
			sql = "INSERT INTO issues(issue_id,issue_number,issue_name,api_detail_url,image_url,store_date) VALUES(?,?,?,?,?,?)";
			
			//Conversion String to date
			//Date format in the input String
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			// Parsing Strings in util.date
			java.util.Date date_storeDate = null;
			try {
				date_storeDate = sdf.parse(store_date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			java.util.Date date_coverDate = null;
			
			Date sqlDate_storeDate = Date.valueOf(date_storeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			
			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, issue_id);
				pstmt.setInt(2, issue_number);
				pstmt.setString(3, issue_name);
				pstmt.setString(4, api_detail_url);
				pstmt.setString(5, image_url);
				pstmt.setDate(6, sqlDate_storeDate);
				pstmt.executeUpdate();
							
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// Favorites table methods
	public void addNewUserFavorite(int user_id, int issue_id) {
		
		//FIRST : CHECK IF TUPLE DOESN'T EXIST
		boolean isTupleAlreadyExists = false;
		
		String sql = "SELECT * FROM favorites WHERE issue_id = ? AND user_id = ?";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, issue_id);
			pstmt.setInt(2, user_id);
			ResultSet rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				//tuple doesn't exist yet
				isTupleAlreadyExists = false;
			}
			else {
				// tuple already exists
				isTupleAlreadyExists = true;
				System.out.println("Tuple already exist !!!"); 
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// THEN ADD TUPLE IF NOT ALREAY EXISTS	
		if(!isTupleAlreadyExists) {
			
			sql = "INSERT INTO favorites(issue_id,user_id) VALUES(?,?)";
			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setInt(1, issue_id);
				pstmt.setInt(2, user_id);
				pstmt.executeUpdate();
						
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}
	
	public void removeOneUserFavorite(int user_id, int issue_id) {
		String sql = "DELETE FROM favorites WHERE issue_id = ? AND user_id = ?";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, issue_id);
			pstmt.setInt(2, user_id);
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public List<Issue> getUserFavorites(int user_id){
		
		List<Issue> issues = new ArrayList<>();
		
		// GET all issues associated with the user_id
		String sql = "SELECT issues.* FROM issues INNER JOIN favorites ON favorites.issue_id = issues.issue_id WHERE favorites.user_id = " + String.valueOf(user_id);
		
		try (Connection conn = this.connect();Statement pstmt = conn.createStatement()) {

			ResultSet rs = pstmt.executeQuery(sql);
			
			while(rs.next())
			{
				// Build the issue
				Issue i = new Issue("", rs.getString("api_detail_url"), rs.getInt("issue_id"), rs.getString("issue_number"), rs.getString("issue_name"), rs.getString("image_url"));
				issues.add(i);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		
		return issues;
	}

}
