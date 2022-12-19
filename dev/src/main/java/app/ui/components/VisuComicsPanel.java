package app.ui.components;


import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import app.dto.SearchResultDto;
import app.entities.Comics;
import app.ui.frames.MainFrame;
import app.ui.themes.CustomColor;

public class VisuComicsPanel extends JPanel{
	
	private SearchResultDto result;
	private boolean display_comics_infos = false;
	private Comics clicked_comics;
	
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
		LabelComic.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println(comics.getSynopsis() + " has been clicked.");
				// Updating and storing the comic that has been clicked
				display_comics_infos = true;
				clicked_comics = comics;
				System.out.println(display_comics_infos);
				clicked_comics.displayInfos(comics);
			}
		});
	}
	
	public void removeComics() {
		this.removeAll();
	}
	
	public void refreshPanel() {
		this.revalidate();
		this.repaint();
	}
	
	// The return statement is here to store the comics that are displayed, 
	// allowing to detect which one of them has been clicked.
	public void showResult(SearchResultDto res) {
		
		removeComics();
		
		result = res;	
		if(result != null) {
			result.getResults().stream().forEach(System.out::println);			
		}
		else {
			System.out.println("Result null");
		}
		
		for(int i = 0; i < result.getResults().size(); i++) {
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

			Comics comics = new Comics(result.getResults().get(i));
			this.displayComics(comics);
		}

		refreshPanel();
	}
}
package app.ui.components;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import app.dto.SearchResultDto;
import app.entities.Comics;
import app.ui.frames.MainFrame;
import app.ui.themes.CustomColor;

public class VisuComicsPanel extends JPanel{
	
	private SearchResultDto result;
	private boolean display_comics_infos = false;
	private Comics clicked_comics;
	
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
		LabelComic.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println(comics.getSynopsis() + " has been clicked.");
				// Updating and storing the comic that has been clicked
				display_comics_infos = true;
				clicked_comics = comics;
				//MainFrame.visuComics.setVisible(false);
				ComicsInfosPanel infos = new ComicsInfosPanel();
				infos.createComicsInfosPanel(clicked_comics);
				infos.setVisible(true);
				JFrame f = new JFrame();
				f.getContentPane().setBackground(new Color(172, 0, 0));
				f.setSize(1050,600); 
				f.add(infos);
				
				f.setVisible(true);
			}
		});
	}
	
	public void removeComics() {
		this.removeAll();
	}
	
	public void refreshPanel() {
		this.revalidate();
		this.repaint();
	}
	
	// The return statement is here to store the comics that are displayed, 
	// allowing to detect which one of them has been clicked.
	public void showResult(SearchResultDto res) {
		
		removeComics();
		
		result = res;	
		if(result != null) {
			result.getResults().stream().forEach(System.out::println);			
		}
		else {
			System.out.println("Result null");
		}
		
		for(int i = 0; i < result.getResults().size(); i++) {
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

			Comics comics = new Comics(result.getResults().get(i));
			this.displayComics(comics);
		}

		refreshPanel();
	}
}
