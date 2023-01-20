package app.ui.frames;

import app.entities.User;
import app.helpers.ComicVineSearchStatus;
import app.models.UiModel;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;
import app.services.UiController;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import app.ui.components.*;
import app.ui.themes.*;
import lombok.Data;

@SuppressWarnings("serial")

public class MainFrame extends JFrame implements PropertyChangeListener {
		
		// UI COMPONENTS
		static JFrame mf;
		static JPanel loginInfo;
		static JPanel sideLeftBar;
		static JPanel searchBarPanel;
		static JPanel creationCollection;
		public static ComicVueSearch visuSearchComics;
		private static ComicVueRecommandation visuRecommandedComics;
		private static ComicVueFavorite visuFavoriteComics;
		private static ComicVueRead visuReadComics;
		private static ComicVueCollection visuCollectionComics;
		static LeftBarButton btn_discover;
		static LeftBarButton btn_myRecommandation;
		static LeftBarButton btn_myFavorites;
		static LeftBarButton btn_myReads;
		static LeftBarButton btn_myCollections;
		static JLabel lbl_title;
		static JLabel lbl_username;
		private DefaultButton btnUserLogin;
		private JLabel lblUserID;
		private PaginationPanel paginationPanel;
		private JScrollPane scrollPaneVisuComics;
		
		//Models
		protected UserModel userModel;
		protected UiModel uiModel;
		
		//Controllers
		protected ComicVineService comicVineService;
		protected DatabaseService dataBaseService;
		protected UiController uiController;
		
