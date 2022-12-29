package app.ui.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

//import app.dto.InfosResultDto;
import app.services.ComicVineService;
import app.ui.themes.CustomColor;

// The goal of this class is to create a panel in which the informations of the selected comic will be displayed
public class ComicsInfosPanel extends JPanel {
	
	private ComicCoverPanel comicCover;
//	private InfosResultDto infosResult;
	private ComicVineService comicVineService;
	
	public ComicsInfosPanel(ComicCoverPanel comicCoverPanel) {
		comicVineService = new ComicVineService();
		//infosResult = new InfosResultDto();
		this.comicCover = comicCoverPanel;
	}
	
//	public void fetchInformations() {
//		this.comicVineService.search_from_url(this.comicCover.getResultsApi().getApi_detail_url());
//	}
	
	public void createInfosPanel() {
		
		JPanel box1 = new JPanel();
		JTextArea synopsis_title = new JTextArea("Synopsis");
		JTextArea synopsis = new JTextArea();
		String synopsis_text;
		JScrollPane scroll_synopsis_text;
		JTextArea creators_title = new JTextArea("Creators");
		JTextArea creators = new JTextArea("Creators");
		JScrollPane scroll_creators;
		JTextArea characters_title = new JTextArea("Characters");
		JTextArea characters = new JTextArea("Characters");
		JScrollPane scroll_characters;
		
		JPanel box2 = new JPanel();
		JLabel image = new JLabel();
		JTextArea issue_infos = new JTextArea("Informations about the issue");
		
		JScrollPane scrollPaneComicsInfos = new JScrollPane(this);
		
		// Column 1 (content: summary, creators, characters)
		box1.setLayout(new BoxLayout(box1, BoxLayout.Y_AXIS));
		box1.setBackground(CustomColor.WhiteCloud);
		
		synopsis_title.setEditable(false);
		synopsis_title.setBackground(CustomColor.WhiteCloud);
		box1.add(synopsis_title);
		
		box1.add(Box.createRigidArea(new Dimension(0, 10)));
		
		try {
			synopsis_text = this.comicCover.getResultsApi().getDescription().replaceAll("\\<.*?\\>", "");
		} catch (NullPointerException e) {
			synopsis_text = "Description not found.";
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
		scroll_synopsis_text.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CustomColor.Black));
		box1.add(scroll_synopsis_text);

		box1.add(Box.createRigidArea(new Dimension(0, 50)));
		
		creators_title.setEditable(false);
		creators_title.setBackground(CustomColor.WhiteCloud);
		box1.add(creators_title);
		box1.add(Box.createRigidArea(new Dimension(0, 10)));
		
		creators.setEditable(false);
		creators.setLineWrap(true);
		creators.setWrapStyleWord(true);
		creators.setBackground(CustomColor.WhiteCloud);
		creators.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 10));
		scroll_creators = new JScrollPane(creators);
		scroll_creators.setPreferredSize(new Dimension(700, 300));
		scroll_creators.setMaximumSize(new Dimension(700, 700));
		scroll_creators.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CustomColor.Black));
		box1.add(scroll_creators);
		
		box1.add(Box.createRigidArea(new Dimension(0, 50)));
		
		characters_title.setEditable(false);
		characters_title.setBackground(CustomColor.WhiteCloud);
		box1.add(characters_title);
		box1.add(Box.createRigidArea(new Dimension(0, 10)));
		
		characters.setEditable(false);
		characters.setLineWrap(true);
		characters.setWrapStyleWord(true);
		characters.setBackground(CustomColor.WhiteCloud);
		characters.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 10));
		scroll_characters = new JScrollPane(characters);
		scroll_characters.setPreferredSize(new Dimension(700, 400));
		scroll_characters.setMaximumSize(new Dimension(700, 700));
		scroll_characters.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CustomColor.Black));
		box1.add(scroll_characters);
		
		// ---------------
		
		// Column 2 (content: image, info about the issue)
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
		issue_infos.setEditable(false);
		issue_infos.setLineWrap(true);
		issue_infos.setBackground(CustomColor.WhiteCloud);
		issue_infos.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CustomColor.Black));
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
