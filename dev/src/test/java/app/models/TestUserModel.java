package app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.entities.Issue;
import app.entities.User;
import junit.framework.TestCase;

public class TestUserModel extends TestCase {


	public void testAddNewUserFavoriteIssue() {
		
		
		UserModel test = new UserModel();
		
		List<Issue> userFavoriteIssues = new ArrayList<>();
		User u = new User(0, "a7x", "Hugo", "Keenan"); //user we want to add
		boolean isAuthenticated = false;
		List<Issue> userReadingIss = new ArrayList<>(); 
		List<Issue> userReadedIss = new ArrayList<>();
		
		test.setUser(isAuthenticated, u, userFavoriteIssues, userReadingIss, userReadedIss);
		
		
		String aliases = null;
		String api_detail_url = "https://comicvine.gamespot.com/api/issue/4000-934902/"; 
		int id = 934902; 
		String issue_number = "2"; 
		String name = "Wonderland"; 
		String image_url = "https://comicvine.gamespot.com/a/uploads/square_avatar/11116/111168846/8561815-spider02-01.jpg";;
		
		Issue issue = new Issue(aliases,api_detail_url,id,issue_number,name,image_url);
		
		//add the issue to the list of favorite
		test.addNewUserFavoriteIssue(issue);
		
		
			//perform the test
		//is the issue of the array equals to 1?
		assertEquals(test.getUserFavoriteIssues().size(), 1);
		
		//is the issue object equals to what was added inside the list
		assertEquals(Objects.equals(test.getUserFavoriteIssues().get(0), issue),true);
		
		
		
		
		
		
		
	}

	public void testRemoveUserFavoriteIssue() {
		UserModel test = new UserModel();
		
		List<Issue> userFavoriteIssues = new ArrayList<>();
		User u = new User(0, "a7x", "Hugo", "Keenan"); //user we want to add
		boolean isAuthenticated = false;
		List<Issue> userReadingIss = new ArrayList<>(); 
		List<Issue> userReadedIss = new ArrayList<>();
		
		test.setUser(isAuthenticated, u, userFavoriteIssues, userReadingIss, userReadedIss);
		
		
		String aliases = null;
		String api_detail_url = "https://comicvine.gamespot.com/api/issue/4000-934902/"; 
		int id = 934902; 
		String issue_number = "2"; 
		String name = "Wonderland"; 
		String image_url = "https://comicvine.gamespot.com/a/uploads/square_avatar/11116/111168846/8561815-spider02-01.jpg";;
		
		Issue issue = new Issue(aliases,api_detail_url,id,issue_number,name,image_url);
		
		//add the issue to the list of favorite
		test.removeUserFavoriteIssue(issue);
		
		
			//perform the test
		//is the issue of the array equals to 1?
		assertEquals(test.getUserFavoriteIssues().size(), 0);
		
		//is the issue object equals to what was added inside the list
		assertEquals(Objects.equals(test.getUserFavoriteIssues().isEmpty(), true),true);
	}

}
