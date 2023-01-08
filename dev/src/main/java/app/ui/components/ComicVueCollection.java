package app.ui.components;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import app.dto.ResultDto;
import app.entities.Collection;
import app.entities.Issue;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;

public class ComicVueCollection extends ComicVue {
	
	public ComicVueCollection(UserModel um, ComicVineService cvS, DatabaseService dbS) {
		super(um, cvS, dbS);
	}

	@Override
	public void showResult() {
		removeComics();
		
		//Show userCollectionIssues
		List<Issue> allCollectionnedIssues = userModel.getAllCollectionnedIssues();
		
		for(int i=0;i<allCollectionnedIssues.size();i++) {
			ComicCoverPanel comicCover = new ComicCoverPanel(allCollectionnedIssues.get(i), databaseService, userModel.getUser());
			ComicCoverPanels.add(comicCover);
			add(comicCover);
			
		}
		
		refreshPanel();
	}
	
	public void updateButtonStates(int itemRefreshCode) {
		List<Issue> allCollectionnedIssues = userModel.getAllCollectionnedIssues();
		
		for(int i=0;i<allCollectionnedIssues.size();i++) {
			
			//FAVORITE UPDATE
			if(itemRefreshCode == 1 || itemRefreshCode == 0) {
				boolean isFavorite = false;
				// Find if the issue displayed is favorite for the User
				for(Issue favorite : userModel.getUserFavoriteIssues()) {
					if(favorite.getId() == allCollectionnedIssues.get(i).getId()) {
						isFavorite=true;
						break;
					}
				}
				ComicCoverPanels.get(i).refreshStateButtons(isFavorite);
			}
			
			//READING/READED UPDATE
			if(itemRefreshCode == 2 || itemRefreshCode == 0) {
				int readState = 0;
				// Find if the issue displayed is reading by the User
				for(Issue reading : userModel.getUserReadingIssues()) {
					if(reading.getId() == allCollectionnedIssues.get(i).getId()) {
						readState = 1;
						break;
					}
				}
				
				// Find if the issue displayed is readed by the User
				for(Issue readed : userModel.getUserReadedIssues()) {
					if(readed.getId() == allCollectionnedIssues.get(i).getId()) {
						readState = 2;
						break;
					}
				}
				ComicCoverPanels.get(i).refreshStateButtons(readState);
				
			}
			//COLLECTION UPDATE
			if(itemRefreshCode == 3 || itemRefreshCode == 0) {
				String selectedItem = "All";
				Boolean findCorrespondance = false;
				for(int j=0; j<userModel.getUserCollections().size();j++) {
					if (!findCorrespondance) {
						for(Issue issue_col: userModel.getUserCollections().get(j).getIssues()) {
							if(issue_col.getId() == allCollectionnedIssues.get(i).getId()) {
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
				System.out.println("Add one new favorite (VueFavorite)");
			else if(evt.getNewValue() == "remove")
				System.out.println("Remove one favorite (VueFavorite)");
			
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
				System.out.println("Collection change [add] (VueFavorite)");
			else if(evt.getNewValue() == "remove")
				System.out.println("Collection change [remove] (VueFavorite)");
			showResult();
			updateButtonStates(0);
		}
		else if(evt.getPropertyName() == "collectionListChange") {
			updateButtonStates(4);
		}
	}

}
