package app.ui.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SearchBar extends JPanel {
	
	//constructor
	SearchBar(){
		
		//this.setBackground(Color.red);
		this.setBounds(250, 0, 1350, 150);
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		JTextField search = new JTextField();
        search.setText("Search Your Book");
        search.setForeground(new Color(38, 47, 79));
        search.setFont(new Font("Roboto", Font.PLAIN, 22));
        search.setBounds(134, 85, 550, 21);
        search.setBorder(emptyBorder);

		
        this.setLayout(null);
        
		this.add(search);
		
	}
	
	
	public static void main(String[] args) {
		
		//rajouter des panneaux
		SearchBar mySearchBar = new SearchBar();
		
		
		
		
		//instancier une fenêtre
		JFrame myWindow = new JFrame();
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myWindow.setLayout(null); //that line allows to have panels which have not the size of
		//the window
		myWindow.setSize(1400, 900);		
		myWindow.setVisible(true);
		
		
	
		
		myWindow.add(mySearchBar);
		
		
		
		
		
				
		
	}

}