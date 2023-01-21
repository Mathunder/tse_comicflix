 package app.ui.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import app.dto.ResponseDto;
import app.dto.ResultDto;
import app.entities.Issue;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;
import app.ui.themes.CustomColor;

/*
 *  The goal of this class is to create a panel in which the informations of the selected comic will be displayed.
 */
@SuppressWarnings("serial")
public class ComicsInfosPanel extends JPanel implements PropertyChangeListener {
	
	private ResultDto result;
	private ComicVineService cvs;
	private ResponseDto response;
	private String type = "";
	private ResultDto result_volume;
	private ResultDto result_prev;
	private ResultDto result_next;
	private DatabaseService databaseService;
	private UserModel userModel;
	private ComicCoverPanel comicCoverPanel_current;
	private ComicCoverPanel comicCoverPanel_prev;
	private ComicCoverPanel comicCoverPanel_next;
	private List<ComicCoverPanel> comicCoverPanels = new ArrayList<>();
	private boolean hasNext = false;
	private boolean hasPrev = false;
	
	
	// This constructor is used when doing a research of an issue
	public ComicsInfosPanel(ResultDto result, DatabaseService dbS, UserModel um) {
		this.result = result;
		this.cvs = new ComicVineService();
		this.type = "issue";
		this.databaseService = dbS;
		this.userModel = um;
		this.userModel.addPropertyChangeListener(this);
	}
	
	// This constructor is used when doing a research of a character
	public ComicsInfosPanel(ResultDto result, String type) {
		this.result = result;
		this.cvs = new ComicVineService();
		this.type = type;
	}
		
	// This constructor is used when displaying an issue from local issue
	public ComicsInfosPanel(String api_detail_url, DatabaseService dbS, UserModel um) {
		this.databaseService = dbS;
		this.userModel = um;
		this.cvs = new ComicVineService();
		this.result = new ResultDto();
		this.result.setApi_detail_url(api_detail_url);
		this.type = "issue";
		this.userModel.addPropertyChangeListener(this);
	}
	
	public ResponseDto getResponse() {
		return this.response;
	}
	
	public ResultDto getResult() {
		return this.result;
	}
	
	public void fetchInformations() {
		// Fetching the informations of the issue/character
		this.cvs.search_from_url(this.result.getApi_detail_url()); 
		this.response = this.cvs.getInfosResult();
		this.result = this.cvs.getInfosResult().getResults();
		
		// Fetching the informations of the volume if the type is "issue"
		if (this.type == "issue") {
			this.cvs = new ComicVineService();
			this.response = new ResponseDto();
			this.cvs.search_from_url(this.result.getVolume().getApi_detail_url());
			this.response = this.cvs.getInfosResult();
			this.result_volume = this.cvs.getInfosResult().getResults();
			this.comicCoverPanel_current = new ComicCoverPanel(this.result.convertToIssue(), this.databaseService, this.userModel.getUser());
			this.comicCoverPanels.add(this.comicCoverPanel_current);
		}
	}
	
	public void fetchPreviousNextInformations() {
		// Sometimes the API has missing issues (ex: 2, 3, 4, 6, 7...)
		
		// Verifying if the current issue has has a sequel and/or prequel
		if (this.result_volume.getSpecificIssue(Integer.parseInt(this.result.getIssue_number()) + 1).getApi_detail_url() != null) {
			this.hasNext = true;
		}
		if (this.result_volume.getSpecificIssue(Integer.parseInt(this.result.getIssue_number()) - 1).getApi_detail_url() != null) {
			this.hasPrev = true;
		}

		// Fetching the informations of the sequel
		if (this.hasNext) {
			this.cvs = new ComicVineService();
			this.response = new ResponseDto();
			try {
				this.cvs.search_from_url(this.result_volume.getSpecificIssue(Integer.parseInt(this.result.getIssue_number()) + 1).getApi_detail_url());
			} catch (IllegalArgumentException e) {
				// error msg
			}
			this.response = this.cvs.getInfosResult();
			this.result_next = this.cvs.getInfosResult().getResults();
			this.comicCoverPanel_next = new ComicCoverPanel(this.result_next.convertToIssue(), this.databaseService, this.userModel.getUser());
			this.comicCoverPanels.add(comicCoverPanel_next);
		}
		// Fetching the informations of the prequel
		if (this.hasPrev) {
			this.cvs = new ComicVineService();
			this.response = new ResponseDto();
			try {
				this.cvs.search_from_url(this.result_volume.getSpecificIssue(Integer.parseInt(this.result.getIssue_number()) - 1).getApi_detail_url());
			} catch (IllegalArgumentException e) {
				// error msg
			}
			this.response = this.cvs.getInfosResult();
			this.result_prev = this.cvs.getInfosResult().getResults();
			this.comicCoverPanel_prev = new ComicCoverPanel(this.result_prev.convertToIssue(), this.databaseService, this.userModel.getUser());
			this.comicCoverPanels.add(this.comicCoverPanel_prev);
		}
	}
	
