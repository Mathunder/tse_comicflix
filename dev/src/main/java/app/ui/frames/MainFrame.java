package app.ui.frames;


import app.entities.User;
import app.services.ComicVineService;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import app.ui.components.*;
import app.ui.events.InterfaceMainFrame;
import app.ui.themes.*;
import lombok.Data;

@SuppressWarnings("serial")

public class MainFrame extends JFrame {
		
		// UI COMPONENTS
		static JFrame mf;
		static JPanel sideLeftBar;
		static JPanel searchBarPanel;
		public static ComicVueSearch visuSearchComics;
		private static ComicVueFavorite visuFavoriteComics;
		private static ComicVueRead visuReadComics;
		static LeftBarButton discoverBtn;
		static LeftBarButton recommandBtn;
		static LeftBarButton myFavorites;
		static LeftBarButton myReads;
		static JLabel lbl_title;
		static JLabel lbl_username;
		private JButton btnUserLogin;
		private JLabel lblUserID;
		protected ComicVineService comicVineService;
		private static InterfaceMainFrame listenerController;
		private  PaginationPanel paginationPanel;
		private User user;
		
		public MainFrame(ComicVineService comicVineService) {	
			super();
			this.comicVineService = comicVineService;
			listenerController = new InterfaceMainFrame(this);
			
			initComponents();
		}
		
		public static JFrame getMf() {
			return mf;
		}
		
