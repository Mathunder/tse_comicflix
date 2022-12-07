package app.services;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dto.SearchResultDto;
import app.helpers.ComicVineSearchFilter;
import io.restassured.RestAssured;

/**
 * A class to handle sending requests to Comicvine API
 * 
 * @author RedRosh
 *
 */
public class ComicVineService {

	public ComicVineService() {
		RestAssured.baseURI = "https://comicvine.gamespot.com/api";
		RestAssured.port = 443;
	}
	
	public SearchResultDto  search(String keyword, List<ComicVineSearchFilter> filters, int limit, int offset) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("api_key", "f9073eee3658e2a4f39a9f531ad521b935ce87bc");
		params.put("resources",
				String.join(",", filters.stream().map(comicVineFilter -> comicVineFilter.getFilterValue()).toList()));
		params.put("format", "json");
		params.put("query", keyword);
		params.put("limit", Integer.toString(limit));
		params.put("offset", Integer.toString(offset));
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";
		SearchResultDto result = given().params(params).header("User-Agent", userAgent).expect().statusCode(200)
				.body("status_code", equalTo(1)).when().get("/search").as(SearchResultDto.class);
		return (result);
	}

}
