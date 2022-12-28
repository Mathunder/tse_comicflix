package app.ui.components;

import java.awt.GridLayout;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

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
	//Vue
	protected List<ComicCoverPanel> ComicCoverPanels = new ArrayList<>();
	
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
		ComicCoverPanels.clear();
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
