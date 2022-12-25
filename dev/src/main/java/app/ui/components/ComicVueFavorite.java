package app.ui.components;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import app.entities.Issue;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;

@SuppressWarnings("serial")
public class ComicVueFavorite extends ComicVue{

	private List<Issue> userFavoriteIssues = new ArrayList<>();
	
	public ComicVueFavorite(UserModel um, ComicVineService cvS,DatabaseService dbS){
		super(um, cvS, dbS);
		
	}
	
	@Override
	public void showResult() {
		removeComics();
		
		String read_status = "no_read";
		
		if(userFavoriteIssues != null) {
			
			for(int i=0;i<userFavoriteIssues.size();i++) {		
				//Find read button color
				for(int j=0;j<userModel.getUserReadingIssue().size();j++) {
					if(userModel.getUserReadingIssue().get(j).getId() == userFavoriteIssues.get(i).getId()) {
						read_status = "reading";
						break;
					}
				}
				if(read_status != "reading"){
					for(int j=0; j<userModel.getUserReadedIssues().size();j++) {
						if(userModel.getUserReadedIssues().get(j).getId() == userFavoriteIssues.get(i).getId()) {
							read_status = "readed";
							break;
						}
					}
				}
				ComicCoverPanel comicCover = new ComicCoverPanel(userFavoriteIssues.get(i), true, read_status, userModel.getUser(), databaseService);
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
		
		if(evt.getPropertyName() == "favoriteChange" || evt.getPropertyName() == "userChange") {
			if(evt.getNewValue() == "add")
				System.out.println("Add one new favorite");
			else if(evt.getNewValue() == "remove")
				System.out.println("Remove one favorite");
			
			userFavoriteIssues = userModel.getUserFavoriteIssues();
			showResult();
		}
		else if(evt.getPropertyName() == "readChange") {
			if(evt.getNewValue() == "add")
				System.out.println("Add new read (change state)");
			else if(evt.getNewValue() == "remove")
				System.out.println("remove read");
			showResult();
		}
	}

}
