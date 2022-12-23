package app.ui.components;

import java.awt.*;
import javax.swing.*;

import app.entities.Comics;
import app.ui.themes.CustomColor;

// The goal of this class is to create a panel in which the informations of the selected comic will be displayed
public class ComicsInfosPanel extends JPanel {
	
	public void createComicsInfosPanel(Comics comic) {
		
		JPanel box1 = new JPanel();
		JTextArea synopsis_title = new JTextArea("Synopsis");
		JTextArea synopsis = new JTextArea();
		String synopsis_text;
		
		JTextArea creators = new JTextArea("Creators");
		JTextArea characters = new JTextArea("Characters");
		JPanel box2 = new JPanel();
		JLabel image = new JLabel(comic.getImage());
		JTextArea issue_infos = new JTextArea("Informations about the issue");


		
		// Column 1 (content: summary, creators, characters)
		box1.setLayout(new BoxLayout(box1, BoxLayout.Y_AXIS));
		
		synopsis_title.setEditable(false);
		box1.add(synopsis_title);
		
		box1.add(Box.createRigidArea(new Dimension(0, 10)));
		
		synopsis.setPreferredSize(new Dimension(700, 200));
		synopsis.setMinimumSize(new Dimension(700, 100));
		synopsis.setMaximumSize(new Dimension(700, 300));
		try {
			synopsis_text = comic.getSynopsis().replaceAll("\\<.*?\\>", "");
		} catch (NullPointerException e) {
			synopsis_text = "Description not found.";
		}
		synopsis.setText(synopsis_text);
		synopsis.setEditable(false);
		// Allow to wrap line
		synopsis.setLineWrap(true);
		// Wrap lines only between words (and not in the middle of one)
		synopsis.setWrapStyleWord(true);
		synopsis.setBackground(CustomColor.LightGray);
		box1.add(synopsis);

		box1.add(Box.createRigidArea(new Dimension(0, 50)));
		
		creators.setPreferredSize(new Dimension(700, 200));
		creators.setMinimumSize(new Dimension(700, 100));
		creators.setMaximumSize(new Dimension(700, 300));
		creators.setEditable(false);
		creators.setLineWrap(true);
		creators.setWrapStyleWord(true);
		creators.setBackground(CustomColor.LightGray);
		box1.add(creators);
		
		box1.add(Box.createRigidArea(new Dimension(0, 50)));
		
		characters.setPreferredSize(new Dimension(700, 200));
		characters.setMinimumSize(new Dimension(700, 100));
		characters.setMaximumSize(new Dimension(700, 300));
		characters.setEditable(false);
		characters.setLineWrap(true);
		characters.setWrapStyleWord(true);
		characters.setBackground(CustomColor.LightGray);
		box1.add(characters);
		
		// ---------------
		
		// Column 2 (content: image, info about the issue)
		box2.setLayout(new BoxLayout(box2, BoxLayout.Y_AXIS));
	
		box2.add(image);
		
		box2.add(Box.createRigidArea(new Dimension(0, 50)));
		image.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		issue_infos.setEditable(false);
		issue_infos.setLineWrap(true);
		// matching the size of the block to the size of the image
		issue_infos.setMaximumSize(new Dimension(image.getMaximumSize().width, 500));
		issue_infos.setEditable(false);
		issue_infos.setLineWrap(true);
		issue_infos.setBackground(CustomColor.LightGray);
		box2.add(issue_infos);
		
		this.setPreferredSize(new Dimension(1000, 600));
		this.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(BorderLayout.WEST, box1);
		// Vertical separator
		this.add(Box.createHorizontalGlue());
		this.add(BorderLayout.WEST, box2);
		this.setVisible(true);
	}
}
