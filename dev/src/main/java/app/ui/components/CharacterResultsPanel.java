package app.ui.components;

import java.beans.PropertyChangeEvent;
import java.util.List;

import app.entities.VineCharacter;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;

@SuppressWarnings("serial")
public class CharacterResultsPanel extends ResultsPanel {
	private List<VineCharacter> characters;
	private ComicVineService comicVineService;

	private DatabaseService databaseService;

	public CharacterResultsPanel(String resultType, UserModel userModel, ComicVineService comicVineService,
			DatabaseService databaseService) {
		super(resultType, userModel, comicVineService, databaseService);
		this.comicVineService = comicVineService;

	}

	public void showResult() {
		this.resultsList.removeAll();

		if (characters.size() != 0) {
			this.setVisible(true);
			// Show ResultAPIIssues
			for (int i = 0; i < characters.size(); i++) {

				try {
					CharacterCoverPanel characterCoverPanel = new CharacterCoverPanel(characters.get(i),
							databaseService);
					this.resultsList.add(characterCoverPanel);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		} else {
			this.setVisible(false);
		}
		this.resultsList.revalidate();
		this.resultsList.repaint();
		this.repaint();
		this.revalidate();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "searchStatus" || evt.getPropertyName() == "clearSearchResults"
				|| evt.getPropertyName() == "searchResultsChanged") {
			this.characters = this.comicVineService.getCharacterResults();
			showResult();
		}

	}

}
