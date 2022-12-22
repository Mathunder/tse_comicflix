package app.ui.components;

import java.beans.PropertyChangeEvent;

import app.dto.SearchResultDto;
import app.helpers.ComicVineSearchStatus;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;

public class ComicVueSearch extends ComicVue{

	private SearchResultDto result;
	
	public ComicVueSearch(UserModel um, ComicVineService cvS,DatabaseService dbS){
		super(um, cvS, dbS);
	}
	
	@Override
	public void showResult() {
		removeComics();
		
		if(result != null) {
			
			boolean isFavorite = false;
			
			for(int i=0;i<result.getResults().size();i++) {	
				
				isFavorite = false;
				// IF AUTHENTICATED USER
				if(userModel.getIsAuthenticated()) {
					//Update favorite button color			
						for(int j=0;j<userModel.getUserFavoriteIssues().size();j++) {
							if(userModel.getUserFavoriteIssues().get(j).getId() == result.getResults().get(i).getId()) {
								isFavorite = true;
								break;
							}
						}
				}
			
				ComicCoverPanel comicCover = new ComicCoverPanel(result.getResults().get(i),isFavorite, userModel.getUser(), databaseService);
				this.add(comicCover);
			}
		}
		else {
			System.out.println("Result null");
		}
		
		refreshPanel();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if(evt.getPropertyName() == "searchStatus") //From Controller/Model ComicVineService
		{
			if(evt.getNewValue() == ComicVineSearchStatus.FETCHING) {
				System.out.println("Loading");
			}
			else if(evt.getNewValue() == ComicVineSearchStatus.DONE) {
				System.out.println("Loaded");
				result = comicsVineService.getSearchResult();
				showResult();
			}
		}
		else if(evt.getPropertyName() == "userChange") {
			showResult();
		}
		else if(evt.getPropertyName() == "favoriteChange") {
			if(evt.getNewValue() == "add")
				System.out.println("Add one new favorite");
			else if(evt.getNewValue() == "remove")
				System.out.println("Remove one favorite");
			showResult();
		}
	}

}
