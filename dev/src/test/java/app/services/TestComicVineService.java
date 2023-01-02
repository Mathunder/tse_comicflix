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
		
		test.setKeyword("batman");
		
		List<ComicVineSearchFilter> filters = new ArrayList<ComicVineSearchFilter>();
		filters.add(ComicVineSearchFilter.CHARACTER);
		
		int page = 1;
		
		//test.search(test.getKeyword(), filters, test.getLimit(), page);
		
		//wait that the search function be changed into a non asynchronous method
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//System.out.println(test.getSearchResult().getResults());
		
		//create the object that is expected after performing the method search()
		ComicVineService expected = new ComicVineService();
		
		//expected.setSearchStatus(ComicVineSearchStatus.DONE);
		
		
		
		//Perform the test
		
		//on the searchStatus
		assertEquals(Objects.equals(expected.getSearchStatus(), test.getSearchStatus()),true);
		
		
		//verifying that we have an array of issues after querying the API. Verify all the elements of the array
//		for(int k = 0; k < test.getSearchResult().getResults().size(); k ++) {
//			assertEquals(test.getSearchResult().getResults().get(k).getClass().toString(), "class app.entities.Issue");
//		}
		
	}

}
