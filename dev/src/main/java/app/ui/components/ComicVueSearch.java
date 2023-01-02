package app.ui.components;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import app.dto.SearchResultDto;
import app.entities.Issue;
import app.helpers.ComicVineSearchStatus;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;
import app.ui.themes.CustomColor;

@SuppressWarnings("serial")
public class ComicVueSearch extends JPanel {
	// Model
	protected UserModel userModel;
	// Controller
	protected DatabaseService databaseService;
	protected ComicVineService comicsVineService;

	/**
	 * @param User            Model
	 * @param comicVine       Service
	 * @param DatabaseService
	 */
	public ComicVueSearch(UserModel userModel, ComicVineService comicVineService, DatabaseService databaseService) {
		// Styles
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(CustomColor.WhiteCloud);
		this.setBounds(250, 150, 1350, 900);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// Models && Controllers
		this.userModel = userModel;
		this.comicsVineService = comicVineService;
		this.databaseService = databaseService;
		// * Issue Results Panel
		IssueResultsPanel issuesResultPanel = new IssueResultsPanel("Issues :", userModel, comicVineService,
				databaseService);
		// * Character Results Panel
		CharacterResultsPanel characterResultPanel = new CharacterResultsPanel("Characters :", userModel, comicVineService,
				databaseService);
		this.add(issuesResultPanel);
		this.add(characterResultPanel);

	}

}
