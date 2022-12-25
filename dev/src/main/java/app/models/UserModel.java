package app.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import app.entities.Issue;
import app.entities.User;

public class UserModel {

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private User user = new User(0,"Invité","","");
	private boolean isAuthenticated = false;
	private List<Issue> userFavoriteIssues = new ArrayList<>();
	private List<Issue> userReadingIssues = new ArrayList<>();
	private List<Issue> userReadedIssues = new ArrayList<>();
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	public void setUser(boolean isAuth, User u, List<Issue> userFavIss, List<Issue> userReadingIss, List<Issue> userReadedIss) {
		User previousUser = user;
		user = u;
		isAuthenticated = isAuth;
		userFavoriteIssues = userFavIss;
		userReadingIssues = userReadingIss;
		userReadedIssues = userReadedIss;
		
		this.pcs.firePropertyChange("userChange", previousUser, user);
	}
	
	public User getUser() {
		return user;
	}
	
	public boolean getIsAuthenticated() {
		return isAuthenticated;
	}
	
	public void addNewUserFavoriteIssue(Issue newFavIssue) {
		List<Issue> oldUserFavoriteIssues = userFavoriteIssues;
		
		this.userFavoriteIssues.add(newFavIssue);
	
		this.pcs.firePropertyChange("favoriteChange", oldUserFavoriteIssues, "add");
	}
	
	public void removeUserFavoriteIssue(Issue oldFavIssue) {
		List<Issue> oldUserFavoriteIssues = userFavoriteIssues;

		this.userFavoriteIssues.removeIf(n -> n.getId() == oldFavIssue.getId());

		this.pcs.firePropertyChange("favoriteChange", oldUserFavoriteIssues, "remove");
	}
	
	public List<Issue> getUserFavoriteIssues() {
		return userFavoriteIssues;
	}
	
	public void addNewUserReadingIssue(Issue newReadingIssue) {
		List<Issue> oldUserReadingIssues = userReadingIssues;
		
		this.userReadingIssues.add(newReadingIssue);
		
		this.pcs.firePropertyChange("readingChange", oldUserReadingIssues, userReadingIssues);
	}
	
	public void removeUserReadingIssue(Issue oldReadingIssue) {
		List<Issue> oldUserReadingIssues = userReadingIssues;
		
		this.userReadingIssues.removeIf(n -> n.getId() == oldReadingIssue.getId());
		
		this.pcs.firePropertyChange("readingChange", oldUserReadingIssues, userReadingIssues);
	}
	
	public void addNewUserReadedIssue(Issue newReadedIssue) {
		List<Issue> oldUserReadedIssues = userReadedIssues;
		
		this.userReadedIssues.add(newReadedIssue);
		
		this.pcs.firePropertyChange("readedChange", oldUserReadedIssues, userReadedIssues);
	}
	
	public void removeUserReadedIssue(Issue oldReadedIssue) {
		List<Issue> oldUserReadedIssues = userReadedIssues;
		
		this.userReadedIssues.removeIf(n -> n.getId() == oldReadedIssue.getId());
		
		this.pcs.firePropertyChange("readedChange", oldUserReadedIssues, userReadedIssues);
	}
}
