package app.ui.components;

import java.awt.Font;
import javax.swing.JLabel;
import app.entities.Comics;
import app.ui.themes.CustomColor;

public class LabelComics extends JLabel{
	
	LabelComics(Comics comics, int n){
				
		super(comics.getTitle(), comics.getImage(), JLabel.CENTER); //Call constructor of the mother class (JLabel)
				
		this.setForeground(CustomColor.Black.getColor());
		this.setFont(new Font("Arial", Font.PLAIN, 20)); //set font of text
		this.setBackground(CustomColor.WhiteCloud.getColor());
		this.setHorizontalTextPosition(JLabel.CENTER); //set text L, R, CENTER of imageIcon
		this.setVerticalTextPosition(JLabel.BOTTOM);
		
	}
}

