package app.entities;

import java.util.List;
import lombok.Data;

@Data
public class User {

	private boolean isAuthentified;
	private String username;
	private String first_name; 
	private String last_name;
	
	public User(boolean isAuthentified, String username, String first_name, String last_name) {
		super();
		this.isAuthentified = isAuthentified;
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	//private List<Comics> bookmark; ??
	//private List<Comics> reads; ??
	//private List<Comics> notes; ??
	
	
}
