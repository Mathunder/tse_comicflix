package app.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import app.entities.Comics;
import app.ui.themes.CustomColor;

public class VisuComicsPanel extends JPanel{
	
	public VisuComicsPanel(){

		this.setBackground(CustomColor.WhiteCloud);
		this.setBounds(250, 150, 1350, 900);
		this.setLayout(new GridLayout(0,4,0,40));
	}
	
	public void displayComics(Comics comics, int n) {
		//Add a comic at the right position
		LabelComics LabelComic = new LabelComics(comics, n);
		this.add(LabelComic);
	}
}
