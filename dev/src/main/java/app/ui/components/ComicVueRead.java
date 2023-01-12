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

import app.entities.Issue;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;

public class ComicVueRead extends ComicVue{
	
	public ComicVueRead(UserModel um, ComicVineService cvS,DatabaseService dbS){
		super(um, cvS, dbS);
	}
	
	@Override
	public void showResult() {
		removeComics();
		
		//Show userReadingdIssues
		for(int i=0;i<userModel.getUserReadingIssues().size();i++) {
			Issue read_issue = userModel.getUserReadingIssues().get(i);
			ComicCoverPanel comicCover = new ComicCoverPanel(read_issue, databaseService, userModel.getUser());
			// Adding the mouse listener to enable the click on an issue 
			comicCover.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					ComicsInfosPanel infos = new ComicsInfosPanel(read_issue.getApi_detail_url());
					infos.fetchInformations();
					infos.createInfosPanel();
					// Creating the new frame that will display the informations the user wants.
					String frame_name = "";
					try {
						frame_name = infos.getResult().getVolume().getName() + ' ' + '(' + infos.getResult().getIssue_number() + ')';
					} catch (NullPointerException e1) {}
					
					JFrame f = new JFrame(frame_name);
					try {

						URL url_image = new URL(infos.getResult().getImage().getIcon_url());
						Image icon = Toolkit.getDefaultToolkit().getImage(url_image);
						f.setIconImage(icon);
					} catch (MalformedURLException e1) {}
					f.setSize(1050, 600);
					f.add(infos);
					f.setResizable(false);
					f.setVisible(true);

				}
			});

			this.ComicCoverPanels.add(comicCover);
			this.add(comicCover);
		}
		
		//Show userReadedIssues
		for(int i=0;i<userModel.getUserReadedIssues().size();i++) {
			Issue readed_issue = userModel.getUserReadedIssues().get(i);
			ComicCoverPanel comicCover = new ComicCoverPanel(readed_issue, databaseService, userModel.getUser());
			comicCover.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					ComicsInfosPanel infos = new ComicsInfosPanel(readed_issue.getApi_detail_url());
					infos.fetchInformations();
					infos.createInfosPanel();
					// Creating the new frame that will display the informations the user wants.
					String frame_name = "";
					try {
						frame_name = infos.getResult().getVolume().getName() + ' ' + '(' + infos.getResult().getIssue_number() + ')';
					} catch (NullPointerException e1) {}
					
					JFrame f = new JFrame(frame_name);
					try {

						URL url_image = new URL(infos.getResult().getImage().getIcon_url());
						Image icon = Toolkit.getDefaultToolkit().getImage(url_image);
						f.setIconImage(icon);
					} catch (MalformedURLException e1) {}
					f.setSize(1050, 600);
					f.add(infos);
					f.setResizable(false);
					f.setVisible(true);

				}
			});
			this.ComicCoverPanels.add(comicCover);
			this.add(comicCover);
		}
			
		refreshPanel();
	}
	
	//Refresh state of buttons on the coverPanel
	public void updateButtonStates(int itemRefreshCode) {
		int i=0;
		//For all user reading issues
		for(i=0; i<userModel.getUserReadingIssues().size();i++) {	
			if(itemRefreshCode == 1 || itemRefreshCode == 0) {
				boolean isFavorite = false;
				// Find if the issue displayed is favorite for the User
				for(Issue favorite : userModel.getUserFavoriteIssues()) {
					if(favorite.getId() == userModel.getUserReadingIssues().get(i).getId()) {
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
					if(reading.getId() == userModel.getUserReadingIssues().get(i).getId()) {
						readState = 1;
						break;
					}
				}
				
				// Find if the issue displayed is readed by the User
				for(Issue readed : userModel.getUserReadedIssues()) {
					if(readed.getId() == userModel.getUserReadingIssues().get(i).getId()) {
						readState = 2;
						break;
					}
				}
				
				ComicCoverPanels.get(i).refreshStateButtons(readState);
			}
			if(itemRefreshCode == 3 || itemRefreshCode == 0) {
				String selectedItem = "All";
				Boolean findCorrespondance = false;
				for(int k=0; k<userModel.getUserCollections().size();k++) {
					if (!findCorrespondance) {
						for(Issue issue_col: userModel.getUserCollections().get(k).getIssues()) {
							if(issue_col.getId() == userModel.getUserReadingIssues().get(i).getId()) {
								selectedItem = userModel.getUserCollections().get(k).getName();
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
		
		for(int j=0; j<userModel.getUserReadedIssues().size();j++) {	
			if(itemRefreshCode == 1 || itemRefreshCode == 0) {
				boolean isFavorite = false;
				// Find if the issue displayed is favorite for the User
				for(Issue favorite : userModel.getUserFavoriteIssues()) {
					if(favorite.getId() == userModel.getUserReadedIssues().get(j).getId()) {
						isFavorite=true;
						break;
					}
				}
				ComicCoverPanels.get(j + i).refreshStateButtons(isFavorite);
			}
			
			if(itemRefreshCode == 2 || itemRefreshCode == 0) {
				int readState = 0;
				
				// Find if the issue displayed is readed by the User
				for(Issue reading : userModel.getUserReadingIssues()) {
					if(reading.getId() == userModel.getUserReadedIssues().get(j).getId()) {
						readState = 1;
						break;
					}
				}
				
				// Find if the issue displayed is readed by the User
				for(Issue readed : userModel.getUserReadedIssues()) {
					if(readed.getId() == userModel.getUserReadedIssues().get(j).getId()) {
						readState = 2;
						break;
					}
				}
				ComicCoverPanels.get(j+i).refreshStateButtons(readState);
			}
			if(itemRefreshCode == 3 || itemRefreshCode == 0) {
				String selectedItem = "All";
				Boolean findCorrespondance = false;
				for(int l=0; l<userModel.getUserCollections().size();l++) {
					if (!findCorrespondance) {
						for(Issue issue_col: userModel.getUserCollections().get(l).getIssues()) {
							if(issue_col.getId() == userModel.getUserReadedIssues().get(j).getId()) {
								selectedItem = userModel.getUserCollections().get(l).getName();
								findCorrespondance = true;
								break;
							}
						}
					}
					else 
						break;
				}

				ComicCoverPanels.get(i+j).refreshStateComboBox(selectedItem);
			}
			if(itemRefreshCode == 4 || itemRefreshCode == 0) {
				ComicCoverPanels.get(i+j).updateComboBoxList();
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
				System.out.println("Add one new favorite (VueRead)");
			else if(evt.getNewValue() == "remove")
				System.out.println("Remove one favorite (VueRead)");
			
			updateButtonStates(1);
		}
		else if(evt.getPropertyName() == "readChange") {
			if(evt.getNewValue() == "add")
				System.out.println("Add new read (change state)");
			else if(evt.getNewValue() == "remove")
				System.out.println("remove read");
			
			showResult();
			updateButtonStates(0);
		}
		else if(evt.getPropertyName() == "collectionChange") {
			if(evt.getNewValue() == "add")
				System.out.println("Collection change [add] (VueRead)");
			else if(evt.getNewValue() == "remove")
				System.out.println("Collection change [remove] (VueRead)");
			updateButtonStates(3);
		}
		else if(evt.getPropertyName() == "collectionListChange") {
			updateButtonStates(4);
		}
	}
}
