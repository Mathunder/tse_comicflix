package app.ui.components;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.JPanel;

import app.dto.SearchResultDto;
import app.entities.Comics;
import app.helpers.ComicVineSearchFilter;
import app.helpers.ComicVineSearchStatus;
import app.services.ComicVineService;
import app.ui.frames.MainFrame;
import app.ui.themes.CustomColor;
import javax.swing.SpringLayout;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class SearchBarPanel extends JPanel implements PropertyChangeListener {
	private DefaultButton btnFilter;
	private SearchResultDto result;
	private SearchBar searchRoundBar;
	private ComicVineService comicVineService;

	public SearchBarPanel(ComicVineService comicVineService) {

		this.comicVineService = comicVineService;
		comicVineService.addPropertyChangeListener(this);
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
		searchRoundBar.searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				
				if (searchRoundBar.getSearchText().trim().equals("") || searchRoundBar.getSearchText().trim().equals("Search")
						|| searchRoundBar.getSearchText().trim().equals("Loading...")) {
					btnFilter.setEnabled(false);
				} else {
					btnFilter.setEnabled(true);
				}
			}
		});
		searchRoundBar.searchTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				searchRoundBar.searchTextField.setText("");
				btnFilter.setEnabled(false);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, searchRoundBar, 25, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, searchRoundBar, 400, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, searchRoundBar, -50, SpringLayout.SOUTH, btnFilter);
		springLayout.putConstraint(SpringLayout.EAST, searchRoundBar, -400, SpringLayout.EAST, this);
		add(searchRoundBar);
		FilterComBox filterCombox = new FilterComBox(comicVineService);
		DefaultButton btnFilter1 = new DefaultButton(" Search ", CustomColor.Red, 20, true);
		springLayout.putConstraint(SpringLayout.NORTH, filterCombox, 25, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, filterCombox, 625, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, filterCombox, -70, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, filterCombox, -50, SpringLayout.EAST, this);
		add(filterCombox);
	}

	// Init button search
	private void initButtonSearch() {
		btnFilter = new DefaultButton(" Search ", CustomColor.Red, 20, true);
		btnFilter.setText("Go");

		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnFilterActionPerformed(evt);
			}
		});
	}

	// Action button search
	private void btnFilterActionPerformed(ActionEvent evt) {

		if (searchRoundBar.getSearchText() != "" && searchRoundBar.getSearchText() != "Search"
				&& searchRoundBar.getSearchText() != "Loading...") {

			List<ComicVineSearchFilter> filters = new ArrayList<>();
//			filters.add(ComicVineSearchFilter.ISSUE);
			filters.add(ComicVineSearchFilter.CHARACTER);
			String keyword = searchRoundBar.getSearchText().replaceAll(" ", "-");
			this.comicVineService.search(keyword, filters, this.comicVineService.getLimit(), 0);
		}

	}

	public SearchResultDto getResult() {
		return result;
	}

	public void setResult(SearchResultDto result) {
		this.result = result;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "searchStatus") {
			if (evt.getNewValue() == ComicVineSearchStatus.FETCHING) {
				this.searchRoundBar.setSearchText("Loading...");
				this.btnFilter.setEnabled(false);
			} else if (evt.getNewValue() == ComicVineSearchStatus.DONE) {
				this.searchRoundBar.setSearchText("Search");
				this.btnFilter.setEnabled(true);
			}
		}
	}

}
