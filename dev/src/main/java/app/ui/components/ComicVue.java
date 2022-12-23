package app.ui.components;

import java.awt.GridLayout;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import app.dto.SearchResultDto;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;
import app.ui.themes.CustomColor;

public abstract class ComicVue extends JPanel implements PropertyChangeListener{
		
	//Model
	protected UserModel userModel;
	//Controller
	protected DatabaseService databaseService;
	protected ComicVineService comicsVineService;
	
	
	public ComicVue(UserModel um, ComicVineService cvS,DatabaseService dbS){
		
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
		refreshPanel();
	};
}
