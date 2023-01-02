package app.ui.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import app.dto.InfosResultDto;
import app.services.ComicVineService;
import app.ui.themes.CustomColor;

/*
 *  The goal of this class is to create a panel in which the informations of the selected comic will be displayed.
 */
public class ComicsInfosPanel extends JPanel {
	
	private ComicCoverPanel comicCover;
	private ComicVineService cvs;
	private InfosResultDto infosResult;

	
	public ComicsInfosPanel(ComicCoverPanel comicCoverPanel) {
		this.comicCover = comicCoverPanel;
		this.infosResult = new InfosResultDto();
		this.cvs = new ComicVineService();
	}
	
	public InfosResultDto getInfosResult() {
		return this.infosResult;
	}
	
	public void fetchInformations() {
		System.out.println(this.comicCover.getResultsApi().getApi_detail_url());
		this.cvs.search_from_url(this.comicCover.getResultsApi().getApi_detail_url());
		this.infosResult = this.cvs.getInfosResult();
		System.out.println(this.infosResult.getResults());
	}
	
	public void createInfosPanel() {
		
		JPanel box1 = new JPanel();
		JTextArea synopsis_title = new JTextArea("Summary");
		JTextArea synopsis = new JTextArea();
		String synopsis_text;
		JScrollPane scroll_synopsis_text;
		JTextArea creators_title = new JTextArea("Creators");
		JTextArea creators = new JTextArea();
		JScrollPane scroll_creators;
		JTextArea characters_title = new JTextArea("Characters");
		JTextArea characters = new JTextArea();
		JScrollPane scroll_characters;
		Font title_font = new Font("Dialog", Font.BOLD, 16);

		
		JPanel box2 = new JPanel();
		JLabel image = new JLabel();
		JTextArea issue_infos = new JTextArea();
		
		JScrollPane scrollPaneComicsInfos = new JScrollPane(this);
		
		
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
			synopsis_text = this.comicCover.getResultsApi().getDescription().replaceAll("\\<.*?\\>", "");
		} catch (NullPointerException e) {
			synopsis_text = "Sorry, no description was found.";
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
		
		creators_title.setEditable(false);
		creators_title.setFont(title_font);
		creators_title.setBackground(CustomColor.WhiteCloud);
		box1.add(creators_title);
		
		box1.add(Box.createRigidArea(new Dimension(0, 10)));
		
		try {
			for (int i = 0; i < this.comicCover.getResultsApi().getCreators().size(); i++) {
				characters.setText(this.comicCover.getResultsApi().getCreators().get(i).getName() + "\n");
			}
		} catch (NullPointerException e) {
			creators.setText("Sorry, no creators were found.");
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
			if (!this.infosResult.getResults().getCharacter_credits().isEmpty()) {
				for (int i = 0; i < this.infosResult.getResults().getCharacter_credits().size(); i++) {
					characters.setText(characters.getText() + this.infosResult.getResults().getCharacter_credits().get(i).getName() + '\n');
				}
			} else {
				characters.setText("Sorry, no characters were found.");
			}
		} catch (NullPointerException e) {
			characters.setText("Sorry, no characters were found.");
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
		
		ImageIcon img;
		try {
			URL url_img = new URL(this.comicCover.getResultsApi().getImage().getMedium_url());
			BufferedImage imageBrute = ImageIO.read(url_img);
			Image imageResize = imageBrute.getScaledInstance(206, 310, Image.SCALE_DEFAULT);
			img = new ImageIcon(imageResize);
			image.setIcon(img);
		} catch (IOException e) {
			// The url is displayed in case the image cold not be loaded
			img = new ImageIcon(this.comicCover.getResultsApi().getImage().getMedium_url());
		}
		image.setIcon(img);
		box2.add(image);
		
		box2.add(Box.createRigidArea(new Dimension(0, 50)));
		image.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// matching the size of the block to the size of the image
		issue_infos.setMaximumSize(new Dimension(image.getMaximumSize().width, 500));
		issue_infos.setText(
				"Name : " + this.comicCover.getResultsApi().getName() + '\n' + 
				"Volume : " + this.comicCover.getResultsApi().getVolume().getName() + '\n' +
				"Issue number : " + this.comicCover.getResultsApi().getIssue_number() + '\n' +
				"Cover date : " + this.comicCover.getResultsApi().getCover_date() + '\n'
				);
		issue_infos.setEditable(false);
		issue_infos.setLineWrap(true);
		issue_infos.setBackground(CustomColor.WhiteCloud);
		issue_infos.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 10));
		issue_infos.setBorder(null);
		box2.add(issue_infos);
		
		this.setBackground(CustomColor.WhiteCloud);
		this.setPreferredSize(new Dimension(1000, 600));
		this.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(BorderLayout.WEST, box1);
		// Vertical separator
		this.add(Box.createHorizontalGlue());
		this.add(BorderLayout.WEST, box2);
		this.setVisible(true);
		
		scrollPaneComicsInfos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneComicsInfos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneComicsInfos.getVerticalScrollBar().setUnitIncrement(14);
		scrollPaneComicsInfos.getHorizontalScrollBar().setUnitIncrement(14);
	}
}
