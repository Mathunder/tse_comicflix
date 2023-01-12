package app.ui.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Console;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import app.models.UserModel;
import app.services.DatabaseService;
import app.ui.components.DefaultButton;
import app.ui.themes.CustomColor;

public class CreateAccount extends JFrame implements PropertyChangeListener {
	
	private JPanel contentPane;
	private JTextField txtField_firstName;
	private JTextField txtField_lastName;
	private JTextField txtField_username;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JTextField txtField_question;
	private JLabel lblErrorCreate;
	//Model
	protected UserModel userModel; 
	//Controller
	protected DatabaseService databaseService;
	
	
	/**
	 * Create the frame.
	 */
	public CreateAccount(UserModel um, DatabaseService dbS) {
		this.userModel = um;
		this.databaseService = dbS;
		this.userModel.addPropertyChangeListener(this);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\icon.png"));
		setTitle("Create Account");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(249, 246, 241));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblFirstName = new JLabel("First Name");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblFirstName, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblFirstName, 45, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblFirstName, 54, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblFirstName, -282, SpringLayout.EAST, contentPane);
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblFirstName);
		
		txtField_firstName = new JTextField();
		txtField_firstName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtField_firstName, 15, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtField_firstName, 45, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtField_firstName, 34, SpringLayout.EAST, lblFirstName);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtField_firstName, -34, SpringLayout.EAST, contentPane);
		contentPane.add(txtField_firstName);
		txtField_firstName.setColumns(1);
		
		JLabel lblLastName = new JLabel("Last Name");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblLastName, 0, SpringLayout.SOUTH, lblFirstName);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblLastName, 45, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblLastName, 54, SpringLayout.SOUTH, lblFirstName);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblLastName, -282, SpringLayout.EAST, contentPane);
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLastName);
		
		txtField_lastName = new JTextField();
		txtField_lastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtField_lastName, 13, SpringLayout.NORTH, lblLastName);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtField_lastName, 0, SpringLayout.WEST, txtField_firstName);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtField_lastName, -9, SpringLayout.SOUTH, lblLastName);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtField_lastName, 0, SpringLayout.EAST, txtField_firstName);
		contentPane.add(txtField_lastName);
		txtField_lastName.setColumns(1);
		
		JLabel lblUsername = new JLabel("Username");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUsername, 0, SpringLayout.SOUTH, lblLastName);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUsername, 45, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblUsername, 54, SpringLayout.SOUTH, lblLastName);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblUsername, -282, SpringLayout.EAST, contentPane);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblUsername);
		
		txtField_username = new JTextField();
		txtField_username.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtField_username, 13, SpringLayout.NORTH, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtField_username, 0, SpringLayout.WEST, txtField_lastName);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtField_username, -9, SpringLayout.SOUTH, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtField_username, 0, SpringLayout.EAST, txtField_lastName);
		contentPane.add(txtField_username);
		txtField_username.setColumns(1);
		
		JLabel lblPassword = new JLabel("Passorwd");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPassword, 0, SpringLayout.SOUTH, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPassword, 45, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblPassword, 54, SpringLayout.SOUTH, lblUsername);
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
		
		JLabel lblConfirmPassword = new JLabel("Confirm passorwd");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblConfirmPassword, 0, SpringLayout.SOUTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblConfirmPassword, 30, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblConfirmPassword, 54, SpringLayout.SOUTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblConfirmPassword, -282, SpringLayout.EAST, contentPane);
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblConfirmPassword);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.NORTH, confirmPasswordField, 13, SpringLayout.NORTH, lblConfirmPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, confirmPasswordField, 0, SpringLayout.WEST, passwordField);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, confirmPasswordField, -9, SpringLayout.SOUTH, lblConfirmPassword);
		sl_contentPane.putConstraint(SpringLayout.EAST, confirmPasswordField, 0, SpringLayout.EAST, passwordField);
		contentPane.add(confirmPasswordField);
		
		JLabel lblQuestion = new JLabel("Dans quelle ville êtes-vous né(e) ?");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblQuestion, 0, SpringLayout.SOUTH, lblConfirmPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblQuestion, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblQuestion, 54, SpringLayout.SOUTH, lblConfirmPassword);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblQuestion, -200, SpringLayout.EAST, contentPane);
		lblQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblQuestion);
		
		txtField_question = new JTextField();
		txtField_question.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtField_question, 13, SpringLayout.NORTH, lblQuestion);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtField_question, 5, SpringLayout.EAST, lblQuestion);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtField_question, -9, SpringLayout.SOUTH, lblQuestion);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtField_question, 0, SpringLayout.EAST, confirmPasswordField);
		contentPane.add(txtField_question);
		txtField_question.setColumns(1);
		
		lblErrorCreate = new JLabel("");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblErrorCreate, 0, SpringLayout.SOUTH, lblQuestion);
		sl_contentPane.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblErrorCreate, 225, SpringLayout.WEST, contentPane);
		lblErrorCreate.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblErrorCreate.setForeground(Color.RED);
		lblErrorCreate.setHorizontalTextPosition(SwingConstants.CENTER);
		lblErrorCreate.setVisible(false);
		contentPane.add(lblErrorCreate);
		
		DefaultButton btnCreate = new DefaultButton("Create", CustomColor.CrimsonRed, 14, true);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCreateActionPerformed(e);
			}
		});
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCreate, -50, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnCreate, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCreate, -10, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCreate, -110, SpringLayout.EAST, contentPane);
		contentPane.add(btnCreate);
		
		DefaultButton btnCancel = new DefaultButton("Cancel", CustomColor.Gray, 14, true);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelActionPerformed(e); 				
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnCreate);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCancel, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnCancel, 0, SpringLayout.SOUTH, btnCreate);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancel, 110, SpringLayout.WEST, contentPane);
		contentPane.add(btnCancel);
	}
	
	private void btnCancelActionPerformed(ActionEvent e) {
		dispose();
	}
	
	private void btnCreateActionPerformed(ActionEvent e) {
		
		if ( !txtField_firstName.getText().isEmpty() && !txtField_lastName.getText().isEmpty() 
				&& !txtField_username.getText().isEmpty() && !String.valueOf(passwordField.getPassword()).isEmpty()
				&& !String.valueOf(confirmPasswordField.getPassword()).isEmpty()
				&& !txtField_question.getText().isEmpty())
		{
			if (String.valueOf(passwordField.getPassword()).contentEquals(String.valueOf(confirmPasswordField.getPassword())))
			{
				if (databaseService.verifUsername(txtField_username.getText()))
				{
					databaseService.addNewUserAccount(txtField_firstName.getText(), txtField_lastName.getText(), 
							txtField_username.getText(), String.valueOf(passwordField.getPassword()), txtField_question.getText() );
					dispose();
				}
				else
				{
					lblErrorCreate.setText("Username already exist ");
					lblErrorCreate.setVisible(true);
				}
			}
			else
			{
				lblErrorCreate.setText("Passwords are not the same ");
				lblErrorCreate.setVisible(true);
			}
				
		}
		else
		{
			lblErrorCreate.setText("A field is empty ");
			lblErrorCreate.setVisible(true);
		}
		
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
    }

}
