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
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	public void setUser(boolean isAuth, User u, List<ResultsAPI> userFavIss) {
		User previousUser = user;
		user = u;
		isAuthenticated = isAuth;
		userFavoriteIssues = userFavIss;
		
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
}
