package app.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import app.dto.SearchResultDto;
import app.helpers.ComicVineSearchFilter;
import app.helpers.ComicVineSearchStatus;
import app.services.ComicVineService;
import app.ui.frames.MainFrame;
import app.ui.themes.CustomColor;
import javax.swing.SpringLayout;
import java.awt.Color;

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
		springLayout.putConstraint(SpringLayout.WEST, btnFilter, 350, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnFilter, -25, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnFilter, -350, SpringLayout.EAST, this);
		setLayout(springLayout);
		this.add(btnFilter);

		searchRoundBar = new SearchBar();
		searchRoundBar.setSearchText("Search");
		springLayout.putConstraint(SpringLayout.NORTH, searchRoundBar, 25, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, searchRoundBar, 250, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, searchRoundBar, -50, SpringLayout.SOUTH, btnFilter);
		springLayout.putConstraint(SpringLayout.EAST, searchRoundBar, -250, SpringLayout.EAST, this);
		add(searchRoundBar);
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
			// filters.add(ComicVineSearchFilter.ISSUE);
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
			} else if (evt.getNewValue() == ComicVineSearchStatus.DONE) {
				MainFrame.visuComics.showResult(this.comicVineService.getSearchResult());

				this.searchRoundBar.setSearchText("Search book ...");
			}

		}

	}

}