		private void initComponents() {
			// Main Frame
			mf = new JFrame("Comics Library");   
			mf.getContentPane().setBackground(new Color(172, 0, 0));
			
			Image icon = Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\icon.png");  
			mf.setIconImage(icon);  
			mf.setSize(1600,900);      
			mf.setBackground(CustomColor.Red);
			mf.setResizable(false);

			//User
			user = new User(false, "Invité", "", "");
			
			// Panels -----------------------------------------------------
			//loginInfo Panel
			loginInfo = new JPanel();
			loginInfo.setBounds(0, 0, 250, 150);
			loginInfo.setBackground(CustomColor.CrimsonRed);
			
			//LeftBar Panel
			sideLeftBar = new JPanel();
			sideLeftBar.setBounds(0, 150, 200, 425);
			sideLeftBar.setBackground(CustomColor.Red);
			
			//SearchBar Panel
			//Pagination Panel
			paginationPanel= new PaginationPanel(comicVineService);	
			
			//ReadsComics Panel
			visuReadComics = new ComicVueRead(userModel, comicVineService, dataBaseService);
			
			//ScrollBar VisuComics Panel
			JScrollPane scrollPaneVisuComics = new JScrollPane(visuComics);
			scrollPaneVisuComics.setBounds(250, 150, 1253, 715);
			scrollPaneVisuComics.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPaneVisuComics.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPaneVisuComics.getVerticalScrollBar().setUnitIncrement(14);
			
			//Add Panels to Main Frame
			mf.getContentPane().setLayout(null);
							 
			//Add Panels to Main Frame
			mf.getContentPane().add(loginInfo);
			mf.getContentPane().add(sideLeftBar);

			
			mf.getContentPane().add(paginationPanel);
			mf.getContentPane().add(scrollPaneVisuComics);
			mf.getContentPane().add(scrollPaneComicsInfos);
			
			//Button Discover
			discoverBtn = new LeftBarButton("Découvrir",CustomColor.Red,20,true);
			discoverBtn.setBackground(new Color(121, 0, 0));
			discoverBtn.setBorderColorOnFocus();
			discoverBtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	discoverBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.setLayout(new GridLayout(0, 1, 0, 0));
			sideLeftBar.add(discoverBtn);
			
			//Button Recommendation
			recommandBtn = new LeftBarButton("Recommandation",CustomColor.Red,20,true);
			recommandBtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	recommandBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(recommandBtn);
			
			//Button Favorites
			myFavorites = new LeftBarButton("Mes favoris",CustomColor.Red,20,true);
			myFavorites.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	myLibraryBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(myFavorites);
			
			myReads = new LeftBarButton("Mes lectures", CustomColor.Red,20,true);
			myReads.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					myReadsBtnActionPerformed(evt);
				}
			});
			sideLeftBar.add(myReads);
			
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
			btnUserLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					loginBtnActionPerformed(evt);
				}
			});
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
			UserCard.setBounds(20, 53, 75, 75);
			UserCard.setLayout(null);
			loginInfo.add(UserCard);
			
			lblUserID = new JLabel("IN");
			lblUserID.setHorizontalAlignment(SwingConstants.CENTER);
			lblUserID.setForeground(Color.GRAY);
			lblUserID.setBounds(-1, 0, 78, 75);
			lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 30));
			UserCard.add(lblUserID);
			
			SearchBarPanel searchBarPanel = new SearchBarPanel(comicVineService);
			searchBarPanel.setBounds(250, 0, 1236, 150);
			mf.getContentPane().add(searchBarPanel);
			
			updateUserPanelsAvailable();
			mf.setVisible(true);  
		}
		
		//Actions -------------------------------------------------------------
		private void discoverBtnActionPerformed(ActionEvent evt) {  
			setFocusOnDiscoverPanel();
	    }  
		
		private void setFocusOnDiscoverPanel() {

	    	discoverBtn.setBorderColorOnFocus();
	    	recommandBtn.setBorderColorOnUnfocus();
	    	myFavorites.setBorderColorOnUnfocus();
	    	myReads.setBorderColorOnUnfocus();
	    	scrollPaneVisuComics.setViewportView(visuSearchComics);
		}
	    
	    private void recommandBtnActionPerformed(ActionEvent evt) {  

	    	discoverBtn.setBorderColorOnUnfocus();
	    	recommandBtn.setBorderColorOnFocus();
	    	myFavorites.setBorderColorOnUnfocus();
	    	myReads.setBorderColorOnUnfocus();
	    	scrollPaneVisuComics.setViewportView(null);
	    }  
	    
	    private void myLibraryBtnActionPerformed(ActionEvent evt) {   
	    	discoverBtn.setBorderColorOnUnfocus();
	    	recommandBtn.setBorderColorOnUnfocus();
	    	myFavorites.setBorderColorOnFocus();
	    	myReads.setBorderColorOnUnfocus();
	    	scrollPaneVisuComics.setViewportView(visuFavoriteComics);	    	
	    } 
	    
	    private void myReadsBtnActionPerformed(ActionEvent evt) {
	    	discoverBtn.setBorderColorOnUnfocus();
	    	recommandBtn.setBorderColorOnUnfocus();
	    	myFavorites.setBorderColorOnUnfocus();
	    	myReads.setBorderColorOnFocus();
	    	scrollPaneVisuComics.setViewportView(visuReadComics);
	    }
	    
	    private void loginBtnActionPerformed(ActionEvent evt) {
	    	
	    	//If user is not authenticated
	    	if (!user.isAuthenticated()) {
		    	JFrame loginFrame = new LoginForm(listenerController);
		    	loginFrame.setVisible(true);
	    	}
	    	else { 
	    		userModel.setUser(false, new User(0, "Invité", "",""), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	    	}
	    }
	    
	    public void setUserProfile(User newUser) {
	    	
	    	user = newUser;
	    	
	    	//Update information on login panel
	    	if(user.isAuthenticated())
	    		lblUserID.setText( user.getFirst_name().substring(0,1).toUpperCase() + user.getLast_name().substring(0,1).toUpperCase());
	    	else
	    		lblUserID.setText(user.getUsername().length() < 2 ? user.getUsername() : user.getUsername().toUpperCase().substring(0,2));
	    	
	    	lbl_username.setText(user.getUsername());

	    	//Update interface
	    	updateUserPanelsAvailable();

	    }
	    
	    private void updateUserPanelsAvailable() {
	    	
	    	if(user.isAuthenticated()) {
	    		btnUserLogin.setText("Logout");
	    		recommandBtn.setVisible(true);
	    		myFavorites.setVisible(true);
	    		myReads.setVisible(true);
	    				
	    	}
	    	else {
	    		btnUserLogin.setText("Login");
	    		recommandBtn.setVisible(false);
	    		myFavorites.setVisible(false);
	    		myReads.setVisible(false);
	    		setFocusOnDiscoverPanel();
	    	}
	    }
}