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
import app.ui.components.DefaultButton;
import app.ui.themes.CustomColor;

public class LoginForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtField_username;
	private JTextField txtField_password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm frame = new LoginForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginForm() {
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
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtField_username, 13, SpringLayout.NORTH, lblUsername);
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
		
		txtField_password = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.WEST, txtField_password, 180, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtField_username, 0, SpringLayout.WEST, txtField_password);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtField_username, -22, SpringLayout.NORTH, txtField_password);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtField_password, 13, SpringLayout.NORTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtField_password, -156, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtField_password, -34, SpringLayout.EAST, contentPane);
		contentPane.add(txtField_password);
		txtField_password.setColumns(1);
		
		DefaultButton btnLogin = new DefaultButton("Login", CustomColor.Red, 14, true);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLogin, 52, SpringLayout.SOUTH, txtField_password);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnLogin, 230, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnLogin, -60, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnLogin, -79, SpringLayout.EAST, contentPane);
		contentPane.add(btnLogin);
		
		DefaultButton btnCancel = new DefaultButton("Cancel", CustomColor.Gray, 14, true);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnLogin);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCancel, 66, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnCancel, -60, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancel, -50, SpringLayout.WEST, btnLogin);
		contentPane.add(btnCancel);
	}
}
