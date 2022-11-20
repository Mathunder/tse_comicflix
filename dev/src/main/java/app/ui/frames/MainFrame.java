package app.ui.frames;

import app.entities.Comics;
import app.ui.components.*;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class MainFrame extends JFrame {
		
		// UI COMPONENTS
		static JFrame mf;
		static JPanel sideLeftBar;
		static JPanel searchBar;
		static JPanel visuComics;
		static JPanel loginInfo;
		static LeftBarButton discoverBtn;
		static LeftBarButton recommandBtn;
		static LeftBarButton myLibrary;
		static JLabel lbl_title;
		static JLabel lbl_username;
		    
		MainFrame() {		
			initComponents();
		}
		
		private void initComponents() {
			// Main Frame
			mf = new JFrame("Comics Library");   
			Image icon = Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\icon.png");  
			mf.setIconImage(icon);  
			mf.setSize(1600,900);      
			mf.setBackground(Color.decode("#AC0101"));
			mf.setResizable(false);
			mf.setLayout(null);

			// Panels -----------------------------------------------------
			//loginInfo Panel
			loginInfo = new JPanel();
			loginInfo.setLayout(new GridLayout(0,1));
			loginInfo.setBounds(0,0,250,150);
			loginInfo.setBackground(Color.decode("#790000"));
			
			//LeftBar Panel
			sideLeftBar = new JPanel();
			sideLeftBar.setLayout(new GridLayout(0,1));
			sideLeftBar.setBounds(0,150,250,750);
			sideLeftBar.setBackground(Color.decode("#AC0101"));
			
			//SearchBar Panel
			searchBar = new JPanel();
			searchBar.setBounds(250,0,1350,150);
			searchBar.setBackground(Color.gray);
			
			//VisuComics Panel
			VisuComicsPanel visuComics = new VisuComicsPanel();
			
			//ScrollBar VisuComics Panel
			JScrollPane scrollPaneVisuComics = new JScrollPane(visuComics);
			scrollPaneVisuComics.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPaneVisuComics.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPaneVisuComics.setBounds(250, 150, 1290, 750);
			
			//Load a test image, resize and convert into an ImageIcon
			ImageIcon imageTest = null;

			try {
				BufferedImage imageBrute = ImageIO.read(new File("src\\main\\resources\\batman.jpg"));
				Image imageResize = imageBrute.getScaledInstance(206, 320, Image.SCALE_DEFAULT);
				imageTest = new ImageIcon(imageResize);
				

			} catch (IOException e) {
				System.out.println("Problem load img");
				e.printStackTrace();
			}
			
			//Add test comics to VisuComics Panel
			Comics comics1 = new Comics("Batman", imageTest);
			for (int i = 0; i < 15; i++) {
				visuComics.displayComics(comics1, i);
				}
				 
			//Add Panels to Main Frame
			mf.add(loginInfo);
			mf.add(sideLeftBar);
			mf.add(searchBar);
			mf.add(scrollPaneVisuComics);
			
			//Button Discover
			discoverBtn = new LeftBarButton("Découvrir","#5F0000",20);
			discoverBtn.setBorderColorOnFocus();
			discoverBtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	discoverBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(discoverBtn);
			
			//Button Recommendation
			recommandBtn = new LeftBarButton("Recommandation","#AC0101",20);
			recommandBtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	recommandBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(recommandBtn);
			
			//Button MyLibrary
			myLibrary = new LeftBarButton("Ma bibliothèque","#AC0101",20);
			myLibrary.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	myLibraryBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(myLibrary);
			
			//Label Title
			lbl_title = new JLabel("Comics Library");
			lbl_title.setFont(new Font("Arial", Font.PLAIN,30));
			lbl_title.setForeground(Color.white);
			lbl_title.setSize(100,20);
			loginInfo.add(lbl_title);
			
			//Label Title
			lbl_username = new JLabel("      Invité");
			lbl_username.setFont(new Font("Arial", Font.PLAIN,20));
			lbl_username.setForeground(Color.lightGray);
			lbl_username.setSize(100,20);
			loginInfo.add(lbl_username);
			
			mf.setVisible(true);  
		}
		
		//Actions -------------------------------------------------------------
		
		private void discoverBtnActionPerformed(ActionEvent evt) {  
			
			//UI
	    	discoverBtn.setBackground(Color.decode("#5F0000"));
	    	recommandBtn.setBackground(Color.decode("#AC0101"));
	    	myLibrary.setBackground(Color.decode("#AC0101"));
	    	discoverBtn.setBorderColorOnFocus();
	    	recommandBtn.setBorderColorOnUnfocus();
	    	myLibrary.setBorderColorOnUnfocus();
	    }  
	    
	    private void recommandBtnActionPerformed(ActionEvent evt) {  

	    	//UI
	    	recommandBtn.setBackground(Color.decode("#5F0000"));
	    	discoverBtn.setBackground(Color.decode("#AC0101"));
	    	myLibrary.setBackground(Color.decode("#AC0101"));
	    	discoverBtn.setBorderColorOnUnfocus();
	    	recommandBtn.setBorderColorOnFocus();
	    	myLibrary.setBorderColorOnUnfocus();
	    }  
	    
	    private void myLibraryBtnActionPerformed(ActionEvent evt) {   

	    	//UI
	    	myLibrary.setBackground(Color.decode("#5F0000"));
	    	discoverBtn.setBackground(Color.decode("#AC0101"));
	    	recommandBtn.setBackground(Color.decode("#AC0101"));
	    	discoverBtn.setBorderColorOnUnfocus();
	    	recommandBtn.setBorderColorOnUnfocus();
	    	myLibrary.setBorderColorOnFocus();
	    } 
		
		public static void main(String[] args) { 
			new MainFrame();
		}

}