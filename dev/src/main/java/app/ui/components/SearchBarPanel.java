package app.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import app.ui.themes.CustomColor;

public class SearchBarPanel extends JPanel {
	
	private SearchBar searchRoundBar;
	private JButton btnFilter;
	
	public SearchBarPanel(){
		searchRoundBar = new SearchBar();
		initButtonSearch();
		
		this.add(searchRoundBar);
		this.add(btnFilter);
		this.setLayout(new GridLayout(0,2));

	}
	
	//Init button search
	private void initButtonSearch() {
		btnFilter = new JButton("Search");
		btnFilter.setForeground(CustomColor.WhiteCloud);
		btnFilter.setBorderPainted(true);
		btnFilter.setFocusPainted(false);	
		btnFilter.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.darkGray));
		btnFilter.setFont(new Font("Candara", Font.BOLD, 20));
		btnFilter.setBackground(CustomColor.DarkGray);
		
		btnFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnFilterActionPerformed(evt);
            }
		});
	}
	
	//Action button search
	private void btnFilterActionPerformed(ActionEvent evt) {
		searchRoundBar.setSearchText("");
	}
	
}
