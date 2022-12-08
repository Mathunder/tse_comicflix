package app.ui.components;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

import app.ui.themes.CustomColor;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class SearchBar extends PanelRound {
	
	
	private JTextField searchTextField;
	
	//constructor
	public SearchBar(){
		
		this.setRoundTopRight(25);
		this.setRoundTopLeft(25);
		this.setRoundBottomRight(25);
		this.setRoundBottomLeft(25);
		this.setBounds(300,75,883,100);
		this.setBackground(CustomColor.LightGray);		
	
		Border emptyBorder = BorderFactory.createEmptyBorder();
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		searchTextField = new JTextField();
		searchTextField.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.NORTH, searchTextField, 5, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, searchTextField, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, searchTextField, -5, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, searchTextField, -10, SpringLayout.EAST, this);
		searchTextField.setBackground(CustomColor.LightGray);
		searchTextField.setForeground(CustomColor.DarkGray);
		searchTextField.setFont(new Font("Roboto", Font.PLAIN, 22));
		searchTextField.setSize(300, 85);
		searchTextField.setBorder(emptyBorder);
		
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