package app.ui.frames;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import app.ui.components.*;
import app.ui.themes.*;

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
			mf.setBackground(CustomColor.WhiteCloud.getColor());
			mf.setResizable(false);
			
			// Panels -----------------------------------------------------
			//loginInfo Panel
			loginInfo = new JPanel();
			loginInfo.setBounds(0,0,250,150);
			loginInfo.setBackground(CustomColor.CrimsonRed.getColor());
			
			//LeftBar Panel
			sideLeftBar = new JPanel();
			sideLeftBar.setLayout(new GridLayout(0,1));
			sideLeftBar.setBounds(0,150,250,750);
			sideLeftBar.setBackground(CustomColor.Red.getColor());
			
			//SearchBar Panel
			searchBar = new JPanel();
			searchBar.setBounds(250,0,1350,150);
			searchBar.setBackground(CustomColor.Gray.getColor());
			
			//VisuComics Panel
			visuComics = new JPanel();
			visuComics.setBounds(250,150,1350,750);
			visuComics.setBackground(CustomColor.LightGray.getColor());
				 
			//Add Panels to Main Frame
			mf.getContentPane().add(loginInfo);
			mf.getContentPane().add(sideLeftBar);
			mf.getContentPane().add(searchBar);
			mf.getContentPane().add(visuComics);
			
			//Button Discover
			discoverBtn = new LeftBarButton("Découvrir",CustomColor.Red.getColorHexValue(),20,true);
			discoverBtn.setBackground(CustomColor.DarkRed.getColor());
			discoverBtn.setBorderColorOnFocus();
			discoverBtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	discoverBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(discoverBtn);
			
			//Button Recommendation
			recommandBtn = new LeftBarButton("Recommandation",CustomColor.Red.getColorHexValue(),20,true);
			recommandBtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	recommandBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(recommandBtn);
			
			//Button MyLibrary
			myLibrary = new LeftBarButton("Ma bibliothèque",CustomColor.Red.getColorHexValue(),20,true);
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
			lbl_username.setLocation(125, 50);
			lbl_username.setFont(new Font("Arial", Font.PLAIN,20));
			lbl_username.setForeground(CustomColor.LightGray.getColor());
			lbl_username.setSize(115,50);
			loginInfo.add(lbl_username);
			
			//Button login
			btnUserLogin = new JButton("Login");
			btnUserLogin.setForeground(new Color(255, 255, 255));
			btnUserLogin.setBorderPainted(true);
			btnUserLogin.setFocusPainted(false);	
			btnUserLogin.setBorder(BorderFactory.createMatteBorder(-1, -1, -1, -1, Color.darkGray));
			btnUserLogin.setFont(new Font("Candara", Font.BOLD, 20));
			btnUserLogin.setBackground(new Color(121, 0, 0));
			btnUserLogin.setBounds(146, 94, 71, 34);
			loginInfo.add(btnUserLogin);
			
			//RoundPanel UserIdCard
			PanelRound UserCard = new PanelRound();
			UserCard.setRoundTopRight(75);
			UserCard.setRoundTopLeft(75);
			UserCard.setRoundBottomRight(75);
			UserCard.setRoundBottomLeft(75);
			UserCard.setBounds(30, 53, 75, 75);
			loginInfo.add(UserCard);
			UserCard.setLayout(null);
			
			//UserLabelId
			JLabel lblUserID = new JLabel("IN");
			lblUserID.setForeground(Color.GRAY);
			lblUserID.setBounds(18, 13, 42, 49);
			lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 40));
			UserCard.add(lblUserID);
			
			mf.setVisible(true);  
		}
		
		//Actions -------------------------------------------------------------
		
		private void discoverBtnActionPerformed(ActionEvent evt) {  
			
	    	discoverBtn.setBackground(CustomColor.DarkRed.getColor());
	    	recommandBtn.setBackground(CustomColor.Red.getColor());
	    	myLibrary.setBackground(CustomColor.Red.getColor());
	    	discoverBtn.setBorderColorOnFocus();
	    	recommandBtn.setBorderColorOnUnfocus();
	    	myLibrary.setBorderColorOnUnfocus();
	    }  
	    
	    private void recommandBtnActionPerformed(ActionEvent evt) {  

	    	recommandBtn.setBackground(CustomColor.DarkRed.getColor());
	    	discoverBtn.setBackground(CustomColor.Red.getColor());
	    	myLibrary.setBackground(CustomColor.Red.getColor());
	    	discoverBtn.setBorderColorOnUnfocus();
	    	recommandBtn.setBorderColorOnFocus();
	    	myLibrary.setBorderColorOnUnfocus();
	    }  
	    
	    private void myLibraryBtnActionPerformed(ActionEvent evt) {   

	    	myLibrary.setBackground(CustomColor.DarkRed.getColor());
	    	discoverBtn.setBackground(CustomColor.Red.getColor());
	    	recommandBtn.setBackground(CustomColor.Red.getColor());
	    	discoverBtn.setBorderColorOnUnfocus();
	    	recommandBtn.setBorderColorOnUnfocus();
	    	myLibrary.setBorderColorOnFocus();
	    } 
		
		public static void main(String[] args) { 
			new MainFrame();
		}
}