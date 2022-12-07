package app.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
	private JButton btnFilter;
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

		if(searchRoundBar.getSearchText()!="") {
			ComicVineService comicVineService = new ComicVineService();
			List<ComicVineSearchFilter> filters = new ArrayList<>();
			//filters.add(ComicVineSearchFilter.ISSUE);
	        filters.add(ComicVineSearchFilter.CHARACTER);
			String keyword = searchRoundBar.getSearchText();
			CompletableFuture.runAsync(() -> {
				System.out.println();
				SearchResultDto AsyncResults = comicVineService.search(keyword, filters, 16, 0);
				MainFrame.visuComics.showResult(AsyncResults);
				AsyncResults.getResults().stream().forEach(System.out::println);
				searchRoundBar.setSearchText("Search");


			});
			
			searchRoundBar.setSearchText("Loading...");

		}
	}

	public SearchResultDto getResult() {
		return result;
	}

	public void setResult(SearchResultDto result) {
		this.result = result;
	}
	
	
	
}
