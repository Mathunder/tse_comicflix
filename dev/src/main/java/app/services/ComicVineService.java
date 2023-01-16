package app.services;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import app.dto.ResponseDto;
import app.dto.ResultDto;
import app.dto.SearchResultDto;
import app.entities.Issue;
import app.entities.VineCharacter;
import app.helpers.ComicVineSearchFilter;
import app.helpers.ComicVineSearchStatus;
import io.restassured.RestAssured;
import lombok.Data;

/**
 * A class to handle sending requests to ComicVine API
 * 
 * @author RedRosh
 *
 */

@Data
public class ComicVineService {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private int limit = 8;
	private int totalNumberOfPages = 1;
	private String keyword;
	private ComicVineSearchStatus searchStatus = ComicVineSearchStatus.IDLE;
	private int currentPage = 1;
	private List<ComicVineSearchFilter> filters;
	private List<ResultDto> issueResults;
	private List<ResultDto> characterResults;
	private ResponseDto infosResult;

	public ComicVineService() {
		this.issueResults = new ArrayList<>();
		this.characterResults = new ArrayList<>();
		this.filters = new ArrayList<>();
		filters.add(ComicVineSearchFilter.ISSUE);

		RestAssured.baseURI = "https://comicvine.gamespot.com/api";
		RestAssured.port = 443;
	}

	/**
	 * this function will fire a keywordChanged event whenever we change the keyword
	 * if the keyword changes, we need to set the currentPage variable to 1
	 * 
	 * @param keyword
	 */
	public void setKeyword(String keyword) {
		String oldKeyword = this.keyword;
		this.keyword = keyword;
		this.pcs.firePropertyChange("keywordChanged", oldKeyword, keyword);
	}

	private void search() {
		this.clearSearchResults();
		CompletableFuture.runAsync(() -> {

			Map<String, String> params = new HashMap<String, String>();
			params.put("api_key", "f9073eee3658e2a4f39a9f531ad521b935ce87bc");
			params.put("resources", String.join(",",
					filters.stream().map(comicVineFilter -> comicVineFilter.getFilterValue()).toList()));
			params.put("format", "json");
			params.put("query", keyword);
			params.put("limit", Integer.toString(limit));
			params.put("page", Integer.toString(this.currentPage));
			String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";
			this.setSearchStatus(ComicVineSearchStatus.FETCHING);

			filters.stream().forEach(filter -> {
				params.put("resources", filter.getFilterValue());
				SearchResultDto searchResult = given().params(params).header("User-Agent", userAgent).expect()
						.statusCode(200).body("status_code", equalTo(1)).when().get("/search")
						.as(SearchResultDto.class);

				processSearchResults(searchResult);
			});

			this.setSearchStatus(ComicVineSearchStatus.DONE);

		});
	}

	public void processSearchResults(SearchResultDto searchResults) {
		int newTotalNumberOfPages = (int) Math.ceil(searchResults.getNumber_of_total_results()) / this.limit > 1000
				? 1000
				: (int) Math.ceil(searchResults.getNumber_of_total_results() / this.limit);
		this.setTotalNumberOfPages((int) Math.max(totalNumberOfPages, newTotalNumberOfPages));

		// Fill Lists with the new results
		searchResults.getResults().forEach(result -> {
			switch (result.getResource_type()) {
			case "issue": {
				this.issueResults.add(result);
				break;
			}
			case "character": {

				this.characterResults.add(result);
				break;
			}
			}
		});

		this.pcs.firePropertyChange("searchResultsChanged", true, true);

	}

	public void setTotalNumberOfPages(int totalNumberOfResults) {
		int oldTotalNumberOfPages = this.totalNumberOfPages;
		this.totalNumberOfPages = totalNumberOfResults;
		this.pcs.firePropertyChange("totalNumberOfPagesChanged", oldTotalNumberOfPages, totalNumberOfPages);

	}

	public void setCurrentPage(int currentPage) {
		int oldCurrentPage = this.currentPage;
		this.currentPage = currentPage;
		this.pcs.firePropertyChange("currentPageChanged", oldCurrentPage, currentPage);

	}

	public void initialSearch(String keyword) {
		this.setKeyword(keyword);
		this.setCurrentPage(1);
		this.setTotalNumberOfPages(1);

		CompletableFuture.runAsync(() -> {
			this.search();
		});
	}

	public void nextSearch() {

		this.setCurrentPage(currentPage + 1);
		CompletableFuture.runAsync(() -> {
			this.search();
		});
	}

	public void previousSearch() {
		this.setCurrentPage(currentPage - 1);
		CompletableFuture.runAsync(() -> {
			this.search();
		});
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void setSearchStatus(ComicVineSearchStatus status) {
		ComicVineSearchStatus oldSearchStatus = this.getSearchStatus();
		this.searchStatus = status;
		this.pcs.firePropertyChange("searchStatus", oldSearchStatus, this.getSearchStatus());

	}

	public void clearSearchResults() {
		this.characterResults.clear();
		this.issueResults.clear();
		this.pcs.firePropertyChange("clearSearchResults", true, true);

	}

	public void updateFilter(ComicVineSearchFilter filter, int state) {

		if (state == 2) {
			this.filters.remove(filter);
		} else {
			if (!this.filters.contains(filter)) {
				this.filters.add(filter);
			}
		}
		
		this.pcs.firePropertyChange("updateFilter", null, filters.size());
	}

	/*
	 * This method IS NOT asynchronous since it is computed very quickly, hence the
	 * asynchrony is not needed.
	 */
	public void search_from_url(String url) {

		// The base url is changed for the url we want
		RestAssured.baseURI = url;

		// The parameters are fewer since the url targets perfectly the result we want.
		// Only the "api_key" and "format" remain.
		Map<String, String> params = new HashMap<String, String>();
		params.put("api_key", "f9073eee3658e2a4f39a9f531ad521b935ce87bc");
		params.put("format", "json");
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";

		/*
		 * The part that makes the request; the verifications were removed since there
		 * is no possible error (if the api gives an url, then we suppose that this url
		 * points towards something that exists).
		 */
		this.infosResult = given().params(params).header("User-Agent", userAgent).when().get().as(ResponseDto.class);

		// Since the baseURI has been modified, it is put back to its original value at
		// the end of this method.
		RestAssured.baseURI = "https://comicvine.gamespot.com/api";
	}

}
