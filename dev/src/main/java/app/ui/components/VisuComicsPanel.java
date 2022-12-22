package app.ui.components;


import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import app.dto.SearchResultDto;
import app.entities.Comics;
import app.entities.Issue;
import app.entities.User;
import app.services.DatabaseService;
import app.ui.themes.CustomColor;

public class VisuComicsPanel extends JPanel{
	
	private SearchResultDto result;
	private User user;
	private DatabaseService dataBase;
	private List<Issue> fav_user_issues;
	
	public VisuComicsPanel(){
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(CustomColor.WhiteCloud);
		this.setBounds(250, 150, 1350, 900);
		this.setLayout(new GridLayout(0,4,0,40));
		
		this.user = null;
		this.dataBase = new DatabaseService();
		this.fav_user_issues = new ArrayList<>();
	}
			
	public void removeComics() {
		this.removeAll();
	}
	
	public void refreshPanel() {
		this.revalidate();
		this.repaint();
	}
	
	public void showResult() {
		
		removeComics();
			
		if(result != null) {
			//result.getResults().stream().forEach(System.out::println);		
			
			if(this.user != null) {
				//Update user favorite list
				this.fav_user_issues = dataBase.getUserFavorites(user.getId());
				
				for(int i=0;i<result.getResults().size();i++) {
			
					boolean isFavorite = false;
					
					for(int j=0;j<fav_user_issues.size();j++) {
						if(fav_user_issues.get(j).getId() == result.getResults().get(i).getId()) {
							isFavorite = true;
							break;
						}
					}
					
					ComicCoverPanel comicCover = new ComicCoverPanel(result.getResults().get(i),isFavorite, this.user.getId());
					this.add(comicCover);
				}
			}
			else 
				System.out.println("User null");
			
		}
		else {
			System.out.println("Result null");
		}
				
		refreshPanel();
		
	}
	
	public void updateResults(SearchResultDto res) {
		this.result = res;
		showResult();
	}
	
	public void updateResults(List<Issue> res) {
		result = new SearchResultDto(res);
		showResult();
	}
	
	public void updateUser(User user) {
		this.user = user;
		showResult();
	}
	
}
