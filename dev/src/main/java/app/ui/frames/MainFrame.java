package app.ui.frames;

import app.entities.Comics;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import app.ui.components.*;
import app.ui.themes.*;

class MainFrame extends JFrame {
		
		// UI COMPONENTS
		static JFrame mf;
		static JPanel sideLeftBar;
		static SearchBarPanel searchBar;
		static JPanel loginInfo;
		static LeftBarButton discoverBtn;
		static LeftBarButton recommandBtn;
		static LeftBarButton myLibrary;
		static JLabel lbl_title;
		static JLabel lbl_username;
		private JButton btnUserLogin;
		    
		MainFrame() {		
			initComponents();
		}
		
		private void initComponents() {
			// Main Frame
			mf = new JFrame("Comics Library");   
			Image icon = Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\icon.png");  
			mf.setIconImage(icon);  
			mf.setSize(1600,900);      
			mf.setBackground(CustomColor.WhiteCloud);
			mf.setResizable(false);
			mf.getContentPane().setLayout(null);

			// Panels -----------------------------------------------------
			//loginInfo Panel
			loginInfo = new JPanel();
			loginInfo.setBounds(0,0,250,150);
			loginInfo.setBackground(CustomColor.CrimsonRed);
			
			//LeftBar Panel
			sideLeftBar = new JPanel();
			sideLeftBar.setLayout(new GridLayout(0,1));
			sideLeftBar.setBounds(0,150,250,750);
			sideLeftBar.setBackground(CustomColor.Red);
			
			//SearchBar Panel
			searchBar = new SearchBarPanel();
			searchBar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
			searchBar.setBounds(250,0,1350,150);
			searchBar.setBackground(CustomColor.WhiteCloud);
			
			//VisuComics Panels
			VisuComicsPanel visuComics = new VisuComicsPanel();
	
			//ScrollBar VisuComics Panel
			JScrollPane scrollPaneVisuComics = new JScrollPane(visuComics);
			scrollPaneVisuComics.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPaneVisuComics.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPaneVisuComics.setBounds(250, 150, 1255, 750);
			scrollPaneVisuComics.getVerticalScrollBar().setUnitIncrement(14);
			
			//Load a test image, resize and convert into an ImageIcon ______________________ TEST _______________________
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
			
			//____________________________________________________________________________________________________________
			
			//Add Panels to Main Frame
			mf.getContentPane().add(loginInfo);
			mf.getContentPane().add(sideLeftBar);
			mf.getContentPane().add(searchBar);		
			mf.getContentPane().add(scrollPaneVisuComics);
			
			//Button Discover
			discoverBtn = new LeftBarButton("Découvrir",CustomColor.Red,20,true);
			discoverBtn.setBackground(new Color(121, 0, 0));
			discoverBtn.setBorderColorOnFocus();
			discoverBtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	discoverBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(discoverBtn);
			
			//Button Recommendation
			recommandBtn = new LeftBarButton("Recommandation",CustomColor.Red,20,true);
			recommandBtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	recommandBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(recommandBtn);
			
			//Button MyLibrary
			myLibrary = new LeftBarButton("Ma bibliothèque",CustomColor.Red,20,true);
			myLibrary.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	myLibraryBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(myLibrary);
			loginInfo.setLayout(null);
			
			//Label Title
			lbl_title = new JLabel("Comics Library");
			lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_title.setLocation(0, 0);
			lbl_title.setFont(new Font("Arial", Font.PLAIN,30));
			lbl_title.setForeground(Color.white);
			lbl_title.setSize(250,50);
			loginInfo.add(lbl_title);
			
			//Label Title
			lbl_username = new JLabel("Invité");
			lbl_username.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_username.setLocation(125, 43);
			lbl_username.setFont(new Font("Arial", Font.PLAIN,20));
			lbl_username.setForeground(CustomColor.LightGray);
			lbl_username.setSize(115,50);
			loginInfo.add(lbl_username);
			
			btnUserLogin = new JButton("Login");
			btnUserLogin.setForeground(new Color(255, 255, 255));
			btnUserLogin.setBorderPainted(true);
			btnUserLogin.setFocusPainted(false);	
			btnUserLogin.setBorder(BorderFactory.createMatteBorder(-1, -1, -1, -1, Color.darkGray));
			btnUserLogin.setFont(new Font("Candara", Font.BOLD, 20));
			btnUserLogin.setBackground(new Color(121, 0, 0));
			btnUserLogin.setBounds(147, 85, 71, 34);
			loginInfo.add(btnUserLogin);
			
			PanelRound UserCard = new PanelRound();
			UserCard.setRoundTopRight(75);
			UserCard.setRoundTopLeft(75);
			UserCard.setRoundBottomRight(75);
			UserCard.setRoundBottomLeft(75);
			UserCard.setBounds(30, 53, 75, 75);
			UserCard.setLayout(null);
			loginInfo.add(UserCard);
			
			JLabel lblUserID = new JLabel("IN");
			lblUserID.setForeground(Color.GRAY);
			lblUserID.setBounds(18, 13, 42, 49);
			lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 40));
			UserCard.add(lblUserID);
			
			mf.setVisible(true);  
		}
		
		//Actions -------------------------------------------------------------
		
		private void discoverBtnActionPerformed(ActionEvent evt) {  
			
	    	discoverBtn.setBackground(CustomColor.DarkRed);
	    	recommandBtn.setBackground(CustomColor.Red);
	    	myLibrary.setBackground(CustomColor.Red);
	    	discoverBtn.setBorderColorOnFocus();
	    	recommandBtn.setBorderColorOnUnfocus();
	    	myLibrary.setBorderColorOnUnfocus();
	    }  
	    
	    private void recommandBtnActionPerformed(ActionEvent evt) {  

	    	recommandBtn.setBackground(CustomColor.DarkRed);
	    	discoverBtn.setBackground(CustomColor.Red);
	    	myLibrary.setBackground(CustomColor.Red);
	    	discoverBtn.setBorderColorOnUnfocus();
	    	recommandBtn.setBorderColorOnFocus();
	    	myLibrary.setBorderColorOnUnfocus();
	    }  
	    
	    private void myLibraryBtnActionPerformed(ActionEvent evt) {   

	    	myLibrary.setBackground(CustomColor.DarkRed);
	    	discoverBtn.setBackground(CustomColor.Red);
	    	recommandBtn.setBackground(CustomColor.Red);
	    	discoverBtn.setBorderColorOnUnfocus();
	    	recommandBtn.setBorderColorOnUnfocus();
	    	myLibrary.setBorderColorOnFocus();
	    } 
		
		public static void main(String[] args) { 
			new MainFrame();
		}
}