package app.ui.components;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import app.entities.Issue;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;

@SuppressWarnings("serial")
public class ComicVueFavorite extends ComicVue{
	
	public ComicVueFavorite(UserModel um, ComicVineService cvS,DatabaseService dbS){
		super(um, cvS, dbS);
		
	}
	
	@Override
	public void showResult() {
		removeComics();
		
		// Show userFavoriteIssues
		for(int i=0; i<userModel.getUserFavoriteIssues().size();i++) {
			ComicCoverPanel comicCover = new ComicCoverPanel(userModel.getUserFavoriteIssues().get(i), databaseService, userModel.getUser());
			this.ComicCoverPanels.add(comicCover);
			this.add(comicCover);
		}
				
		refreshPanel();
	}
	
	//Refresh state of buttons on the coverPanel
	public void updateButtonStates(int itemRefreshCode) {
		
		for(int i=0; i<userModel.getUserFavoriteIssues().size();i++) {	
			if(itemRefreshCode == 1 || itemRefreshCode == 0) {
				boolean isFavorite = false;
				// Find if the issue displayed is favorite for the User
				for(Issue favorite : userModel.getUserFavoriteIssues()) {
					if(favorite.getId() == userModel.getUserFavoriteIssues().get(i).getId()) {
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
					if(reading.getId() == userModel.getUserFavoriteIssues().get(i).getId()) {
						readState = 1;
						break;
					}
				}
				
				// Find if the issue displayed is readed by the User
				for(Issue readed : userModel.getUserReadedIssues()) {
					if(readed.getId() == userModel.getUserFavoriteIssues().get(i).getId()) {
						readState = 2;
						break;
					}
				}
				ComicCoverPanels.get(i).refreshStateButtons(readState);
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
				System.out.println("Add one new favorite (VueFavorite)");
			else if(evt.getNewValue() == "remove")
				System.out.println("Remove one favorite (VueFavorite)");
			
			showResult();
			updateButtonStates(0);
		}
		else if(evt.getPropertyName() == "readChange") {
			if(evt.getNewValue() == "add")
				System.out.println("Add new read (change state)");
			else if(evt.getNewValue() == "remove")
				System.out.println("remove read");
			updateButtonStates(2);
		}
	}

}
