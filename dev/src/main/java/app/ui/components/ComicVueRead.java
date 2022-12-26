package app.ui.components;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import app.entities.Issue;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;

public class ComicVueRead extends ComicVue{
	
	private List<Issue> userReadingIssues = new ArrayList<>();
	private List<Issue> userReadedIssues = new ArrayList<>();
	
	public ComicVueRead(UserModel um, ComicVineService cvS,DatabaseService dbS){
		super(um, cvS, dbS);
	}
	
	@Override
	public void showResult() {
		removeComics();
		
		String read_status = "no_read";
		
		if(userReadingIssues != null) {	
			read_status="reading";
			for(int i=0;i<userReadingIssues.size();i++) {		
				ComicCoverPanel comicCover = new ComicCoverPanel(userReadingIssues.get(i), true, read_status, userModel.getUser(), databaseService);
				this.add(comicCover);
			}
		}
		if (userReadedIssues != null) {
			read_status="readed";
			for(int i=0;i<userReadedIssues.size();i++) {
				ComicCoverPanel comicCover = new ComicCoverPanel(userReadedIssues.get(i), true, read_status, userModel.getUser(), databaseService);
				this.add(comicCover);
			}
		}
		
		refreshPanel();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if(evt.getPropertyName() == "readChange" || evt.getPropertyName() == "userChange") {
			if(evt.getNewValue() == "add")
				System.out.println("Add one new read");
			else if(evt.getNewValue() == "remove")
				System.out.println("Remove one read");
			
			userReadingIssues = userModel.getUserReadingIssue();
			userReadedIssues = userModel.getUserReadedIssues();
			showResult();
		}
	}
}
