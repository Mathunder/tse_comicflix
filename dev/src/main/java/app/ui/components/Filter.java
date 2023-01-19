package app.ui.components;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.JCheckBox;
import app.helpers.ComicVineSearchFilter;
import app.services.ComicVineService;
import app.ui.themes.CustomColor;

@SuppressWarnings("serial")
public class Filter extends JPanel {
	boolean checkIssue = true;
	boolean checkCharacter = false; 
	public Filter(ComicVineService comicVineService) {
		JCheckBox checkboxIssue  = new JCheckBox("Numéro");
		this.setOpaque(false);
		JCheckBox checkboxCharacter  = new JCheckBox("Personnage");
		checkboxIssue.setBounds(40,0,10,50);
		checkboxIssue.setForeground(CustomColor.Red);
		checkboxCharacter.setOpaque(false);
		checkboxIssue.setOpaque(false);
		checkboxIssue.setSelected(true);
		checkboxCharacter.setForeground(CustomColor.Red);
		checkboxIssue.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				comicVineService.updateFilter(ComicVineSearchFilter.ISSUE,e.getStateChange());
				checkboxIssue.setFocusable(false);
			
			}
		});
		checkboxCharacter.setBounds(40,60,10,50);
		checkboxCharacter.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				comicVineService.updateFilter(ComicVineSearchFilter.CHARACTER,e.getStateChange());
				checkboxCharacter.setFocusable(false);

			}
		});
		this.add(checkboxIssue);
		this.add(checkboxCharacter);

	}

}
