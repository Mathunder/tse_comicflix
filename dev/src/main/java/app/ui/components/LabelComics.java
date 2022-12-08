package app.ui.components;

import java.awt.Font;
import javax.swing.JLabel;
import app.entities.Comics;
import app.ui.themes.CustomColor;
import java.awt.event.*;

public class LabelComics extends JLabel{
	
	LabelComics(Comics comics){
				
		super(comics.getTitle(), comics.getImage(), JLabel.CENTER); //Call constructor of the mother class (JLabel)
				
		this.setForeground(CustomColor.Black);
		this.setFont(new Font("Arial", Font.PLAIN, 20)); //set font of text
		this.setBackground(CustomColor.WhiteCloud);
		this.setHorizontalTextPosition(JLabel.CENTER); //set text L, R, CENTER of imageIcon
		this.setVerticalTextPosition(JLabel.BOTTOM);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println(comics.getSynopsis() + " has been clicked.");
				
			}
		});
	}
}

