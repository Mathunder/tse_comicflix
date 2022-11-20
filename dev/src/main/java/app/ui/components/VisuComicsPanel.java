package app.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import app.entities.Comics;

public class VisuComicsPanel extends JPanel{
	
	public VisuComicsPanel(){

		this.setBackground(Color.LIGHT_GRAY);
//		this.setBounds(250, 150, 1350, 750);
//		this.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
		this.setLayout(null);
		this.setPreferredSize(new Dimension(1350, 3000));


	}
	
	
	public void displayComics(Comics comics, int n) {
		//Add a comic at the right position
		LabelComics LabelComic = new LabelComics(comics, n);
		this.add(LabelComic);
	}
}
