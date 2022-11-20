package app.ui.components;

import java.awt.Color;


import javax.swing.JFrame;
import javax.swing.JPanel;

public class SearchBarPanel extends JPanel {
	
	SearchBarPanel(){
		this.setBounds(250, 0, 1350, 150);
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
	}
	
	
	public void displaySearchBar() {
		//instanciate the SearchBar
		SearchBar mySearchBar = new SearchBar();
		
		
		//add the search bar to this Panel
		this.add(mySearchBar);
	}
	
	public static void main(String[] args) {
		
		
		SearchBarPanel myPanel = new SearchBarPanel();
		
		//instancier une fenêtre
		JFrame myWindow = new JFrame();
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myWindow.setLayout(null); //that line allows to have panels which have not the size of
		//the window
		myWindow.setSize(1400, 900);		
		myWindow.setVisible(true);
		
				
		myWindow.add(myPanel);
	}
}
