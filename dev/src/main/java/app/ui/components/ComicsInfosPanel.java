package app.ui.components;

import java.awt.*;
import javax.swing.*;

import app.entities.Comics;

// The goal of this class is to create a panel in which the informations of the selected comic will be displayed
public class ComicsInfosPanel extends JPanel {
	
	public void createComicsInfosPanel(Comics comic) {
		
		// Column 1 (content: summuary, creators, characters)
		JPanel box1 = new JPanel();  // new JPanel which will be the first column of informations will be displayed
		box1.setLayout(new BoxLayout(box1, BoxLayout.Y_AXIS));  // chosen layout: BoxLayout
		
		JTextField text = new JTextField(comic.getSynopsis());  // adding the synopsis 
		text.setPreferredSize(new Dimension(300, 400));
		box1.add(text);
		box1.add(Box.createRigidArea(new Dimension(0, 50))); // adding some space between the blocks
		
		JTextField creators = new JTextField();
		// adding the creators
//		for (int i = 0; i < comic.getCreators().size(); i++) {
//			creators.add(comic.getCreators().get(i), creators);
//		}
		creators.setPreferredSize(new Dimension(300, 100));
		box1.add(creators);
		box1.add(Box.createRigidArea(new Dimension(0, 50)));
		
		JTextField characters = new JTextField();
		// adding the characters
//		for (int i = 0; i < comic.getCharacters().size(); i++) {
//			creators.add(comic.getCharacters().get(i), characters);
//		}
		characters.setPreferredSize(new Dimension(300, 200));
		box1.add(characters);
		
		// ---------------
		
		// Column 2 (content: image, infos about the issue)
		JPanel box2 = new JPanel();  // new JPanel which will be the second column of informations will be displayed
		box2.setLayout(new BoxLayout(box2, BoxLayout.Y_AXIS));// chosen layout: BoxLayout
		
		JLabel image = new JLabel(comic.getImage());  // adding the image
		box2.add(image);
		box2.add(Box.createRigidArea(new Dimension(0, 50)));  // adding some space between the blocks
		image.setAlignmentX(Component.CENTER_ALIGNMENT);  // center the image
		
		JTextField issue_infos = new JTextField("Informations about the issue");  // adding the informations about the issue
		issue_infos.setMaximumSize(new Dimension(image.getMaximumSize().width, 500));  // matching the size of the block to the size of the image
		box2.add(issue_infos);

		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(BorderLayout.WEST, box1);
		// Vertical separator
		this.add(Box.createRigidArea(new Dimension(50, 0)));
		this.add(BorderLayout.WEST, box2);
	}
	
	public void displayComicsInfosPanel() {
		// display comics infos here
	}
}
