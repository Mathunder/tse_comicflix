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

import app.dto.SearchResultDto;
import app.helpers.ComicVineSearchFilter;
import app.helpers.ComicVineSearchStatus;
import io.restassured.RestAssured;
import lombok.Data;

/**
 * A class to handle sending requests to Comicvine API
 * 
 * @author RedRosh
 *
 */
@Data
public class ComicVineService {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private int offset = 0;
	private int limit = 16;
	private int totalResults = 1;
	private String keyword;
	private ComicVineSearchStatus searchStatus = ComicVineSearchStatus.IDLE;
	private SearchResultDto searchResult;

	public ComicVineService() {
		RestAssured.baseURI = "https://comicvine.gamespot.com/api";
		RestAssured.port = 443;
	}

	public void search(String keyword, List<ComicVineSearchFilter> filters, int limit, int page) {
		this.keyword = keyword;
		this.offset = page==1?  0 :  page* this.getLimit();
		
		CompletableFuture.runAsync(() -> {

			Map<String, String> params = new HashMap<String, String>();
			params.put("api_key", "f9073eee3658e2a4f39a9f531ad521b935ce87bc");
			params.put("resources", String.join(",",
					filters.stream().map(comicVineFilter -> comicVineFilter.getFilterValue()).toList()));
			params.put("format", "json");
			params.put("query", keyword);
			params.put("limit", Integer.toString(limit));
			params.put("page", Integer.toString(page));
			String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";

	
			ComicVineSearchStatus oldSearchStatus = this.getSearchStatus();
			this.setSearchStatus(ComicVineSearchStatus.FETCHING);
			this.pcs.firePropertyChange("searchStatus", oldSearchStatus, this.getSearchStatus());

			SearchResultDto prevSearchResult = this.getSearchResult();
			this.searchResult = given().params(params).header("User-Agent", userAgent).expect().statusCode(200)
					.body("status_code", equalTo(1)).when().get("/search").as(SearchResultDto.class);
			this.pcs.firePropertyChange("searchResults", prevSearchResult, searchResult);

			System.out.println("passed there");
			totalResults = this.searchResult.getNumber_of_total_results();
			oldSearchStatus = this.getSearchStatus();
			this.setSearchStatus(ComicVineSearchStatus.DONE);
			this.pcs.firePropertyChange("searchStatus", oldSearchStatus, this.getSearchStatus());
			System.out.println(this.getSearchResult());

		});
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	

}
