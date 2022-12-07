package app.ui.components;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.JPanel;

import app.dto.SearchResultDto;
import app.helpers.ComicVineSearchFilter;
import app.helpers.ComicVineSearchStatus;
import app.services.ComicVineService;
import app.ui.frames.MainFrame;
import app.ui.themes.CustomColor;

@SuppressWarnings("serial")
public class SearchBarPanel extends JPanel implements PropertyChangeListener {
	private ComicVineService comicVineService;
	private SearchBar searchRoundBar;
	private DefaultButton btnFilter;
	private SearchResultDto result;

	public SearchBarPanel(ComicVineService comicVineService) {
		this.comicVineService = comicVineService;
		comicVineService.addPropertyChangeListener(this);
		searchRoundBar = new SearchBar();
		initButtonSearch();
		this.add(searchRoundBar);
		this.add(btnFilter);
		this.setLayout(new GridLayout(0, 2));

	}

	// Init button search
	private void initButtonSearch() {
		btnFilter = new DefaultButton(" Search ", CustomColor.Red, 20, true);

		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnFilterActionPerformed(evt);
			}
		});
	}

	// Action button search
	private void btnFilterActionPerformed(ActionEvent evt) {

		if (searchRoundBar.getSearchText() != "" && searchRoundBar.getSearchText() != "Search book ...        ") {

			List<ComicVineSearchFilter> filters = new ArrayList<>();
			// filters.add(ComicVineSearchFilter.ISSUE);
			filters.add(ComicVineSearchFilter.CHARACTER);
			String keyword = searchRoundBar.getSearchText();
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

				this.searchRoundBar.setSearchText("Loading...        ");
			} else if (evt.getNewValue() == ComicVineSearchStatus.DONE) {
				MainFrame.visuComics.showResult(this.comicVineService.getSearchResult());

				this.searchRoundBar.setSearchText("Search book ...        ");
			}

		}

	}

}
