package app.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import app.dto.SearchResultDto;
import app.helpers.ComicVineSearchFilter;
import app.services.ComicVineService;
import app.ui.frames.MainFrame;
import app.ui.themes.CustomColor;

public class SearchBarPanel extends JPanel {
	
	private SearchBar searchRoundBar;
	private DefaultButton btnFilter;
	private SearchResultDto result;
	
	public SearchBarPanel(){
		searchRoundBar = new SearchBar();
		initButtonSearch();
		
		this.add(searchRoundBar);
		this.add(btnFilter);
		this.setLayout(new GridLayout(0,2));

	}
	
	//Init button search
	private void initButtonSearch() {
		btnFilter = new DefaultButton(" Search ", CustomColor.Red, 20, true);
			
		btnFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnFilterActionPerformed(evt);
            }
		});
	}
	
	//Action button search
	private void btnFilterActionPerformed(ActionEvent evt) {

		if(searchRoundBar.getSearchText()!="") {
			ComicVineService comicVine = new ComicVineService();
			List<ComicVineSearchFilter> filters = new ArrayList<>();
			//filters.add(ComicVineSearchFilter.ISSUE);
	        filters.add(ComicVineSearchFilter.CHARACTER);
			result = comicVine.search(searchRoundBar.getSearchText(), filters, 16, 0);
				
			MainFrame.visuComics.showResult(result);
			
			searchRoundBar.setSearchText("Search book ...        ");

		}
	}

	public SearchResultDto getResult() {
		return result;
	}

	public void setResult(SearchResultDto result) {
		this.result = result;
	}
	
	
	
}
