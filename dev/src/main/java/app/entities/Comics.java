package app.entities;

import javax.swing.ImageIcon;

public class Comics {
	private String title;
	private ImageIcon image;
	
	//Constructor
	public Comics(String title, ImageIcon image2) {
		this.title = title;
		this.image = image2;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	//Methods...
	
	
}
