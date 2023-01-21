package app.ui.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.SpringLayout;
import java.awt.Toolkit;

import app.models.UiModel;
import app.models.UserModel;
import app.services.DatabaseService;
import app.services.UiController;
import app.ui.components.DefaultButton;
import app.ui.themes.CustomColor;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginForm extends JFrame implements PropertyChangeListener {

	private DefaultButton btnCreate;
	
	private JPanel contentPane;
	private JTextField txtField_username;
	private JPasswordField passwordField;
	private JLabel lblErrorLogin;
	private boolean isCredientialCorrect = false;
	//Model
	protected UserModel userModel; 
	protected UiModel uiModel;
	//Controller
	protected DatabaseService databaseService;
	protected UiController uiController;
	
	/**
	 * Create the frame.
	 */
	public LoginForm(UserModel um, DatabaseService dbS, UiController uiC, UiModel UiM) {
		this.userModel = um;
		this.databaseService = dbS;
		this.uiController = uiC;
		this.uiModel = UiM;
		this.userModel.addPropertyChangeListener(this);
		this.uiModel.addPropertyChangeListener(this);
		initComponents();
		
		
	}
	
	private void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\icon.png"));
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(249, 246, 241));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblUsername = new JLabel("Utilisateur");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUsername, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUsername, 45, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblUsername, 54, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblUsername, -282, SpringLayout.EAST, contentPane);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblUsername);
		
		txtField_username = new JTextField();
		txtField_username.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtField_username, 13, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtField_username, 34, SpringLayout.EAST, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtField_username, -34, SpringLayout.EAST, contentPane);
		contentPane.add(txtField_username);
		txtField_username.setColumns(1);
		
		JLabel lblPassword = new JLabel("Mot de passe");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPassword, 0, SpringLayout.SOUTH, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPassword, 45, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblPassword, -147, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblPassword, -282, SpringLayout.EAST, contentPane);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.NORTH, passwordField, 13, SpringLayout.NORTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, txtField_username);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, passwordField, -9, SpringLayout.SOUTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.EAST, passwordField, 0, SpringLayout.EAST, txtField_username);
		contentPane.add(passwordField);
		
		lblErrorLogin = new JLabel("Utilisateur et/ou mot de passe incorrect(s) ! ");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblErrorLogin, 8, SpringLayout.SOUTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblErrorLogin, 111, SpringLayout.WEST, contentPane);
		lblErrorLogin.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblErrorLogin.setForeground(Color.RED);
		lblErrorLogin.setVisible(false);
		contentPane.add(lblErrorLogin);
		
		DefaultButton btnLogin = new DefaultButton("Connexion", CustomColor.Red, 14, true);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLoginActionPerformed(e);
			}
		});		
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLogin, 151, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnLogin, -60, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtField_username, -106, SpringLayout.NORTH, btnLogin);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnLogin, -130, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnLogin, -25, SpringLayout.EAST, contentPane);
		contentPane.add(btnLogin);
		
		DefaultButton btnCancel = new DefaultButton("Fermer", CustomColor.Black, 14, true);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelActionPerformed(e); 				
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnLogin);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCancel, 25, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnCancel, -60, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancel, 130, SpringLayout.WEST, contentPane);
		contentPane.add(btnCancel);
		
		this.btnCreate = new DefaultButton("Créer compte", CustomColor.CrimsonRed, 14, true);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCreateActionPerformed(e); 				
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCreate, 0, SpringLayout.NORTH, btnLogin);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCreate, 20, SpringLayout.EAST, btnCancel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnCreate, 0, SpringLayout.SOUTH, btnLogin);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCreate, -20, SpringLayout.WEST, btnLogin);
		contentPane.add(btnCreate);
		
		DefaultButton btnChange = new DefaultButton("Changer MDP", CustomColor.Gray, 14, true);
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnChangeActionPerformed(e); 				
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnChange, 10, SpringLayout.SOUTH, btnCreate);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnChange, 0, SpringLayout.WEST, btnCreate);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnChange, -7, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnChange, 0, SpringLayout.EAST, btnCreate);
		contentPane.add(btnChange);
	}
	
	
	private void btnCancelActionPerformed(ActionEvent e) {
		
		uiController.setEnableLoginButton();
		dispose();
	}
	
	private void btnCreateActionPerformed(ActionEvent e) {
		
		uiController.setDisableCreateAccountButton();
		JFrame CreateAccount = new CreateAccount(userModel, databaseService, uiController);
		CreateAccount.setVisible(true);
	
	}
	
	private void btnChangeActionPerformed(ActionEvent e) {
		JFrame RecoverPassword = new RecoverPasswordForm(userModel, databaseService);
		RecoverPassword.setVisible(true);
	}
	
	private void btnLoginActionPerformed(ActionEvent e) {
		
		System.out.println("LOGIN");
		String usr_name = new String(txtField_username.getText());
		String usr_password = new String(passwordField.getPassword());
		
		//CHECK CREDENTIAL AND GET USER INFO
		databaseService.loginUserFromUsername(usr_name, usr_password);
			
		uiController.setEnableLoginButton();
		
		if(isCredientialCorrect){	
			lblErrorLogin.setVisible(false);
			dispose();
			JFrame PopUp = new PopUpForm(userModel, databaseService, "Connexion réussi ");
			PopUp.setVisible(true);
		}
		else 
			lblErrorLogin.setVisible(true);
		
	}
	
	private void checkCredential() {
		if(userModel.getIsAuthenticated())
			isCredientialCorrect = true;
		else
			isCredientialCorrect = false;
	}
	
	
	private void changeStateCreateAccountButton(boolean state) {
		this.btnCreate.setEnabled(state);
	}
	
	
	public void propertyChange(PropertyChangeEvent evt) {
    	checkCredential();
    	
    	if (evt.getPropertyName() == "createAccountButtonStateChange") {
 
    		if(evt.getNewValue().equals(true)) {
				changeStateCreateAccountButton(true);
			}
			else {
				changeStateCreateAccountButton(false);
			}
    	}
    }

	
	
	}
	
