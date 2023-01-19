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

public class PopUpForm extends JFrame implements PropertyChangeListener{
	
	private JPanel contentPane;
	//Model
	protected UserModel userModel; 
	//Controller
	protected DatabaseService databaseService;
	
	public PopUpForm(UserModel um, DatabaseService dbS, String label) {
		
		this.userModel = um;
		this.databaseService = dbS;
		this.userModel.addPropertyChangeListener(this);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\icon.png"));
		setTitle("Success");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 150);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(249, 246, 241));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
	
		JLabel lblPop = new JLabel(label);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPop, 15, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblPop, 150, SpringLayout.WEST, contentPane);
		lblPop.setFont(new Font("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, 14));
		lblPop.setForeground(Color.DARK_GRAY);
		lblPop.setHorizontalTextPosition(SwingConstants.CENTER);
		contentPane.add(lblPop);
		
		
		DefaultButton btnOk = new DefaultButton("Close", CustomColor.Black, 14, true);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOkActionPerformed(e); 				
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnOk, -50, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnOk, 70, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnOk, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnOk, -70, SpringLayout.EAST, contentPane);
		contentPane.add(btnOk);
	}
	
	private void btnOkActionPerformed(ActionEvent e) {
		dispose();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
}