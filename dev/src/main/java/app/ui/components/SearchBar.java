package app.ui.components;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

import app.ui.themes.CustomColor;

public class SearchBar extends PanelRound {
	
	
	private JTextField searchTextField;
	
	//constructor
	public SearchBar(){
		
		this.setRoundTopRight(25);
		this.setRoundTopLeft(25);
		this.setRoundBottomRight(25);
		this.setRoundBottomLeft(25);
		this.setLayout(new FlowLayout());
		this.setBounds(300,75,100,30);
		this.setBackground(CustomColor.LightGray);		
	
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		searchTextField = new JTextField();
		searchTextField.setBackground(CustomColor.LightGray);
		searchTextField.setForeground(CustomColor.DarkGray);
		searchTextField.setFont(new Font("Roboto", Font.PLAIN, 22));
		searchTextField.setSize(300, 85);
		searchTextField.setBorder(emptyBorder);
		searchTextField.setText("Search book ...");
		
		this.add(searchTextField);
        
		searchTextField.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mouseClicked(MouseEvent e) {
				  searchTextField.setText("");
			  }
			});
		
	}
	
	public String getSearchText() {
		return searchTextField.getText();
	}
	
	public void setSearchText(String txt) {
		searchTextField.setText(txt);
	}
	
}