package app.entities;

import lombok.Data;

@Data
public class User {

	private int id;
	private String username;
	private String first_name; 
	private String last_name;

	public User(int id, String username, String first_name, String last_name) {
		this.id = id;
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
	}	
	
}
 