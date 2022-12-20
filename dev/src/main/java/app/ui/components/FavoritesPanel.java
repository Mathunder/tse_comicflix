package app.ui.components;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import app.entities.Comics;
import app.entities.Issue;
import app.entities.User;
import app.services.DatabaseService;
import app.ui.themes.CustomColor;

public class FavoritesPanel extends JPanel {
	private User user;
	private DatabaseService dataBase;
	private List<Issue> fav_issues;
	
	public FavoritesPanel(){
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(CustomColor.WhiteCloud);
		this.setBounds(250, 150, 1350, 900);
		this.setLayout(new GridLayout(0,4,0,40));
		
		this.user = null;
		this.dataBase = new DatabaseService();
		this.fav_issues = new ArrayList<>();
		
	}
	
	public void setUser(User user) {
		this.user = user;
		this.fav_issues = dataBase.getUserFavorites(user.getId());
		showFavorites();
	}
	
	public void displayComics(Comics comics) {
		//Add a comic at the right position
		LabelComics LabelComic = new LabelComics(comics);
		this.add(LabelComic);
	}
	
	public void removeComics() {
		this.removeAll();
	}
	public void refreshPanel() {
		this.revalidate();
		this.repaint();
	}
	public void showFavorites() {
		
		removeComics();
				
		for(int i=0;i<fav_issues.size();i++) {
			//Load a test image, resize and convert into an ImageIcon ______________________ TEST _______________________
			ImageIcon imageURL = null;

			try {
				URL url = new URL(fav_issues.get(i).getImage().getMedium_url());
				BufferedImage imageBrute = ImageIO.read(url);
				Image imageResize = imageBrute.getScaledInstance(206, 310, Image.SCALE_DEFAULT);
				imageURL = new ImageIcon(imageResize);
				

			} catch (IOException e) {
				System.out.println("Problem load img");
				e.printStackTrace();
			}
			
			Comics comics = new Comics(fav_issues.get(i).getName()+" "+i, imageURL);
			this.displayComics(comics);
		}
		
		refreshPanel();
		
	}
}
