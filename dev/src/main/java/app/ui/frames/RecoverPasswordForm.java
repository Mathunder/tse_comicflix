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

public class RecoverPasswordForm extends JFrame implements PropertyChangeListener{
	
	private JPanel contentPane;
	private JTextField txtField_question;
	private JTextField txtField_username;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JLabel lblErrorCreate;
	//Model
	protected UserModel userModel; 
	//Controller
	protected DatabaseService databaseService;
	
	public RecoverPasswordForm(UserModel um, DatabaseService dbS) {
		this.userModel = um;
		this.databaseService = dbS;
		this.userModel.addPropertyChangeListener(this);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\icon.png"));
		setTitle("Change Password");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 350);
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
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtField_username, 15, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtField_username, 45, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtField_username, 34, SpringLayout.EAST, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtField_username, -34, SpringLayout.EAST, contentPane);
		contentPane.add(txtField_username);
		txtField_username.setColumns(1);
		
		JLabel lblQuestion = new JLabel("Dans quelle ville êtes-vous né(e) ?");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblQuestion, 0, SpringLayout.SOUTH, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblQuestion, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblQuestion, 54, SpringLayout.SOUTH, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblQuestion, -200, SpringLayout.EAST, contentPane);
		lblQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblQuestion);
		
		txtField_question = new JTextField();
		txtField_question.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtField_question, 13, SpringLayout.NORTH, lblQuestion);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtField_question, 5, SpringLayout.EAST, lblQuestion);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtField_question, -9, SpringLayout.SOUTH, lblQuestion);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtField_question, 0, SpringLayout.EAST, txtField_username);
		contentPane.add(txtField_question);
		txtField_question.setColumns(1);
		
		JLabel lblPassword = new JLabel("New password");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPassword, 0, SpringLayout.SOUTH, lblQuestion);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPassword, 45, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblPassword, 54, SpringLayout.SOUTH, lblQuestion);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblPassword, -282, SpringLayout.EAST, contentPane);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.NORTH, passwordField, 13, SpringLayout.NORTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, passwordField, 34, SpringLayout.EAST, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, passwordField, -9, SpringLayout.SOUTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.EAST, passwordField, 0, SpringLayout.EAST, txtField_question);
		contentPane.add(passwordField);
		passwordField.setColumns(1);
		
		JLabel lblConfirmPassword = new JLabel("Confirm new password");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblConfirmPassword, 0, SpringLayout.SOUTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblConfirmPassword, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblConfirmPassword, 54, SpringLayout.SOUTH, lblPassword);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblConfirmPassword, -270, SpringLayout.EAST, contentPane);
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
		
		lblErrorCreate = new JLabel("");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblErrorCreate, 0, SpringLayout.SOUTH, lblConfirmPassword);
		sl_contentPane.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblErrorCreate, 225, SpringLayout.WEST, contentPane);
		lblErrorCreate.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblErrorCreate.setForeground(Color.RED);
		lblErrorCreate.setHorizontalTextPosition(SwingConstants.CENTER);
		lblErrorCreate.setVisible(false);
		contentPane.add(lblErrorCreate);
		
		
		
		DefaultButton btnChange = new DefaultButton("Change", CustomColor.CrimsonRed, 14, true);
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnChangeActionPerformed(e);
			}
		});
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnChange, -50, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnChange, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnChange, -10, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnChange, -110, SpringLayout.EAST, contentPane);
		contentPane.add(btnChange);
		
		DefaultButton btnCancel = new DefaultButton("Cancel", CustomColor.Black, 14, true);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelActionPerformed(e); 				
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnChange);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCancel, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnCancel, 0, SpringLayout.SOUTH, btnChange);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancel, 110, SpringLayout.WEST, contentPane);
		contentPane.add(btnCancel);
	}
	
	private void btnCancelActionPerformed(ActionEvent e) {
		dispose();
	}
	
	private void btnChangeActionPerformed(ActionEvent e) {
		if ( !txtField_username.getText().isEmpty() && !txtField_question.getText().isEmpty()
				&& !String.valueOf(passwordField.getPassword()).isEmpty()
				&& !String.valueOf(confirmPasswordField.getPassword()).isEmpty())
		{
			if (String.valueOf(passwordField.getPassword()).contentEquals(String.valueOf(confirmPasswordField.getPassword())))
			{
				if (!databaseService.verifUsername(txtField_username.getText()))
				{
					if (databaseService.verifQuestion(txtField_username.getText(), txtField_question.getText()))
					{
						databaseService.updatePassword(String.valueOf(passwordField.getPassword()), txtField_username.getText());
						dispose();
						JFrame PopUp = new PopUpForm(userModel, databaseService, "successful change password ");
						PopUp.setVisible(true);
					}
					else
					{
						lblErrorCreate.setText("Annswer to the question is not right ");
						lblErrorCreate.setVisible(true);
					}
				}
				else
				{
					lblErrorCreate.setText("Non-existent username ");
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
