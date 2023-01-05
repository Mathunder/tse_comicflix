package app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.lang.Thread;

import app.helpers.ComicVineSearchFilter;
import app.helpers.ComicVineSearchStatus;
import junit.framework.TestCase;

public class TestComicVineService extends TestCase {

	public void testSearch() {
		//we will see if searchStatus is DONE
		
		//create the object which will test the method search()
		ComicVineService test = new ComicVineService();
		
		List<ComicVineSearchFilter> filters = new ArrayList<ComicVineSearchFilter>();
		filters.add(ComicVineSearchFilter.CHARACTER);
		
		test.initialSearch("batman");		
		
		//wait that the search function be changed into a non asynchronous method
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//create the object that is expected after performing the method search()
		ComicVineService expected = new ComicVineService();
		
		expected.setSearchStatus(ComicVineSearchStatus.DONE);
		
		
		
		//create an object that will be used for the search_url méthod
		
		ComicVineService search_url_object = new ComicVineService();
		
		String urlIssue = "https://comicvine.gamespot.com/api/issue/4000-926078/?api_key=f9073eee3658e2a4f39a9f531ad521b935ce87bc&format=json";

		String urlCharacter = "https://comicvine.gamespot.com/api/character/4005-1807/?api_key=f9073eee3658e2a4f39a9f531ad521b935ce87bc&format=json";
		
		
		
		
		
		
	//PERFORM THE TEST
		
		//on the searchStatus
		assertEquals(Objects.equals(expected.getSearchStatus(), test.getSearchStatus()),true);
		
		//on the method search_from_url()
		
		//verifying that the method search_from_url() returns a ResponseDto when we use the url for searching issues
		search_url_object.search_from_url(urlIssue);
		assertEquals(search_url_object.getInfosResult().getClass().toString(),"class app.dto.ResponseDto");
		
		//verifying that the method search_from_url() returns a ResponseDto when we use the url for searching characters
		search_url_object.search_from_url(urlCharacter);
		assertEquals(search_url_object.getInfosResult().getClass().toString(),"class app.dto.ResponseDto");
		
		
	}

}
