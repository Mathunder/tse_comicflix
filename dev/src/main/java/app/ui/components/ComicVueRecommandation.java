package app.ui.components;

import java.beans.PropertyChangeEvent;

import app.entities.Issue;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;

public class ComicVueRecommandation extends ComicVue {

	public ComicVueRecommandation(UserModel um, ComicVineService cvS, DatabaseService dbS) {
		super(um, cvS, dbS);
	}

	@Override
	public void showResult() {
		this.removeAll();
		
		//Show user recommended issues
		for(int i=0;i<userModel.getRecommandedIssueList().size();i++) {
			ComicCoverPanel comicCover = new ComicCoverPanel(userModel.getRecommandedIssueList().get(i), databaseService, userModel.getUser());
			this.ComicCoverPanels.add(comicCover);
			this.add(comicCover);
		}
		
		refreshPanel();
	}
	
	//Refresh state of buttons on the coverPanel
	public void updateButtonStates(int itemRefreshCode) {
			
		for(int i=0; i<userModel.getRecommandedIssueList().size();i++) {	
			if(itemRefreshCode == 1 || itemRefreshCode == 0) {
				boolean isFavorite = false;
				// Find if the issue displayed is favorite for the User
				for(Issue favorite : userModel.getUserFavoriteIssues()) {
					if(favorite.getId() == userModel.getRecommandedIssueList().get(i).getId()) {
						isFavorite=true;
						break;
					}
				}
				ComicCoverPanels.get(i).refreshStateButtons(isFavorite);
			}
			
			if(itemRefreshCode == 2 || itemRefreshCode == 0) {
				int readState = 0;
				// Find if the issue displayed is reading by the User
				for(Issue reading : userModel.getUserReadingIssues()) {
					if(reading.getId() == userModel.getRecommandedIssueList().get(i).getId()) {
						readState = 1;
						break;
					}
				}
				
				// Find if the issue displayed is readed by the User
				for(Issue readed : userModel.getUserReadedIssues()) {
					if(readed.getId() == userModel.getRecommandedIssueList().get(i).getId()) {
						readState = 2;
						break;
					}
				}
				ComicCoverPanels.get(i).refreshStateButtons(readState);
			}
			if(itemRefreshCode == 3 || itemRefreshCode == 0) {
				String selectedItem = "All";
				Boolean findCorrespondance = false;
				for(int j=0; j<userModel.getUserCollections().size();j++) {
					if (!findCorrespondance) {
						for(Issue issue_col: userModel.getUserCollections().get(j).getIssues()) {
							if(issue_col.getId() == userModel.getRecommandedIssueList().get(i).getId()) {
								selectedItem = userModel.getUserCollections().get(j).getName();
								findCorrespondance = true;
								break;
							}
						}
					}
					else 
						break;
				}

				ComicCoverPanels.get(i).refreshStateComboBox(selectedItem);
			}
			if(itemRefreshCode == 4 || itemRefreshCode == 0) {
				ComicCoverPanels.get(i).updateComboBoxList();
				updateButtonStates(3);
			}
		}
		
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if(evt.getPropertyName() == "userChange") {
			showResult();
			updateButtonStates(0);
		}
		else if(evt.getPropertyName() == "favoriteChange"){
			if(evt.getNewValue() == "add")
				System.out.println("Add one new favorite (VueRecommandation)");
			else if(evt.getNewValue() == "remove")
				System.out.println("Remove one favorite (VueRecommandation)");
			
			updateButtonStates(1);
		}
		else if(evt.getPropertyName() == "readChange") {
			if(evt.getNewValue() == "add")
				System.out.println("Add new read (change state)");
			else if(evt.getNewValue() == "remove")
				System.out.println("remove read");
			updateButtonStates(2);
		}
		else if(evt.getPropertyName() == "collectionChange") {
			if(evt.getNewValue() == "add")
				System.out.println("Collection change [add] (VueRecommandation)");
			else if(evt.getNewValue() == "remove")
				System.out.println("Collection change [remove] (VueRecommandation)");
			updateButtonStates(3);
		}
		else if(evt.getPropertyName() == "collectionListChange") {
			updateButtonStates(4);
		}
	}

}