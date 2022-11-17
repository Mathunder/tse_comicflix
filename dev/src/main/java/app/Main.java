package app;

import java.util.ArrayList;
import java.util.List;

import app.dto.SearchResultDto;
import app.helpers.ComicVineSearchFilter;
import app.services.ComicVineService;

public class Main {
	public static void main(String[] args) {
		ComicVineService comicVine = new ComicVineService();
		List<ComicVineSearchFilter> filters = new ArrayList<>();
		filters.add(ComicVineSearchFilter.ISSUE);
//		filters.add(ComicVineSearchFilter.CHARACTER);
		SearchResultDto result = comicVine.search("batman", filters, 2, 0);
		result.getResults().stream().forEach(System.out::println);
	}

}
	