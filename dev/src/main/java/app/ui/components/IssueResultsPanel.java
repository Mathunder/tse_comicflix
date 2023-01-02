package app.ui.components;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import app.entities.Issue;
import app.helpers.ComicVineSearchStatus;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;

@SuppressWarnings("serial")
public class IssueResultsPanel extends ResultsPanel {
	private ComicVineService comicVineService;
	private UserModel userModel;
	private DatabaseService databaseService;
	private List<Issue> issues;
	private List<ComicCoverPanel> comicCoverPanels = new ArrayList<>();

	public IssueResultsPanel(String resultType, UserModel userModel, ComicVineService comicVineService,
			DatabaseService databaseService) {
		super(resultType, userModel, comicVineService, databaseService);
		this.comicVineService = comicVineService;
		this.userModel = userModel;
		this.databaseService = databaseService;

	}

	public void showResult() {
		this.resultsList.removeAll();

		if (issues.size() != 0) {
			this.setVisible(true);
			// Show ResultAPIIssues
			for (int i = 0; i < issues.size(); i++) {
				ComicCoverPanel comicCover = new ComicCoverPanel(issues.get(i), databaseService, userModel.getUser());
				comicCoverPanels.add(comicCover);
				this.resultsList.add(comicCover);
			}
		}else {
			this.setVisible(false);
		}

		this.resultsList.revalidate();
		this.resultsList.repaint();
	}

	// Refresh state of buttons on the coverPanel
	public void updateButtonStates(int itemRefreshCode) { // if 0 : RefreshAllButtons, if 1 : refreshOnlyFavorites, if 2
															// : refreshOnlyReads

		for (int i = 0; i < issues.size(); i++) {
			if (itemRefreshCode == 1 || itemRefreshCode == 0) {
				boolean isFavorite = false;
				// Find if the issue displayed is favorite for the User
				for (Issue favorite : userModel.getUserFavoriteIssues()) {
					if (favorite.getId() == issues.get(i).getId()) {
						isFavorite = true;
						break;
					}
				}
				comicCoverPanels.get(i).refreshStateButtons(isFavorite);
			}

			if (itemRefreshCode == 2 || itemRefreshCode == 0) {
				int readState = 0;
				// Find if the issue displayed is reading by the User
				for (Issue reading : userModel.getUserReadingIssues()) {
					if (reading.getId() == issues.get(i).getId()) {
						readState = 1;
						break;
					}
				}

				// Find if the issue displayed is readed by the User
				for (Issue readed : userModel.getUserReadedIssues()) {
					if (readed.getId() == issues.get(i).getId()) {
						readState = 2;
						break;
					}
				}
				comicCoverPanels.get(i).refreshStateButtons(readState);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "searchStatus" || evt.getPropertyName() == "clearSearchResults"
				|| evt.getPropertyName() == "searchResultsChanged" ) {
			
			this.issues = this.comicVineService.getIssueResults();
			showResult();
		} else if (evt.getPropertyName() == "userChange") {
			showResult();
			if (userModel.getIsAuthenticated()) {
				updateButtonStates(0);
			}
		} else if (evt.getPropertyName() == "favoriteChange") {
			if (evt.getNewValue() == "add")
				System.out.println("Add one new favorite");
			else if (evt.getNewValue() == "remove") {
				System.out.println("Remove one favorite");
				updateButtonStates(1);
			}
		} else if (evt.getPropertyName() == "readChange") {
			if (evt.getNewValue() == "add")
				System.out.println("Add new read (change state)");
			else if (evt.getNewValue() == "remove")
				System.out.println("remove read");
			updateButtonStates(2);
		}
		

	}

}