		public MainFrame(UserModel um, UiModel uim, ComicVineService comicVineService, DatabaseService dbS, UiController uiC) {	
			super();
			this.userModel = um;
			this.uiModel = uim;
			this.comicVineService = comicVineService;
			this.dataBaseService = dbS;
			this.uiController = uiC;
			this.userModel.addPropertyChangeListener(this);
			this.comicVineService.addPropertyChangeListener(this);
			this.uiModel.addPropertyChangeListener(this);
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
			
			// Panels -----------------------------------------------------
			//loginInfo Panel
			loginInfo = new JPanel();
			loginInfo.setBounds(0, 0, 200, 150);
			loginInfo.setBackground(CustomColor.CrimsonRed);
			loginInfo.setLayout(null);
			
			//LeftBar Panel
			sideLeftBar = new JPanel();
			sideLeftBar.setBounds(0, 150, 200, 425);
			sideLeftBar.setBackground(CustomColor.Red);
			sideLeftBar.setLayout(new GridLayout(0, 1, 0, 0));
			
			//SearchBar Panel
			searchBarPanel = new SearchBarPanel(comicVineService);
			searchBarPanel.setBounds(200, 0, 850, 150);
			
			//CreationCollection Panel
			creationCollection = new CreationCollectionPanel(userModel, dataBaseService);
			creationCollection.setBounds(200, 0, 850, 150);
			
			
			//Pagination Panel
			paginationPanel= new PaginationPanel(comicVineService);

			//VisuComics Panels
			visuSearchComics = new ComicVueSearch(userModel, comicVineService, dataBaseService);
			
			//Recommendation Panels
			visuRecommandedComics = new ComicVueRecommandation(userModel, comicVineService, dataBaseService);
			
			//FavoriteComics Panels
			visuFavoriteComics = new ComicVueFavorite(userModel, comicVineService, dataBaseService);
			
			//ReadsComics Panel
			visuReadComics = new ComicVueRead(userModel, comicVineService, dataBaseService);
			
			//CollectionComics Panel
			visuCollectionComics = new ComicVueCollection(userModel, comicVineService, dataBaseService);
			
			//ScrollBar VisuComics Panel
			scrollPaneVisuComics = new JScrollPane(visuSearchComics);
			scrollPaneVisuComics.setBounds(200, 150, 840, 417);
			scrollPaneVisuComics.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneVisuComics.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPaneVisuComics.getVerticalScrollBar().setUnitIncrement(14);
			scrollPaneVisuComics.getHorizontalScrollBar().setUnitIncrement(14);
			
			//Add Panels to Main Frame
			mf.getContentPane().setLayout(null);
			mf.getContentPane().add(loginInfo);
			mf.getContentPane().add(sideLeftBar);
			mf.getContentPane().add(searchBarPanel);
			mf.getContentPane().add(paginationPanel);
			mf.getContentPane().add(scrollPaneVisuComics);
			mf.getContentPane().add(creationCollection);
			
			//Button Discover
			btn_discover = new LeftBarButton("Découvrir",CustomColor.CrimsonRed,20,true);
			btn_discover.setBorderColorOnFocus();
			btn_discover.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	discoverBtnActionPerformed(evt);
	            }
			});
			
			sideLeftBar.add(btn_discover);
			
			//Button Recommendation
			btn_myRecommandation = new LeftBarButton("Recommandation",CustomColor.Red,20,true);
			btn_myRecommandation.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	recommandBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(btn_myRecommandation);
			
			//Button Favorites
			btn_myFavorites = new LeftBarButton("Mes favoris",CustomColor.Red,20,true);
			btn_myFavorites.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	myLibraryBtnActionPerformed(evt);
	            }
			});
			sideLeftBar.add(btn_myFavorites);
			
			btn_myReads = new LeftBarButton("Mes lectures", CustomColor.Red,20,true);
			btn_myReads.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					myReadsBtnActionPerformed(evt);
				}
			});
			sideLeftBar.add(btn_myReads);
			
			btn_myCollections = new LeftBarButton("Mes collections", CustomColor.Red,20,true);
			btn_myCollections.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					myCollectionsBtnActionPerformed(evt);
				}
			});
			sideLeftBar.add(btn_myCollections);
			
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
			setFocusOnDiscoverPanel();
	    }  
		
		private void setFocusOnDiscoverPanel() {

	    	btn_discover.setBorderColorOnFocus();
	    	btn_myRecommandation.setBorderColorOnUnfocus();
	    	btn_myFavorites.setBorderColorOnUnfocus();
	    	btn_myReads.setBorderColorOnUnfocus();
	    	btn_myCollections.setBorderColorOnUnfocus();
	    	scrollPaneVisuComics.setViewportView(visuSearchComics);
	    	searchBarPanel.setVisible(true);
	    	paginationPanel.setVisible(true);
	    	creationCollection.setVisible(false);
		}
	    
	    private void recommandBtnActionPerformed(ActionEvent evt) {  

	    	btn_discover.setBorderColorOnUnfocus();
	    	btn_myRecommandation.setBorderColorOnFocus();
	    	btn_myFavorites.setBorderColorOnUnfocus();
	    	btn_myReads.setBorderColorOnUnfocus();
	    	btn_myCollections.setBorderColorOnUnfocus();
	    	scrollPaneVisuComics.setViewportView(visuRecommandedComics);
	    	searchBarPanel.setVisible(true);
	    	paginationPanel.setVisible(true);
	    	creationCollection.setVisible(false);
	    }  
	    
	    private void myLibraryBtnActionPerformed(ActionEvent evt) {   
	    	btn_discover.setBorderColorOnUnfocus();
	    	btn_myRecommandation.setBorderColorOnUnfocus();
	    	btn_myFavorites.setBorderColorOnFocus();
	    	btn_myReads.setBorderColorOnUnfocus();
	    	btn_myCollections.setBorderColorOnUnfocus();
	    	scrollPaneVisuComics.setViewportView(visuFavoriteComics);	
	    	searchBarPanel.setVisible(true);
	    	paginationPanel.setVisible(true);
	    	creationCollection.setVisible(false);
	    } 
	    
	    private void myReadsBtnActionPerformed(ActionEvent evt) {
	    	btn_discover.setBorderColorOnUnfocus();
	    	btn_myRecommandation.setBorderColorOnUnfocus();
	    	btn_myFavorites.setBorderColorOnUnfocus();
	    	btn_myReads.setBorderColorOnFocus();
	    	btn_myCollections.setBorderColorOnUnfocus();
	    	scrollPaneVisuComics.setViewportView(visuReadComics);
	    	searchBarPanel.setVisible(true);
	    	paginationPanel.setVisible(true);
	    	creationCollection.setVisible(false);
	    }
	    
	    private void myCollectionsBtnActionPerformed(ActionEvent evt) {
	    	btn_discover.setBorderColorOnUnfocus();
	    	btn_myRecommandation.setBorderColorOnUnfocus();
	    	btn_myFavorites.setBorderColorOnUnfocus();
	    	btn_myReads.setBorderColorOnUnfocus();
	    	btn_myCollections.setBorderColorOnFocus();
	    	scrollPaneVisuComics.setViewportView(visuCollectionComics);
	    	searchBarPanel.setVisible(false);
	    	paginationPanel.setVisible(false);
	    	creationCollection.setVisible(true);
	    }
	    
	    private void loginBtnActionPerformed(ActionEvent evt) {
	    	
	    	//If user is not authenticated
	    	if (!userModel.getIsAuthenticated()) {
		    	JFrame loginFrame = new LoginForm(userModel, dataBaseService, uiController);
		    	loginFrame.setVisible(true);
		    	uiController.setDisableLoginButton();
	    	}
	    	else { 
	    		userModel.setUser(false, new User(0, "Invité", "",""), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	    	}
	    	
	    }
	    
	    public void showUserInfo() {
	    	
	    	//Update information on login panel
	    	if(userModel.getIsAuthenticated())
	    		lblUserID.setText( userModel.getUser().getFirst_name().substring(0,1).toUpperCase() + userModel.getUser().getLast_name().substring(0,1).toUpperCase());
	    	else
	    		lblUserID.setText(userModel.getUser().getUsername().length() < 2 ? userModel.getUser().getUsername() : userModel.getUser().getUsername().toUpperCase().substring(0,2));
	    	
	    	lbl_username.setText(userModel.getUser().getUsername());

	    }
	    
	    private void updateUserPanelsAvailable() {
	    	
	    	if(userModel.getIsAuthenticated()) {
	    		btnUserLogin.setText("Logout");
	    		btn_myRecommandation.setVisible(true);
	    		btn_myFavorites.setVisible(true);
	    		btn_myReads.setVisible(true);
	    		btn_myCollections.setVisible(true);
	    				
	    	}
	    	else {
	    		btnUserLogin.setText("Login");
	    		btn_myRecommandation.setVisible(false);
	    		btn_myFavorites.setVisible(false);
	    		btn_myReads.setVisible(false);
	    		btn_myCollections.setVisible(false);
	    		
	    		setFocusOnDiscoverPanel();
	    	}
	    }
	    
	    public void changeStateLoginButton(boolean state) {
	    	this.btnUserLogin.setEnabled(state);
	    }
	    
	    public void propertyChange(PropertyChangeEvent evt) {
	    	if (evt.getPropertyName() == "userChange") {
				showUserInfo();
				updateUserPanelsAvailable();
	    	}
	    	
	    	if(evt.getPropertyName() == "searchStatus") //From Controller/Model ComicVineService
			{
				if(evt.getNewValue() == ComicVineSearchStatus.FETCHING) {
					setFocusOnDiscoverPanel();
				}
			}
	    	
	    	if(evt.getPropertyName() == "loginButtonStateChange") //From Controller/Model ComicVineService
			{
				if(evt.getNewValue().equals(true)) {
					changeStateLoginButton(true);
				}
				else {
					changeStateLoginButton(false);
				}
			}
	    	
	    }
}