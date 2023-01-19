package app.ui.components;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

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
		removeComics();
		
		//Show user recommended issues
		for(int i=0;i<userModel.getRecommandedIssueList().size();i++) {
			Issue recommanded_issue = userModel.getRecommandedIssueList().get(i);
			ComicCoverPanel comicCover = new ComicCoverPanel(recommanded_issue, databaseService, userModel.getUser());
			// Adding the mouse listener to enable the click on an issue 
			comicCover.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					ComicsInfosPanel infos = new ComicsInfosPanel(recommanded_issue.getApi_detail_url(), databaseService, userModel);
					infos.fetchInformations();
					infos.fetchPreviousNextInformations();
					infos.createInfosPanel();
					JScrollPane scrollPaneComicsInfos = new JScrollPane(infos);
					scrollPaneComicsInfos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollPaneComicsInfos.getVerticalScrollBar().setUnitIncrement(14);
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
					f.add(scrollPaneComicsInfos);
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
			System.out.println("USER CHANGE (VueRecommandation)");
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
