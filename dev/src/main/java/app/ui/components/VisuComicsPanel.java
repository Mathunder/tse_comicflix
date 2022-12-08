package app.ui.components;


import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import app.dto.SearchResultDto;
import app.entities.Comics;
import app.ui.themes.CustomColor;

public class VisuComicsPanel extends JPanel{
	
	private SearchResultDto result;
	
	public VisuComicsPanel(){
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(CustomColor.WhiteCloud);
		this.setBounds(250, 150, 1350, 900);
		this.setLayout(new GridLayout(0,4,0,40));
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
	public void showResult(SearchResultDto res) {
		
		removeComics();
		
		result = res;	
		if(result != null) {
			result.getResults().stream().forEach(System.out::println);			
		}
		else {
			System.out.println("Result null");
		}
		
		
		
		for(int i=0;i<result.getResults().size();i++) {
			//Load a test image, resize and convert into an ImageIcon ______________________ TEST _______________________
			ImageIcon imageURL = null;

			try {
				URL url = new URL(result.getResults().get(i).getImage().getMedium_url());
				BufferedImage imageBrute = ImageIO.read(url);
				Image imageResize = imageBrute.getScaledInstance(206, 310, Image.SCALE_DEFAULT);
				imageURL = new ImageIcon(imageResize);
				

			} catch (IOException e) {
				System.out.println("Problem load img");
				e.printStackTrace();
			}
			// [result.getResults().get(i)].class = Issue
			Comics comics = new Comics(result.getResults().get(i));
			this.displayComics(comics);
		}
		
		refreshPanel();
		
	}
}