	/*
	 * This method create the panel in which every information about a character or an issue is displayed.
	 * The data is systematically analyzed like this :
	 * --> is it empty ?
	 * --> is it null ?
	 * If one of these conditions is true, then a default message will be displayed.
	 * These tests are all in a try{ if/else }/catch section
	 */
	public void createInfosPanel() {
		
		removeAll();
		
		JPanel subpanel1 = new JPanel();
		JPanel subpanel2 = new JPanel();
		
		JPanel box1 = new JPanel();
		JTextArea synopsis_title = new JTextArea("Résumé");
		JTextArea synopsis = new JTextArea();
		String synopsis_text = "";
		JScrollPane scroll_synopsis_text;
		JTextArea creators_title = new JTextArea();
		JTextArea creators = new JTextArea();
		JScrollPane scroll_creators;
		JTextArea characters_title = new JTextArea("Personnages");
		JTextArea characters = new JTextArea();
		JScrollPane scroll_characters;
		
		JPanel box2 = new JPanel();
		JLabel image = new JLabel();
		JTextArea infos = new JTextArea();
		JPanel field_title = new JPanel();
		JPanel other_data = new JPanel();
		JTextArea field_title_name = new JTextArea();
		JTextArea field_title_volume = new JTextArea();
		JTextArea field_title_issue_number = new JTextArea();
		JTextArea field_title_cover_date = new JTextArea();
		JTextArea name = new JTextArea();
		JTextArea volume = new JTextArea();
		JTextArea issue_number = new JTextArea();
		JTextArea cover_date = new JTextArea();
		JPanel box3 = new JPanel();
		JPanel box_notes = new JPanel();
		JTextArea notes = new JTextArea();

		Font title_font = new Font("Dialog", Font.BOLD, 16);
		Font field_title_font = new Font("Dialog", Font.BOLD, 12);
		
		
		/* -------------------------------------------------- */
		
		
		/*
		 *  Column 1. Content: 
		 *  1. Summary
		 *  2. Creators
		 *  3. Characters
		 */
		box1.setLayout(new BoxLayout(box1, BoxLayout.Y_AXIS));
		box1.setBackground(CustomColor.WhiteCloud);
		
		synopsis_title.setEditable(false);
		synopsis_title.setFont(title_font);
		synopsis_title.setBackground(CustomColor.WhiteCloud);
		box1.add(synopsis_title);
		
		box1.add(Box.createRigidArea(new Dimension(0, 10)));
		
		try {
			if (this.type == "issue") {
				if (!this.result.getDescription().isEmpty()) {
					synopsis_text = this.result.getDescription().replaceAll("\\<.*?\\>", "");
				} else {
					synopsis_text = "Désolé, nous n'avons pas trouvé de résumé.";
				}
			} else if (this.type == "character") {
				if (!this.result.getDeck().isEmpty()) {
					synopsis_text = this.result.getDeck().replaceAll("\\<.*?\\>", "");
				} else {
					synopsis_text = "Désolé, nous n'avons pas trouvé de résumé.";
				}
			}
		} catch (NullPointerException e) {
			synopsis_text = "Désolé, nous n'avons pas trouvé de résumé.";
		}
		synopsis.setText(synopsis_text);
		synopsis.setEditable(false);
		// Allow to wrap line
		synopsis.setLineWrap(true);
		// Wrap lines only between words (and not in the middle of one)
		synopsis.setWrapStyleWord(true);
		synopsis.setBackground(CustomColor.WhiteCloud);
		synopsis.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
		scroll_synopsis_text = new JScrollPane(synopsis);
		scroll_synopsis_text.setPreferredSize(new Dimension(700, 500));
		scroll_synopsis_text.setMaximumSize(new Dimension(700, 700));
		scroll_synopsis_text.getVerticalScrollBar().setUnitIncrement(5);
		scroll_synopsis_text.setBorder(null);
		box1.add(scroll_synopsis_text);

		box1.add(Box.createRigidArea(new Dimension(0, 50)));
		
		if (this.type == "character") {
			creators_title.setText("Créateurs");
		} else if (this.type == "issue") {
			creators_title.setText("Crédits");
		}
		creators_title.setEditable(false);
		creators_title.setFont(title_font);
		creators_title.setBackground(CustomColor.WhiteCloud);
		box1.add(creators_title);
		box1.add(Box.createRigidArea(new Dimension(0, 10)));

		try {
			if (this.type == "character") {
				if (!this.result.getCreators().isEmpty()) {
					for (int i = 0; i < this.result.getCreators().size(); i++) {
						creators.setText(this.result.getCreators().get(i).getName() + "\n");
					}
				} else {
					creators.setText("Désolé, nous n'avons pas trouvé de créateurs.");
				}
			} else if (this.type == "issue") {
				if (!this.result.getPerson_credits().isEmpty()) {
					for (int i = 0; i < this.result.getPerson_credits().size(); i++) {
						creators.setText(this.result.getPerson_credits().get(i).getRole() + " : "
								+ this.result.getPerson_credits().get(i).getName() + "\n");
					}
				} else {
					creators.setText("Désolé, nous n'avons pas trouvé de crédits.");
				}
			}
		} catch (NullPointerException e) {
			if (this.type == "issue") {
				creators.setText("Désolé, nous n'avons pas trouvé de crédits.");
			} else if (this.type == "character") {
				creators.setText("Désolé, nous n'avons pas trouvé de crédits.");
			}
		}
		creators.setEditable(false);
		creators.setLineWrap(true);
		creators.setWrapStyleWord(true);
		creators.setBackground(CustomColor.WhiteCloud);
		creators.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 10));
		scroll_creators = new JScrollPane(creators);
		scroll_creators.setPreferredSize(new Dimension(700, 300));
		scroll_creators.setMaximumSize(new Dimension(700, 700));
		scroll_creators.setBorder(null);
		box1.add(scroll_creators);
		
		box1.add(Box.createRigidArea(new Dimension(0, 50)));
		
		characters_title.setEditable(false);
		characters_title.setFont(title_font);
		characters_title.setBackground(CustomColor.WhiteCloud);
		box1.add(characters_title);
		
		box1.add(Box.createRigidArea(new Dimension(0, 10)));

		// The character_credits field can be null; this case is handled below in the try/catch.
		try {
			// Sometime the character_credits field is not null but empty; this case is handled below in the if/else.
			if (!this.result.getCharacter_credits().isEmpty()) {
				for (int i = 0; i < this.result.getCharacter_credits().size(); i++) {
					characters.setText(characters.getText() + this.result.getCharacter_credits().get(i).getName() + '\n');
				}
			} else {
				characters.setText("Désolé, nous n'avons pas trouvé de personnages.");
			}
		} catch (NullPointerException e) {
			characters.setText("Désolé, nous n'avons pas trouvé de personnages.");
		}
		characters.setEditable(false);
		characters.setLineWrap(true);
		characters.setWrapStyleWord(true);
		characters.setBackground(CustomColor.WhiteCloud);
		characters.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 10));
		scroll_characters = new JScrollPane(characters);
		scroll_characters.setPreferredSize(new Dimension(700, 400));
		scroll_characters.setMaximumSize(new Dimension(700, 700));
		scroll_characters.setBorder(null);
		box1.add(scroll_characters);
		
		
		/* -------------------------------------------------- */

		
		/*
		 *  Column 2. Content: 
		 *  image
		 *  informations about the issue/character
		 */
		box2.setLayout(new BoxLayout(box2, BoxLayout.Y_AXIS));
		box2.setBackground(CustomColor.WhiteCloud);
		
		if (this.type == "issue") {
			box2.add(this.comicCoverPanel_current);
		} else if (this.type == "character") {
			ImageIcon img;
			try {
				URL url_img = new URL(this.result.getImage().getMedium_url());
				BufferedImage imageBrute = ImageIO.read(url_img);
				Image imageResize = imageBrute.getScaledInstance(206, 310, Image.SCALE_DEFAULT);
				img = new ImageIcon(imageResize);
				image.setIcon(img);
			} catch (IOException e) {
				// The url is displayed in case the image cold not be loaded
				img = new ImageIcon(this.result.getImage().getMedium_url());
			}
			image.setIcon(img);
			box2.add(image);
			image.setAlignmentX(Component.CENTER_ALIGNMENT);
			box2.add(Box.createRigidArea(new Dimension(0, 50)));
		}
		
		// matching the size of the block to the size of the image
		if (this.type == "character") {
			infos.setMaximumSize(new Dimension(image.getMaximumSize().width, 500));
		}
		
		infos.setLayout(new BoxLayout(infos, BoxLayout.X_AXIS));
		field_title.setLayout(new BoxLayout(field_title, BoxLayout.Y_AXIS));
		infos.setOpaque(true);
		field_title.setOpaque(true);
		field_title.setBackground(CustomColor.WhiteCloud);
		other_data.setLayout(new BoxLayout(other_data, BoxLayout.Y_AXIS));
		other_data.setOpaque(true);
		other_data.setBackground(CustomColor.WhiteCloud);
		infos.add(field_title);
		infos.add(other_data);
		
		field_title_name.setText("Nom :");
		field_title_name.setFont(field_title_font);
		field_title_name.setBackground(null);
		field_title_volume.setText("Volume :");
		field_title_volume.setFont(field_title_font);
		field_title_volume.setBackground(null);
		field_title_issue_number.setText("Numéro :");
		field_title_issue_number.setFont(field_title_font);
		field_title_issue_number.setBackground(null);
		field_title_cover_date.setText("Date :");
		field_title_cover_date.setFont(field_title_font);
		field_title_cover_date.setBackground(null);
		name.setText("Non trouvé");
		name.setBackground(null);
		volume.setText("Non trouvé");
		volume.setBackground(null);
		issue_number.setText("Non trouvé");
		issue_number.setBackground(null);
		cover_date.setText("Non trouvé");
		cover_date.setBackground(null);
		// The following lines handle the case where the comic informations are null
		if (this.result.getName() != null)
			name.setText(this.result.getName());
		if (this.result.getVolume() != null) {
			if (this.result.getVolume().getName() != null)
				volume.setText(this.result.getVolume().getName());
		}
		if (this.result.getIssue_number() != null)
			issue_number.setText(this.result.getIssue_number());
		if (this.result.getCover_date() != null)
			cover_date.setText(this.result.getCover_date());
		field_title.add(field_title_name);
		field_title.add(field_title_volume);
		field_title.add(field_title_issue_number);
		field_title.add(field_title_cover_date);
		other_data.add(name);
		other_data.add(volume);
		other_data.add(issue_number);
		other_data.add(cover_date);
		infos.setBackground(CustomColor.WhiteCloud);
		infos.setBorder(null);
		box2.add(infos);
		
		
		/* -------------------------------------------------- */
		
		
		/*
		 * This part displays user personal notes if a user is connected and if it is an issue
		 */
		if (this.type == "issue" && userModel.getIsAuthenticated()) {
			
		}
		
		
		/*
		 * This part displays the previous and next issue when an issue is selected
		 */
		if (this.type == "issue") {
			box3.setLayout(new BoxLayout(box3, BoxLayout.X_AXIS));
			box3.setBackground(CustomColor.WhiteCloud);
			if (this.hasNext && this.hasPrev) {
				this.comicCoverPanel_prev.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						ComicsInfosPanel infos = new ComicsInfosPanel(comicCoverPanel_prev.getIssue().getApi_detail_url(), databaseService, userModel);
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
				box3.add(this.comicCoverPanel_prev);
				box3.add(Box.createRigidArea(new Dimension(70, 0)));
				this.comicCoverPanel_next.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						ComicsInfosPanel infos = new ComicsInfosPanel(comicCoverPanel_next.getIssue().getApi_detail_url(), databaseService, userModel);
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
				box3.add(this.comicCoverPanel_next);
			} else if (this.hasNext && !this.hasPrev) {
				this.comicCoverPanel_next.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						ComicsInfosPanel infos = new ComicsInfosPanel(comicCoverPanel_next.getIssue().getApi_detail_url(), databaseService, userModel);
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
				box3.add(this.comicCoverPanel_next);
			} else if ((!this.hasNext) && this.hasPrev) {
				this.comicCoverPanel_prev.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						ComicsInfosPanel infos = new ComicsInfosPanel(comicCoverPanel_prev.getIssue().getApi_detail_url(), databaseService, userModel);
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
				box3.add(this.comicCoverPanel_prev);
			}
		}

			
		
		subpanel1.setBackground(CustomColor.WhiteCloud);
		subpanel1.setPreferredSize(new Dimension(1000, 600));
		subpanel1.setLayout(new BoxLayout(subpanel1, BoxLayout.LINE_AXIS));
		subpanel1.add(BorderLayout.WEST, box1);
		subpanel1.add(Box.createHorizontalGlue());
		subpanel1.add(BorderLayout.WEST, box2);
		subpanel1.setVisible(true);
		
		subpanel2.setBackground(CustomColor.WhiteCloud);
