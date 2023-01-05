package app.ui.components;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.JFrame;

import app.dto.ResultDto;
import app.entities.VineCharacter;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;

@SuppressWarnings("serial")
public class CharacterResultsPanel extends ResultsPanel {
	private List<ResultDto> characters;
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
					ResultDto character = characters.get(i);
					CharacterCoverPanel characterCover = new CharacterCoverPanel(characters.get(i).convertToCharacter(),
							databaseService);
					characterCover.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {

							ComicsInfosPanel infos = new ComicsInfosPanel(character);
							infos.fetchInformations();
							infos.createInfosPanel();
							// Creating the new frame that will display the informations the user wants.
							String frame_name = "";
							try {
								frame_name = infos.getResult().getName();
							} catch (NullPointerException e1) {}
							
							JFrame f = new JFrame(frame_name);
							try {

								URL url_image = new URL(infos.getResult().getImage().getIcon_url());
								Image icon = Toolkit.getDefaultToolkit().getImage(url_image);
								f.setIconImage(icon);
							} catch (MalformedURLException e1) {
							}
							f.setSize(1050, 600);
							f.add(infos);
							f.setResizable(false);
							f.setVisible(true);

						}
					});

					this.resultsList.add(characterCover);

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
