package app.ui.frames;

import app.entities.User;
import app.helpers.ComicVineSearchStatus;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;
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
		public static ComicVueSearch visuSearchComics;
		private static ComicVueFavorite visuFavoriteComics;
		static LeftBarButton discoverBtn;
		static LeftBarButton recommandBtn;
		static LeftBarButton myLibrary;
		static JLabel lbl_title;
		static JLabel lbl_username;
		private DefaultButton btnUserLogin;
		private JLabel lblUserID;
		private PaginationPanel paginationPanel;
		private JScrollPane scrollPaneVisuComics;
		
		//Models
		protected UserModel userModel;
		
		//Controllers
		protected ComicVineService comicVineService;
		protected DatabaseService dataBaseService;
		
		public MainFrame(UserModel um, ComicVineService comicVineService, DatabaseService dbS) {	
			super();
			this.userModel = um;
			this.comicVineService = comicVineService;
			this.dataBaseService = dbS;
			this.userModel.addPropertyChangeListener(this);
			this.comicVineService.addPropertyChangeListener(this);
			
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
			sideLeftBar.setBounds(0, 150, 200, 450);
			sideLeftBar.setBackground(CustomColor.Red);
			sideLeftBar.setLayout(new GridLayout(0, 1, 0, 0));
			
			//SearchBar Panel
			searchBarPanel = new SearchBarPanel(comicVineService);
			searchBarPanel.setBounds(200, 0, 850, 150);
			
			//Pagination Panel
			paginationPanel= new PaginationPanel(comicVineService);

			//VisuComics Panels
			visuSearchComics = new ComicVueSearch(userModel, comicVineService, dataBaseService);
						
			//FavoriteComics Panels
			visuFavoriteComics = new ComicVueFavorite(userModel, comicVineService, dataBaseService);
			
			//ScrollBar VisuComics Panel
			scrollPaneVisuComics = new JScrollPane(visuSearchComics);
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
			myLibrary = new LeftBarButton("Mes favoris",CustomColor.Red,20,true);
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
			setFocusOnDiscoverPanel();
	    }  
		
		private void setFocusOnDiscoverPanel() {
			discoverBtn.setBackground(CustomColor.DarkRed);
	    	recommandBtn.setBackground(CustomColor.Red);
	    	myLibrary.setBackground(CustomColor.Red);
	    	discoverBtn.setBorderColorOnFocus();
	    	recommandBtn.setBorderColorOnUnfocus();
	    	myLibrary.setBorderColorOnUnfocus();
	    	scrollPaneVisuComics.setViewportView(visuSearchComics);
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
	    	scrollPaneVisuComics.setViewportView(visuFavoriteComics);
	    	    	
	    } 
	    
	    private void loginBtnActionPerformed(ActionEvent evt) {
	    	
	    	//If user is not authenticated
	    	if (!userModel.getIsAuthenticated()) {
		    	JFrame loginFrame = new LoginForm(userModel, dataBaseService);
		    	loginFrame.setVisible(true);
	    	}
	    	else { 
	    		userModel.setUser(false, new User(0, "Invité", "",""), new ArrayList<>());
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
		    	scrollPaneVisuComics.setViewportView(visuSearchComics);
	    		
	    	}
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
	    }
}