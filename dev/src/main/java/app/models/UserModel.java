package app.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import app.entities.Collection;
import app.entities.Issue;
import app.entities.User;

public class UserModel {

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private User user = new User(0,"Invité","","");
	private boolean isAuthenticated = false;
	private List<Issue> userFavoriteIssues = new ArrayList<>();
	private List<Issue> userReadingIssues = new ArrayList<>();
	private List<Issue> userReadedIssues = new ArrayList<>();
	private List<Collection> userCollections = new ArrayList<>();
	private List<Issue> userRecommandedIssues = new ArrayList<>();
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	public void setUser(boolean isAuth, User u, List<Issue> userReco, List<Issue> userFavIss, List<Issue> userReadingIss, List<Issue> userReadedIss, List<Collection> userCols) {
		User previousUser = user;
		user = u;
		isAuthenticated = isAuth;
		userRecommandedIssues = userReco;
		userFavoriteIssues = userFavIss;
		userReadingIssues = userReadingIss;
		userReadedIssues = userReadedIss;
		userCollections = userCols;
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
		
		this.pcs.firePropertyChange("readChange", oldUserReadingIssues, "add");
	}
	
	public void removeUserReadingIssue(Issue oldReadingIssue) {
		List<Issue> oldUserReadingIssues = userReadingIssues;
		
		this.userReadingIssues.removeIf(n -> n.getId() == oldReadingIssue.getId());
	}
	
	public List<Issue> getUserReadingIssues(){
		return userReadingIssues;
	}
	
	public void addNewUserReadedIssue(Issue newReadedIssue) {
		List<Issue> oldUserReadedIssues = userReadedIssues;
		
		this.userReadedIssues.add(newReadedIssue);
		
		this.pcs.firePropertyChange("readChange", oldUserReadedIssues, "add");
	}
	
	public void removeUserReadedIssue(Issue oldReadedIssue) {
		List<Issue> oldUserReadedIssues = userReadedIssues;
		
		this.userReadedIssues.removeIf(n -> n.getId() == oldReadedIssue.getId());
		
		this.pcs.firePropertyChange("readChange", oldUserReadedIssues, "remove");
	}
	
	public List<Issue> getUserReadedIssues(){
		return userReadedIssues;
	}
	
	public void addNewUserCollection(Collection col) {
		List<Collection> oldUserCollection = userCollections;
		
		this.userCollections.add(col);
		
		this.pcs.firePropertyChange("collectionListChange",oldUserCollection, "add");
		this.pcs.firePropertyChange("collectionChange",oldUserCollection,"add");
		
	}
	
	public void removeUserCollection(Collection col) {
		List<Collection> oldUserCollection = userCollections;
		
		this.userCollections.removeIf(n -> n.getName().equals(col.getName()));
		
		this.pcs.firePropertyChange("collectionListChange", oldUserCollection, "remove");
		this.pcs.firePropertyChange("collectionChange",oldUserCollection,"remove");
	}
	
	public List<Collection> getUserCollections(){
		return userCollections;
	}
	
	public void addNewIssueInUserCollection(String cName, Issue iss) {
		List<Collection> oldUserCollection = userCollections;
		
		for(int i=0; i<userCollections.size();i++) {			
			if(userCollections.get(i).getName().equals(cName)) {
				System.out.println("UM : Adding new issue in the collection : " + cName);
				userCollections.get(i).addIssuesInCollection(iss);
			}
		}
		
		this.pcs.firePropertyChange("collectionChange",oldUserCollection, "add");
	}
	
	public void removeIssueInUserCollection(String cName, Issue iss) {
		List<Collection> oldUserCollection = userCollections;
		
		for(int i=0; i<userCollections.size();i++) {
			if(userCollections.get(i).getName().equals(cName)) {
				System.out.println("Removing issue in the collection : " + cName);
				userCollections.get(i).removeIssuesInCollection(iss);
			}
		}
		
		this.pcs.firePropertyChange("collectionChange",oldUserCollection, "remove");
	}
	
	public List<Issue> getAllCollectionnedIssues() {
		List<Issue> resultIssueList = new ArrayList<>();
		for(Collection collection: userCollections) {
			resultIssueList.addAll(collection.getIssues());
		}
		return resultIssueList;
	}
	
	public void setRecommandedIssueList(List<Issue> recoIssues) {
		List<Issue> oldRecoIssues = userRecommandedIssues;
		
		userRecommandedIssues = recoIssues;
		
		this.pcs.firePropertyChange("recommendationChange", oldRecoIssues, "new");
	}
	
	public List<Issue> getRecommandedIssueList(){
		return userRecommandedIssues;
	}
}
