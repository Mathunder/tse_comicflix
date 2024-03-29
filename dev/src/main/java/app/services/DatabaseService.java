package app.services;

import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import app.dto.ImageResultDto;
import app.entities.Collection;
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
	private Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);
	
	public DatabaseService(UserModel um) {
		userModel = um;
	}
	
	private Connection connect() {
		
		String path = "jdbc:sqlite::resource:app.db";
		
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
	public void addNewUserAccount(String first_name, String last_name, String username, String password, String question) {
		
		String encodePassword = encoder.encode(password);
		String encodeQuestion = encoder.encode(question);
		String sql = "INSERT INTO users(first_name,last_name,username,password,question) VALUES(?,?,?,?,?)";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, first_name);
			pstmt.setString(2, last_name);
			pstmt.setString(3, username);
			pstmt.setString(4, encodePassword);
			pstmt.setString(5, encodeQuestion);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updatePassword(String password, String username)
	{
		String encodePassword = encoder.encode(password);
		String sql = "UPDATE users SET password =" + '"' + encodePassword + '"' + "WHERE username =" + '"' + username + '"';
		
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement();){
			
			stmt.executeUpdate(sql);
			
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
								   rs.getString("password")+ "\t" +
								   rs.getString("question"));
			}
			
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	
	
	public boolean verifUsername(String username) {
		String sql = "SELECT username FROM users WHERE username=" + '"' + username + '"' + " LIMIT 1";
		
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			if(!rs.next()) //Check if we have a result of the sql execute
			{
				return true;
			}
			else
			{
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean verifQuestion(String username, String question) {
		String sql = "SELECT question FROM users WHERE username=" + '"' + username + '"' + " LIMIT 1";
		
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			if(rs.next()) //Check if we have a result of the sql execute
			{
				if(encoder.matches(question, rs.getString("question")))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	public void loginUserFromUsername(String username, String password) {
		String sql = "SELECT user_id, first_name, last_name, username, password, question FROM users WHERE username=" 
				+ '"' +  username + '"'
				+ " LIMIT 1";
		
		boolean isAuthenticated = false;
		
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			if(rs.next())
			{

				if(encoder.matches(password, rs.getString("password")))
				{
					if(rs.getInt("user_id") != 0)
						isAuthenticated = true;
					
					userModel.setUser(isAuthenticated, new User(rs.getInt("user_id"), rs.getString("username"),rs.getString("first_name"),rs.getString("last_name")), getUserRecommandation(),getUserFavorites(rs.getInt("user_id")), getUserReading(rs.getInt("user_id")), getUserReaded(rs.getInt("user_id")), getAllUserCollection(rs.getInt("user_id")));
				}
				else
				{
					userModel.setUser(false, new User(0,"Invité","",""),new ArrayList<>(),new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
				}

			}
			else
			{
				userModel.setUser(false, new User(0,"Invité","",""),new ArrayList<>(),new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			userModel.setUser(false, new User(0,"Invité","",""),new ArrayList<>(),new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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
	
	// Recommandations methods
	public List<Issue> getUserRecommandation(){
		List<Issue> issuesResult = new ArrayList<>();
		
		String sql = "SELECT * FROM issues WHERE issue_id IN (SELECT issue_id FROM issues ORDER BY RANDOM() LIMIT 16);";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				// Build the issue
				Issue i = new Issue("", rs.getString("api_detail_url"), rs.getInt("issue_id"), rs.getString("issue_number"), rs.getString("issue_name"), rs.getString("image_url"));
				issuesResult.add(i);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return issuesResult;
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
	
	

	private List<Issue> getUserFavorites(int user_id){
		
		List<Issue> issues = new ArrayList<>();
		
		// GET all issues associated with the user_id
		String sql = "SELECT issues.* FROM issues INNER JOIN favorites ON favorites.issue_id = issues.issue_id WHERE favorites.user_id = " + String.valueOf(user_id);
		
		try (Connection conn = this.connect();Statement pstmt = conn.createStatement()) {

			ResultSet rs = pstmt.executeQuery(sql);
			
			while(rs.next())
			{
				// Build the issue
				Issue i = new Issue("", rs.getString("api_detail_url"), rs.getInt("issue_id"), rs.getString("issue_number"),
						rs.getString("issue_name"), rs.getString("image_url"), rs.getString("deck"), rs.getString("description"));
				issues.add(i);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		
		return issues;
	}

	private List<Issue> getUserReading(int user_id){
		List<Issue> issues_reading = new ArrayList<>();
		
		String sql = "SELECT issues.* FROM issues INNER JOIN reads ON reads.issue_id = issues.issue_id WHERE reads.read_status = 'reading' AND reads.user_id = " + String.valueOf(user_id);
		
		try (Connection conn = this.connect();Statement pstmt = conn.createStatement()) {

			ResultSet rs = pstmt.executeQuery(sql);
			
			while(rs.next())
			{
				// Build the issue
				Issue i = new Issue("", rs.getString("api_detail_url"), rs.getInt("issue_id"), rs.getString("issue_number"),
						rs.getString("issue_name"), rs.getString("image_url"), rs.getString("deck"), rs.getString("description"));
				issues_reading.add(i);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		
		return issues_reading;
	}
	
	private List<Issue> getUserReaded(int user_id){
		List<Issue> issues_readed = new ArrayList<>();
		
		String sql = "SELECT issues.* FROM issues INNER JOIN reads ON reads.issue_id = issues.issue_id WHERE reads.read_status = 'readed' AND reads.user_id = " + String.valueOf(user_id);
		
		try (Connection conn = this.connect();Statement pstmt = conn.createStatement()) {

			ResultSet rs = pstmt.executeQuery(sql);
			
			while(rs.next())
			{
				// Build the issue
				Issue i = new Issue("", rs.getString("api_detail_url"), rs.getInt("issue_id"), rs.getString("issue_number"),
						rs.getString("issue_name"), rs.getString("image_url"), rs.getString("deck"), rs.getString("description"));
				issues_readed.add(i);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return issues_readed;
	}
	
	public void addNewUserReading(User user, Issue issue){
		//FIRST : CHECK IF TUPLE DOESN'T EXIST
			boolean isTupleAlreadyExists = false;
			int user_id = user.getId();
			int issue_id = issue.getId();
			
			String sql = "SELECT * FROM reads WHERE issue_id = ? AND user_id = ?";
			
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
					System.out.println("Reads already exist for this user"); 
				}
				
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			// THEN ADD TUPLE IF NOT ALREAY EXISTS	
			if(!isTupleAlreadyExists) {
				
				sql = "INSERT INTO reads(issue_id,user_id,read_status) VALUES(?,?,?)";
				try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
					pstmt.setInt(1, issue_id);
					pstmt.setInt(2, user_id);
					pstmt.setString(3, "reading");
					pstmt.executeUpdate();
					
					//Add issue in the model
					userModel.addNewUserReadingIssue(issue);
				}
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
	}
	
	public void addNewUserReaded(User user, Issue issue_readed){
		String sql = "UPDATE reads SET read_status = 'readed' WHERE issue_id = ? AND user_id = ?";
		
		try(Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, issue_readed.getId());
			pstmt.setInt(2, user.getId());
			pstmt.executeUpdate();
			
			//Remove issue from the model
			userModel.removeUserReadingIssue(issue_readed);
			//Add issue in the model
			userModel.addNewUserReadedIssue(issue_readed);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeUserRead(User user, Issue issue){
		String sql = "DELETE FROM reads WHERE issue_id = ? AND user_id = ?";
		int user_id = user.getId();
		int issue_id = issue.getId();
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, issue_id);
			pstmt.setInt(2, user_id);
			pstmt.executeUpdate();
			
			//Remove from model
			userModel.removeUserReadedIssue(issue);
		} 
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}

	//Collections 
	private int getCollectionIdFromName(String cName, int user_id) {
		String sql = "SELECT collection_id FROM collection_names WHERE name = ? and user_id = ?";
		
		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, cName);
			pstmt.setInt(2, user_id);
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next())
				return 0;
			else
				return rs.getInt("collection_id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public void createNewCollection(String cName, User user) {
		String sql = "INSERT INTO collection_names(name,user_id) VALUES(?,?)";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, cName);
			pstmt.setInt(2, user.getId());
			pstmt.executeUpdate();
			
			Collection col = new Collection(cName, new ArrayList<>());
			userModel.addNewUserCollection(col);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void removeCollection(String cName, User user) {
		//get collection id
		int collectionId = getCollectionIdFromName(cName, user.getId());
		
		//delete in collections every occurence of collection_id before remove the collection (FOREIGN KEY CONSTRAINT)
		String sql = "DELETE FROM collections WHERE collection_id = ?";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, collectionId);
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		//THEN DELETE IN collection_names
		sql = "DELETE FROM collection_names WHERE name = ?";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, cName);
			pstmt.executeUpdate();
			
			//Remove from model
			Collection colToRemove = new Collection(cName, new ArrayList<>());
			userModel.removeUserCollection(colToRemove);
			
		} 
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public boolean checkIfCollectionNameExist(String cName, int user_id) {
		String sql = "SELECT * FROM collection_names WHERE name = ? and user_id = ?";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, cName);
			pstmt.setInt(2, user_id);
			ResultSet rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				//tuple doesn't exist yet
				return false;
			}
			else {
				// tuple already exists			
				System.out.println("COLLECTION NAME ALREADY EXIST FOR THIS USER !"); 
				return true;
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkIfIssueInUserCollection(String cName, User user, Issue issue) {
			
			int collection_id = 0;
			collection_id = getCollectionIdFromName(cName, user.getId());
			if(collection_id == 0) {
				return false;
			}

			int user_id = user.getId();
			int issue_id = issue.getId();
			
			String sql = "SELECT * FROM collections WHERE issue_id = ? AND user_id = ? AND collection_id = ?";
			
			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setInt(1, issue_id);
				pstmt.setInt(2, user_id);
				pstmt.setInt(3, collection_id);
				ResultSet rs = pstmt.executeQuery();
				
				if(!rs.next()) {
					//tuple doesn't exist yet
					return false;
				}
				else {
					// tuple already exists			
					return true;
				}
				
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
	}
	public void addNewIssueInUserCollection(String cName, User user, Issue issue) {
		//FIRST : CHECK IF COLLECTION EXIST
		int collection_id = 0;
		collection_id = getCollectionIdFromName(cName, user.getId());
		if(collection_id == 0) {
			//Collection doesnt exist
			if(!cName.equals("All")) {
				createNewCollection(cName,user);
				collection_id = getCollectionIdFromName(cName, user.getId());
			}
		}
		
		int user_id = user.getId();
		int issue_id = issue.getId();
			
		// THEN ADD TUPLE IF NOT ALREAY EXISTS	
		if(!checkIfIssueInUserCollection(cName, user, issue)) {
			addNewIssue(issue);
			String sql = "INSERT INTO collections(issue_id,user_id,collection_id) VALUES(?,?,?)";
			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setInt(1, issue_id);
				pstmt.setInt(2, user_id);
				pstmt.setInt(3, collection_id);
				pstmt.executeUpdate();
				
				//Add issue in the model collection
				userModel.addNewIssueInUserCollection(cName, issue);
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public void removeIssueFromUserCollection(String cName, User user, Issue issue) {
		String sql = "DELETE FROM collections WHERE user_id = ? AND issue_id = ?";
		int user_id = user.getId();
		int issue_id = issue.getId();
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, issue_id);
			pstmt.executeUpdate();
			
			//Remove from model
			userModel.removeIssueInUserCollection(cName, issue);
		} 
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	public List<String> getAllCollectionNamesFromUser(int user_id) {
		String sql = "SELECT DISTINCT(name) FROM collection_names WHERE user_id = ?";
		List<String> collection_names = new ArrayList<>();
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				// Build the collection name
				collection_names.add(new String(rs.getString("name")));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return collection_names;
	}
	
	public List<String> getNotes(User user, int issue_id) {
		//return a list of notes from this user and issue id 
	    List<String> notes = new ArrayList<>();
	    String sql_query = "SELECT note_message, note_date FROM notes WHERE user_id = ? AND issue_id = ?";
	    try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql_query)) {
	        pstmt.setInt(1, user.getId());
	        pstmt.setInt(2, issue_id);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            java.sql.Timestamp timestamp = rs.getTimestamp("note_date");
	            LocalDateTime dateTime = timestamp.toLocalDateTime();
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	            String formattedDate = dateTime.format(formatter);
	            notes.add(formattedDate + " : " + rs.getString("note_message"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return notes;
	}

	
	public void addNotes(User user, int issue_id, String note) {
		//This function add the comment note in the database
	    String sql_query = "INSERT INTO notes (issue_id, user_id, note_message, note_date) VALUES (?, ?, ?, ?)";
	    java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
	    try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql_query)) {
	        pstmt.setInt(1, issue_id);
	        pstmt.setInt(2, user.getId());
	        pstmt.setString(3, note);
	        pstmt.setTimestamp(4, timestamp);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void removeLastNote(User user, int issue_id) {
		//remove the last note with this user and issue id 
	    String select_query = "SELECT note_id FROM notes WHERE user_id = ? and issue_id = ? ORDER BY note_id DESC LIMIT 1";
	    String delete_query = "DELETE FROM notes WHERE note_id = ?";
	    try (Connection conn = this.connect(); PreparedStatement select_stmt = conn.prepareStatement(select_query);
	    PreparedStatement delete_stmt = conn.prepareStatement(delete_query)) {
	        select_stmt.setInt(1, user.getId());
	        select_stmt.setInt(2, issue_id);
	        ResultSet rs = select_stmt.executeQuery();
	        if (rs.next()) {
	            int note_id = rs.getInt("note_id");
	            delete_stmt.setInt(1, note_id);
	            delete_stmt.executeUpdate();
	            System.out.println("Note with id " + note_id + " was deleted");
	        } else {
	            System.out.println("No note found with user_id: " + user.getId() + " and issue_id: " + issue_id);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	
	private Collection getAllIssuesFromUserCollection(int user_id, String cName){
		List<Issue> issues = new ArrayList<>();
		
		Collection collection = new Collection(cName,issues);
		
		int collection_id = getCollectionIdFromName(cName, user_id);
		
		String sql = "SELECT issues.* FROM issues INNER JOIN collections ON collections.issue_id = issues.issue_id WHERE collections.collection_id = ?  AND collections.user_id = ?";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, collection_id);
			pstmt.setInt(2, user_id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				// Build the issue
				Issue i = new Issue("", rs.getString("api_detail_url"), rs.getInt("issue_id"), rs.getString("issue_number"), rs.getString("issue_name"), rs.getString("image_url"));
				issues.add(i);
			}
			
			collection.setIssues(issues);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		return collection;
	}
	
	private List<Collection> getAllUserCollection(int user_id){

		List<Collection> user_collections = new ArrayList<>();
		
		List<String> collection_names = getAllCollectionNamesFromUser(user_id);
		
		for(String colName : collection_names) {
			user_collections.add(getAllIssuesFromUserCollection(user_id, colName));
		}
		
		return user_collections;
	}
	
}