//		subpanel2.setLayout(new BoxLayout(subpanel2, BoxLayout.X_AXIS));
		subpanel2.add(BorderLayout.CENTER, box3);
		subpanel2.setVisible(true);

		
		this.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		this.setBackground(CustomColor.WhiteCloud);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(BorderLayout.NORTH, subpanel1);
		this.add(Box.createRigidArea(new Dimension(0, 60)));
		this.add(BorderLayout.SOUTH, subpanel2);
		if (this.type == "issue") {
			if(userModel.getIsAuthenticated())
				this.updateButtonStates(0);
		}
		this.setVisible(true);
		
	}

	public void updateButtonStates(int itemRefreshCode) {
		
		for(int i=0; i<this.comicCoverPanels.size();i++) {
			//Favorite update
			if(itemRefreshCode == 1 || itemRefreshCode == 0) {
				boolean isFavorite = false;
				for(Issue favorite : userModel.getUserFavoriteIssues()) {
					if(favorite.getId() == this.comicCoverPanels.get(i).getIssue().getId()) {
						isFavorite = true;
						break;
					}
				}
				this.comicCoverPanels.get(i).refreshStateButtons(isFavorite);
			}
			//Reading/Readed UPDATE
			if(itemRefreshCode == 2 || itemRefreshCode == 0) {
				int readState = 0;
				for(Issue reading : userModel.getUserReadingIssues()) {
					if(reading.getId() == this.comicCoverPanels.get(i).getIssue().getId()) {
						readState = 1;
						break;
					}
				}
				// Find if the issue displayed is readed by the User
				for(Issue readed : userModel.getUserReadedIssues()) {
					if(readed.getId() == this.comicCoverPanels.get(i).getIssue().getId()) {
						readState = 2;
						break;
					}
				}
				this.comicCoverPanels.get(i).refreshStateButtons(readState);
			}
			//COLLECTION UPDATE
			if(itemRefreshCode == 3 || itemRefreshCode == 0) {
				String selectedItem = "All";
				Boolean findCorrespondance = false;
				for(int j=0; j<userModel.getUserCollections().size();j++) {
					if (!findCorrespondance) {
						for(Issue issue_col: userModel.getUserCollections().get(j).getIssues()) {
							if(issue_col.getId() == this.comicCoverPanels.get(i).getIssue().getId()) {
								selectedItem = userModel.getUserCollections().get(j).getName();
								findCorrespondance = true;
								break;
							}
						}
					}
					else 
						break;
				}

				this.comicCoverPanels.get(i).refreshStateComboBox(selectedItem);
			}
			if(itemRefreshCode == 4 || itemRefreshCode == 0) {
				this.comicCoverPanels.get(i).updateComboBoxList();
				updateButtonStates(3);
			}
		}
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(userModel.getIsAuthenticated()) {
			if(evt.getPropertyName() == "userChange") {
				updateButtonStates(0);
			}
			else if(evt.getPropertyName() == "favoriteChange"){
				if(evt.getNewValue() == "add")
					System.out.println("Add one new favorite (VueInfo)");
				else if(evt.getNewValue() == "remove")
					System.out.println("Remove one favorite (VueInfo)");
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
					System.out.println("Collection change [add] (VueInfo)");
				else if(evt.getNewValue() == "remove")
					System.out.println("Collection change [remove] (VueInfo)");
				updateButtonStates(3);
			}
			else if(evt.getPropertyName() == "collectionListChange") {
				updateButtonStates(4);
			}
		}
	}
}