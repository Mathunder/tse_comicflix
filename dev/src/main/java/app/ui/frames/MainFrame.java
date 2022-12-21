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
		static JPanel loginInfo;
		static JPanel sideLeftBar;
		static JPanel searchBarPanel;
		public static VisuComicsPanel visuComics;
		static LeftBarButton discoverBtn;
		static LeftBarButton recommandBtn;
		static LeftBarButton myLibrary;
		static JLabel lbl_title;
		static JLabel lbl_username;
		private DefaultButton btnUserLogin;
		private JLabel lblUserID;
		protected ComicVineService comicVineService;
		private static InterfaceMainFrame listenerController;
		private PaginationPanel paginationPanel;
		private User user;
		private FavoritesPanel favPanel;
		private JScrollPane scrollPaneVisuComics;
		
		public MainFrame(ComicVineService comicVineService) {	
			super();
			this.comicVineService = comicVineService;
			listenerController = new InterfaceMainFrame(this);
			
			initComponents();
		}
		
		private void initComponents() {
			// Main Frame
			mf = new JFrame("Comics Library");   
			mf.getContentPane().setBackground(new Color(172, 0, 0));
			
			Image icon = Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\icon.png");  
			mf.setIconImage(icon);  
			mf.setSize(1050,600);      
			mf.setBackground(CustomColor.Red);
			mf.setResizable(false);

			//User
			user = new User(false, 0, "Invité", "", "");
			
			// Panels -----------------------------------------------------
			//loginInfo Panel
			loginInfo = new JPanel();
			loginInfo.setBounds(0, 0, 200, 150);
			loginInfo.setBackground(CustomColor.CrimsonRed);
			loginInfo.setLayout(null);
			
			//LeftBar Panel
			sideLeftBar = new JPanel();
			sideLeftBar.setBounds(0, 150, 200, 450);
			sideLeftBar.setBackground(CustomColor.Red);
			sideLeftBar.setLayout(new GridLayout(0, 1, 0, 0));
			
			//SearchBar Panel
			searchBarPanel = new SearchBarPanel(comicVineService);
			searchBarPanel.setBounds(200, 0, 850, 150);
			
			//Pagination Panel
			paginationPanel= new PaginationPanel(comicVineService);

			//VisuComics Panels
			visuComics = new VisuComicsPanel();
			visuComics.updateUser(user);
			
			//Favorites Panel
			favPanel = new FavoritesPanel();
	
			//ScrollBar VisuComics Panel
			scrollPaneVisuComics = new JScrollPane(visuComics);
			scrollPaneVisuComics.setBounds(200, 150, 840, 417);
			scrollPaneVisuComics.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneVisuComics.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPaneVisuComics.getVerticalScrollBar().setUnitIncrement(14);
			scrollPaneVisuComics.getHorizontalScrollBar().setUnitIncrement(14);
			
			//ComicsInfos Panel
			ComicsInfosPanel visuComicInfos = new ComicsInfosPanel();
			
			//ScrollBar ComicsInfos Panel
			JScrollPane scrollPaneComicsInfos = new JScrollPane(visuComicInfos);
			
			//Add Panels to Main Frame
			mf.getContentPane().setLayout(null);
			mf.getContentPane().add(loginInfo);
			mf.getContentPane().add(sideLeftBar);
			mf.getContentPane().add(searchBarPanel);
			mf.getContentPane().add(paginationPanel);
			mf.getContentPane().add(scrollPaneVisuComics);
			
			//Button Discover
			discoverBtn = new LeftBarButton("Découvrir",CustomColor.CrimsonRed,20,true);
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
			
			
			//Label Title
			lbl_title = new JLabel("Comics Library");
			lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_title.setLocation(0, 0);
			lbl_title.setFont(new Font("Arial", Font.PLAIN, 24));
			lbl_title.setForeground(Color.white);
			lbl_title.setSize(200,50);
			loginInfo.add(lbl_title);
			
			//Label Title
			lbl_username = new JLabel("Invité");
			lbl_username.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_username.setLocation(94, 54);
			lbl_username.setFont(new Font("Arial", Font.PLAIN, 22));
			lbl_username.setForeground(CustomColor.LightGray);
			lbl_username.setSize(106,50);
			loginInfo.add(lbl_username);
			
			//Button UserLoginLogout
			btnUserLogin = new DefaultButton("Login", CustomColor.CrimsonRed, 18, false);
			btnUserLogin.setSize(106, 30);
			btnUserLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					loginBtnActionPerformed(evt);
				}
			});
			
			btnUserLogin.setLocation(94,100);
			loginInfo.add(btnUserLogin);
			
			//User card
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
						
			updateUserPanelsAvailable();
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
	    	
	    	scrollPaneVisuComics.setViewportView(visuComics);
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
	    	
	    	favPanel.setUser(user);
	    	scrollPaneVisuComics.setViewportView(favPanel);
	    	
	    } 
	    
	    private void loginBtnActionPerformed(ActionEvent evt) {
	    	
	    	//If user is not authenticated
	    	if (!user.isAuthenticated()) {
		    	JFrame loginFrame = new LoginForm(listenerController);
		    	loginFrame.setVisible(true);
	    	}
	    	else { 
	    		user = new User(false, 0, "Invité", "","");
	    		setUserProfile(user);
	    		visuComics.updateUser(user);
	    	}
	    	
	    }
	    
	    public void setUserProfile(User newUser) {
	    	
	    	user = newUser;
	    	visuComics.updateUser(user);
	    	
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
	    		myLibrary.setVisible(true);
	    				
	    	}
	    	else {
	    		btnUserLogin.setText("Login");
	    		recommandBtn.setVisible(false);
	    		myLibrary.setVisible(false);
	    		
		    	discoverBtn.setBackground(CustomColor.DarkRed);
		    	recommandBtn.setBackground(CustomColor.Red);
		    	myLibrary.setBackground(CustomColor.Red);
		    	discoverBtn.setBorderColorOnFocus();
		    	recommandBtn.setBorderColorOnUnfocus();
		    	myLibrary.setBorderColorOnUnfocus();
		    	scrollPaneVisuComics.setViewportView(visuComics);
	    		
	    	}
	    }
}