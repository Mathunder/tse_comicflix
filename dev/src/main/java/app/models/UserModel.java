package app.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import app.entities.ResultsAPI;
import app.entities.User;

public class UserModel {

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private User user = new User(0,"Invité","","");
	private boolean isAuthenticated = false;
	private List<ResultsAPI> userFavoriteIssues = new ArrayList<>();
	private List<ResultsAPI> userReadingIssues = new ArrayList<>();
	private List<ResultsAPI> userReadedIssues = new ArrayList<>();
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	public void setUser(boolean isAuth, User u, List<ResultsAPI> userFavIss, List<ResultsAPI> userReadingIss, List<ResultsAPI> userReadedIss) {
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
	
	public void addNewUserFavoriteIssue(ResultsAPI newFavIssue) {
		List<ResultsAPI> oldUserFavoriteIssues = userFavoriteIssues;
		
		this.userFavoriteIssues.add(newFavIssue);
	
		this.pcs.firePropertyChange("favoriteChange", oldUserFavoriteIssues, "add");
	}
	
	public void removeUserFavoriteIssue(ResultsAPI oldFavIssue) {
		List<ResultsAPI> oldUserFavoriteIssues = userFavoriteIssues;

		this.userFavoriteIssues.removeIf(n -> n.getId() == oldFavIssue.getId());

		this.pcs.firePropertyChange("favoriteChange", oldUserFavoriteIssues, "remove");
	}
	
	public List<ResultsAPI> getUserFavoriteIssues() {
		return userFavoriteIssues;
	}
	
	public void addNewUserReadingIssue(ResultsAPI newReadingIssue) {
		List<ResultsAPI> oldUserReadingIssues = userReadingIssues;
		
		this.userReadingIssues.add(newReadingIssue);
		
		this.pcs.firePropertyChange("readChange", oldUserReadingIssues, "add");
	}
	
	public void removeUserReadingIssue(ResultsAPI oldReadingIssue) {
		List<ResultsAPI> oldUserReadingIssues = userReadingIssues;
		
		this.userReadingIssues.removeIf(n -> n.getId() == oldReadingIssue.getId());
	}
	
	public List<ResultsAPI> getUserReadingIssues(){
		return userReadingIssues;
	}
	
	public void addNewUserReadedIssue(ResultsAPI newReadedIssue) {
		List<ResultsAPI> oldUserReadedIssues = userReadedIssues;
		
		this.userReadedIssues.add(newReadedIssue);
		
		this.pcs.firePropertyChange("readChange", oldUserReadedIssues, "add");
	}
	
	public void removeUserReadedIssue(ResultsAPI oldReadedIssue) {
		List<ResultsAPI> oldUserReadedIssues = userReadedIssues;
		
		this.userReadedIssues.removeIf(n -> n.getId() == oldReadedIssue.getId());
		
		this.pcs.firePropertyChange("readChange", oldUserReadedIssues, "remove");
	}
	
	public List<ResultsAPI> getUserReadedIssues(){
		return userReadedIssues;
	}
}
