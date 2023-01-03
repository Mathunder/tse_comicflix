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
	private List<ResultDto> issues;
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
				ResultDto issue = issues.get(i);
				ComicCoverPanel comicCover = new ComicCoverPanel(issue.convertToIssue(), databaseService, userModel.getUser());
				comicCoverPanels.add(comicCover);
				// Adding the mouse listener to enable the click on a search result
				comicCover.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {

						ComicsInfosPanel infos = new ComicsInfosPanel(issue);
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
				this.resultsList.add(comicCover);
			}
		} else {
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
				|| evt.getPropertyName() == "searchResultsChanged") {

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
