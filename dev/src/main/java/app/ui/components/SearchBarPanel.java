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
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;

public class SearchBarPanel extends JPanel {
	private DefaultButton btnFilter;
	private SearchResultDto result;
	private SearchBar searchRoundBar;
	
	public SearchBarPanel(){
		setBackground(new Color(249, 246, 241));
		initButtonSearch();
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, btnFilter, 85, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnFilter, 550, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnFilter, -25, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnFilter, -550, SpringLayout.EAST, this);
		setLayout(springLayout);
		this.add(btnFilter);
		
		searchRoundBar = new SearchBar();
		searchRoundBar.setSearchText("Search");
		springLayout.putConstraint(SpringLayout.NORTH, searchRoundBar, 25, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, searchRoundBar, 400, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, searchRoundBar, -50, SpringLayout.SOUTH, btnFilter);
		springLayout.putConstraint(SpringLayout.EAST, searchRoundBar, -400, SpringLayout.EAST, this);
		add(searchRoundBar);

	}
	
	//Init button search
	private void initButtonSearch() {
		btnFilter = new DefaultButton(" Search ", CustomColor.Red, 20, true);
		btnFilter.setText("Go");
			
		btnFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnFilterActionPerformed(evt);
            }
		});
	}
	
	//Action button search
	private void btnFilterActionPerformed(ActionEvent evt) {

		if(searchRoundBar.getSearchText()!="" && searchRoundBar.getSearchText() != "Search" && searchRoundBar.getSearchText() != "Loading...") {
			ComicVineService comicVineService = new ComicVineService();
			List<ComicVineSearchFilter> filters = new ArrayList<>();
			//filters.add(ComicVineSearchFilter.ISSUE);
	        filters.add(ComicVineSearchFilter.CHARACTER);
			String keyword = searchRoundBar.getSearchText().replaceAll(" ", "-");
			System.out.println(keyword);
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
