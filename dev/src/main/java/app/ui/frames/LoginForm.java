package app.ui.frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.SpringLayout;
import java.awt.Toolkit;

import app.entities.User;
import app.services.DatabaseService;
import app.ui.components.DefaultButton;
import app.ui.events.InterfaceMainFrame;
import app.ui.themes.CustomColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtField_username;
	private JPasswordField passwordField;

	private InterfaceMainFrame mainInterface;
	private DatabaseService database;
	/**
	 * Create the frame.
	 */
	public LoginForm(InterfaceMainFrame mi) {
		mainInterface = mi;
		database = new DatabaseService();
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\axant\\Documents\\_AxStore\\2-TELECOM\\FISE 2\\S7\\PROJET INFORMATIQUE\\GitLab_repo\\Info5\\dev\\src\\main\\resources\\icon.png"));
		setTitle("Login Form");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(249, 246, 241));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblUsername = new JLabel("Username");
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
		
		JLabel lblPassword = new JLabel("Password");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPassword, 0, SpringLayout.SOUTH, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPassword, 45, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblPassword, -147, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblPassword, -282, SpringLayout.EAST, contentPane);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPassword);
		
		DefaultButton btnLogin = new DefaultButton("Login", CustomColor.Red, 14, true);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLoginActionPerformed(e);
			}
		});
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLogin, 151, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnLogin, -60, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtField_username, -106, SpringLayout.NORTH, btnLogin);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnLogin, 230, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnLogin, -79, SpringLayout.EAST, contentPane);
		contentPane.add(btnLogin);
		
		DefaultButton btnCancel = new DefaultButton("Cancel", CustomColor.Gray, 14, true);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelActionPerformed(e); 				
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnLogin);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCancel, 66, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnCancel, -60, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancel, -50, SpringLayout.WEST, btnLogin);
		contentPane.add(btnCancel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.NORTH, passwordField, 13, SpringLayout.NORTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, txtField_username);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, passwordField, -9, SpringLayout.SOUTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.EAST, passwordField, 0, SpringLayout.EAST, txtField_username);
		contentPane.add(passwordField);
	}
	
	private void btnCancelActionPerformed(ActionEvent e) {
		dispose();
	}
	
	private void btnLoginActionPerformed(ActionEvent e) {
		
		String usr_name = new String(txtField_username.getText());
		String usr_password = new String(passwordField.getPassword());
		System.out.println(usr_name);
		System.out.println(usr_password);	
		
		//CHECK CREDENTIAL AND GET USER INFO
		User newUser = database.getUserFromUsername(usr_name,usr_password);
		
		if(newUser != null) {
			mainInterface.updateLoginInfo(newUser);
			dispose();
		}
		else 
			System.out.println("Username or password invalid !!!");
		
	}
}
