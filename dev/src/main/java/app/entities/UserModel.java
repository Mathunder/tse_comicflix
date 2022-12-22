package app.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UserModel {

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private User user = new User(0,"Invité","","");
	private boolean isAuthenticated = false;
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	public void setUser(boolean isAuth, User u) {
		User previousUser = user;
		user = u;
		isAuthenticated = isAuth;
		
		this.pcs.firePropertyChange("userChange", previousUser, user);
	}
	
	public User getUser() {
		return user;
	}
	
	public boolean getIsAuthenticated() {
		return isAuthenticated;
	}
}
