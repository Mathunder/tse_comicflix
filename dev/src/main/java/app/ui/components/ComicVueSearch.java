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

import app.entities.ResultsAPI;
import app.helpers.ComicVineSearchStatus;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;

public class ComicVueSearch extends ComicVue{

	private List<ResultsAPI> issues;
	
	public ComicVueSearch(UserModel um, ComicVineService cvS,DatabaseService dbS){
		super(um, cvS, dbS);
		this.issues = new ArrayList<>();
	}
	
	@Override
	public void showResult() {
		removeComics();
		
		if(issues.size() != 0) {	
			// Show ResultAPIIssues
			for(int i=0;i<issues.size();i++) {				
				ComicCoverPanel comicCover = new ComicCoverPanel(issues.get(i), databaseService, userModel.getUser());
				
				// Adding the mouse listener to enable the click on a search result
				comicCover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						
						ComicsInfosPanel infos = new ComicsInfosPanel(comicCover);
						infos.fetchInformations();
						infos.createInfosPanel();
						
						//JScrollPane scrollPaneComicsInfos = new JScrollPane(infos);
						
//						scrollPaneComicsInfos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//						scrollPaneComicsInfos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//						scrollPaneComicsInfos.getVerticalScrollBar().setUnitIncrement(14);
//						scrollPaneComicsInfos.getHorizontalScrollBar().setUnitIncrement(14);
		
						// Creating the new frame that will display the informations the user wants.
						JFrame f = new JFrame(infos.getInfosResult().getResults().getVolume().getName() + ' ' + '(' + infos.getInfosResult().getResults().getIssue_number() + ')');
						try {
							
							URL url_image = new URL(comicCover.getResultsApi().getImage().getIcon_url());
							Image icon = Toolkit.getDefaultToolkit().getImage(url_image);
							f.setIconImage(icon);
						} catch (MalformedURLException e1) {}
						f.setSize(1050,600);
						f.add(infos);
						f.setResizable(false);
						f.setVisible(true);
						
					}
				});
				
				this.ComicCoverPanels.add(comicCover);
				this.add(comicCover);
				
			}
		}
		else {
			System.out.println("IssuesSearch null");
		}
		
		refreshPanel();
	}
	
	//Refresh state of buttons on the coverPanel
	public void updateButtonStates(int itemRefreshCode) { // if 0 : RefreshAllButtons, if 1 : refreshOnlyFavorites, if 2 : refreshOnlyReads
		
		for(int i=0; i<issues.size();i++) {	
			if(itemRefreshCode == 1 || itemRefreshCode == 0) {
				boolean isFavorite = false;
				// Find if the issue displayed is favorite for the User
				for(ResultsAPI favorite : userModel.getUserFavoriteIssues()) {
					if(favorite.getId() == issues.get(i).getId()) {
						isFavorite=true;
						break;
					}
				}
				ComicCoverPanels.get(i).refreshStateButtons(isFavorite);
			}
			
			if(itemRefreshCode == 2 || itemRefreshCode == 0) {
				int readState = 0;
				// Find if the issue displayed is reading by the User
				for(ResultsAPI reading : userModel.getUserReadingIssues()) {
					if(reading.getId() == issues.get(i).getId()) {
						readState = 1;
						break;
					}
				}
				
				// Find if the issue displayed is readed by the User
				for(ResultsAPI readed : userModel.getUserReadedIssues()) {
					if(readed.getId() == issues.get(i).getId()) {
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
		
		if(evt.getPropertyName() == "searchStatus") //From Controller/Model ComicVineService
		{
			if(evt.getNewValue() == ComicVineSearchStatus.FETCHING) {
				System.out.println("Loading");
			}
			else if(evt.getNewValue() == ComicVineSearchStatus.DONE) {
				System.out.println("Loaded");
				issues = comicsVineService.getSearchResult().getResults();
				showResult();
				if(userModel.getIsAuthenticated())
					updateButtonStates(0);
			}
		}
		else if(evt.getPropertyName() == "userChange") {
			showResult();
			if(userModel.getIsAuthenticated())
				updateButtonStates(0);
		}
		else if(evt.getPropertyName() == "favoriteChange") {
			if(evt.getNewValue() == "add")
				System.out.println("Add one new favorite");
			else if(evt.getNewValue() == "remove") {
				System.out.println("Remove one favorite");
				updateButtonStates(1);
			}
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
