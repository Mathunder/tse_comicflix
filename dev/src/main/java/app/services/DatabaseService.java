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
import app.models.UserModel;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService {

	private UserModel userModel;
	
	public DatabaseService(UserModel um) {
		userModel = um;
	}
	
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
	
	public void loginUserFromUsername(String username, String password) {
		String sql = "SELECT user_id, first_name, last_name, username FROM users WHERE username=" 
				+ '"' +  username + '"' 
				+ " AND password=" + '"' + password + '"'
				+ " LIMIT 1";
		
		boolean isAuthenticated = false;
		
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			if(rs.getInt("user_id") != 0)
				isAuthenticated = true;
			
			userModel.setUser(isAuthenticated, new User(rs.getInt("user_id"), rs.getString("username"),rs.getString("first_name"),rs.getString("last_name")), getUserFavorites(rs.getInt("user_id")));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			userModel.setUser(false, new User(0,"Invité","",""),new ArrayList<>());
		}	
	}
	
	// Issues table methods
	public void addNewIssue(Issue issue) {
		
		if(issue.getIssue_number() == null) { //IF CHARACTER
			issue.setIssue_number("0");
		}
		
		int issue_id = issue.getId();
		int issue_number = Integer.parseInt(issue.getIssue_number());
		String issue_name = issue.getName();
		String api_detail_url = issue.getApi_detail_url();
		String image_url = issue.getImage().getMedium_url();
		
		
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
				System.out.println("Issue already exist !!!"); 
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// THEN ADD TUPLE IF NOT ALREAY EXISTS	
		if(!isTupleAlreadyExists) {
			sql = "INSERT INTO issues(issue_id,issue_number,issue_name,api_detail_url,image_url) VALUES(?,?,?,?,?)";
			
			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, issue_id);
				pstmt.setInt(2, issue_number);
				pstmt.setString(3, issue_name);
				pstmt.setString(4, api_detail_url);
				pstmt.setString(5, image_url);
				pstmt.executeUpdate();
							
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// Favorites table methods
	public void addNewUserFavorite(User user, Issue issue) {
		
		//FIRST : CHECK IF TUPLE DOESN'T EXIST
		boolean isTupleAlreadyExists = false;
		int user_id = user.getId();
		int issue_id = issue.getId();
		
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
				System.out.println("Favorite already exist for this user"); 
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
				
				//Add issue in the model
				userModel.addNewUserFavoriteIssue(issue);
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}
	
	public void removeOneUserFavorite(User user, Issue issue) {
		String sql = "DELETE FROM favorites WHERE issue_id = ? AND user_id = ?";
		int user_id = user.getId();
		int issue_id = issue.getId();
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, issue_id);
			pstmt.setInt(2, user_id);
			pstmt.executeUpdate();
			
			//Remove from model
			userModel.removeUserFavoriteIssue(issue);
		} 
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	//USELELL IN MVC Design ?
	private List<Issue> getUserFavorites(int user_id){
		
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
