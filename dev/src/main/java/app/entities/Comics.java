package app.entities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import app.ui.components.ComicsInfosPanel;
import app.ui.frames.MainFrame;

// The comic class has two objectives : 
// 1. To provide names & pictures for the comics that are to be displayed when a research is made by the user
// 2. To provide name, picture, synopsis and all the informations that are to be displayed when the user clicks on a comic

public class Comics {
	private String title;
	private ImageIcon image;
	private String synopsis;
	private List<String> creators;
	private List<String> characters;
	
	//Constructor 
	public Comics(Issue issue) {
		// Initialization of the comic's name from issue.getName()
		this.title = issue.getName();
		
		// Initialization of the comic's image from issue.getImage()
		try {
			URL url = new URL(issue.getImage().getOriginal_url());
			BufferedImage imageBrute = ImageIO.read(url);
			Image imageResize = imageBrute.getScaledInstance(206, 310, Image.SCALE_DEFAULT);
			this.image = new ImageIcon(imageResize);
			

		} catch (IOException e) {
			System.out.println("Problem load img");
			e.printStackTrace();
		}
		
		// Initialization of the description
		this.synopsis = issue.getDescription();
		
//		// Initialization of the creators
//		for (int i = 0; i < issue.getPerson_credits().size(); i++) {
//			this.creators.add("Name : " + issue.getPerson_credits().get(i).getName());
//			this.creators.add("Role : " + issue.getPerson_credits().get(i).getRole());
//		}
//		
//		for (int i = 0; i < issue.getCharacter_credits().size(); i++) {
//			this.creators.add("Name : " + issue.getCharacter_credits().get(i).getName());
//			// if image
//			// this.creators.add("Role : " + issue.getCharacter_credits().get(i).getImage());
//			// or if the image is a url, do like above
//		}
	}

	public String getTitle() {
		return title;
	}

	public ImageIcon getImage() {
		return this.image;
	}
	
	public String getSynopsis() {
		return this.synopsis;
	}
	
	public List<String> getCreators() {
		return this.creators;
	}
	
	public List<String> getCharacters() {
		return this.characters;
	}
	
	public void displayInfos() {
		MainFrame.visuComicInfos.updateComicsInfosPanel(this);
	}
}
