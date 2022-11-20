package app.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SearchBar extends JTextField {
	
	//constructor
	SearchBar(){
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		//JTextField search = new JTextField();
        this.setText("Search Your Book");
        this.setForeground(new Color(38, 47, 79));
        this.setFont(new Font("Roboto", Font.PLAIN, 22));
        this.setBounds(350, 85, 550, 21);
        this.setBorder(emptyBorder);

		
	}
	
	
	//event handler
	public void mouseClicked(MouseEvent e){
        this.setText("");
    }
	
	
	public static void main(String[] args) {
		
		
		//instancier un JPanel
		JPanel myPanel = new JPanel();
		myPanel.setBounds(250, 0, 1350, 150);
		myPanel.setBackground(Color.red);
		
		//instancier un textField
		SearchBar mySearchBar = new SearchBar();
		mySearchBar.mouseClicked(null);
        
		
		//continuer l'instanciation du Panel
		myPanel.setLayout(null);
		//add the text field to the panel
		myPanel.add(mySearchBar);
		
		
		
		
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