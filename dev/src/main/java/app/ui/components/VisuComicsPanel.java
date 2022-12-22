package app.ui.components;


import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import app.helpers.ComicVineSearchStatus;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;
import app.ui.themes.CustomColor;

public class VisuComicsPanel extends JPanel implements PropertyChangeListener{
	
	private SearchResultDto result;
	
	//Model
	protected UserModel userModel;
	//Controller
	protected DatabaseService databaseService;
	protected ComicVineService comicsVineService;
	
	public VisuComicsPanel(UserModel um, ComicVineService cvS,DatabaseService dbS){
		
		this.userModel = um;
		this.comicsVineService = cvS;
		this.databaseService = dbS;
		
		//Add listeners 
		this.userModel.addPropertyChangeListener(this);
		this.comicsVineService.addPropertyChangeListener(this);
		
		initComponent();
	}
	
	private void initComponent() {
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(CustomColor.WhiteCloud);
		this.setBounds(250, 150, 1350, 900);
		this.setLayout(new GridLayout(0,4,0,40));
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
	}
	
}
