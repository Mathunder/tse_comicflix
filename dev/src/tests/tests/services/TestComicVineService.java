package tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


import app.helpers.ComicVineSearchFilter;
import app.helpers.ComicVineSearchStatus;
import app.services.ComicVineService;

class TestComicVineService {

	@Test
	void testSearch() {
		
		//creating the expected object
		ComicVineService expectedComicVineService = new ComicVineService();
		expectedComicVineService.setSearchStatus(ComicVineSearchStatus.DONE); //it is expected to be done after
		//passing through the search function
		
		
		
		
		
		//creating the object that we will test
		ComicVineService modelComicVineService = new ComicVineService();
	
		int page = 1;
		modelComicVineService.setKeyword("batman");
		
		List<ComicVineSearchFilter> filters = new ArrayList<ComicVineSearchFilter>();
		filters.add(ComicVineSearchFilter.CHARACTER);
		
		
		
		
		//call the method search
		System.out.println(modelComicVineService.getSearchStatus());
		modelComicVineService.search(modelComicVineService.getKeyword(), filters, modelComicVineService.getLimit(), page);
		System.out.println(modelComicVineService.getSearchStatus());
		
		
		
		//perform the test
			//we test the searchStatus that is expected to be done
			//the reference is expectedComicVineService
		assertEquals(expectedComicVineService.getSearchStatus().toString(),modelComicVineService.getSearchStatus().toString());
		
	}

}
