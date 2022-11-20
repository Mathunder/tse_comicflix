package app.ui.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import app.entities.Comics;

public class LabelComics extends JLabel{
	
	LabelComics(Comics comics, int n){
		
		int line = n/4;
		int row = n%4;
//		System.out.println(row);
//		System.out.println(line);
		
		String title = comics.title;
		ImageIcon image = comics.image;
		
		
		this.setText(title);
		this.setIcon(image);
		this.setHorizontalTextPosition(JLabel.CENTER); //set text L, R, CENTER of imageIcon
		this.setVerticalTextPosition(JLabel.BOTTOM);
		this.setForeground(Color.black); //set font color of text
		this.setFont(new Font("Arial", Font.PLAIN, 20)); //set font of text
		this.setIconTextGap(0); //set distance text - img , can be negative
		this.setBackground(new Color(0xDDDDDD));
		this.setOpaque(true); //display background color 
		this.setVerticalAlignment(JLabel.CENTER);  //set position of icon+text
		this.setHorizontalAlignment(JLabel.CENTER);

		this.setBounds(113+ 306*row, 10+390*line, 206, 340); //set coordinates and size 

	}

}

