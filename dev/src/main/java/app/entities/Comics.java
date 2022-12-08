package app.entities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

// The comic class has two objectives : 
// 1. To provide names & pictures for the comics that are to be displayed when a research is made by the user
// 2. To provide name, picture, synopsis and all the informations that are to be displayed when the user clicks on a comic

public class Comics {
	private String title;
	private ImageIcon image;
	private String synopsis;
	
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
}
