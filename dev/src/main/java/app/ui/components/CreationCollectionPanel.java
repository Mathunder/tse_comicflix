package app.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.PlainDocument;

import app.helpers.DocumentSizeFilter;
import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;
import app.ui.themes.CustomColor;

public class CreationCollectionPanel extends JPanel implements PropertyChangeListener{
	
	private UserModel userModel;
	private DatabaseService databaseService;
	
	private DefaultButton btn_addCollection;
	private JTextField txtFld_collectionName;
	private JLabel lbl_enterCollectionName;
	private JLabel lbl_error;
	
	private DefaultComboBox collectionBox;
	private DefaultButton btn_removeCollection;
	private JLabel lbl_removeCollection;
	
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel model;
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CreationCollectionPanel(UserModel um, DatabaseService dbS) {
		this.userModel = um;
		this.databaseService = dbS;
		
		this.userModel.addPropertyChangeListener(this);
		
		// ComboBox collection
		String[] options = {"Vide"};
	    model = new DefaultComboBoxModel(options);
		
		initComponent();
	}
	
	private void initComponent() {
		setBackground(CustomColor.WhiteCloud);
		SpringLayout springLayout = new SpringLayout();
		
		btn_addCollection = new DefaultButton("Créer", CustomColor.Red, 16, true);
		btn_addCollection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_addCollectionActionPerformed(evt);
			}
		});
		txtFld_collectionName = new JTextField();
		// Limit the lenght of the text in the field at 10 characters
		PlainDocument doc = (PlainDocument) txtFld_collectionName.getDocument();
		doc.setDocumentFilter(new DocumentSizeFilter(12));
		
		lbl_enterCollectionName = new JLabel("Créer une collection : ");
		lbl_error = new JLabel("Nom incorrect ou déjà utilisé !");
		lbl_error.setVisible(false);
		lbl_error.setForeground(CustomColor.Red);
		
		// ADD COLLECTION ZONE
		
		//TextField collection 
		springLayout.putConstraint(SpringLayout.NORTH, txtFld_collectionName, 50, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, txtFld_collectionName, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, txtFld_collectionName, -75, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, txtFld_collectionName, 150, SpringLayout.WEST, this);
		add(txtFld_collectionName);
		
		//Button add
		springLayout.putConstraint(SpringLayout.NORTH, btn_addCollection, 10, SpringLayout.SOUTH, txtFld_collectionName);
		springLayout.putConstraint(SpringLayout.WEST, btn_addCollection, 70, SpringLayout.WEST, txtFld_collectionName);
		springLayout.putConstraint(SpringLayout.SOUTH, btn_addCollection, 70, SpringLayout.NORTH, txtFld_collectionName);
		springLayout.putConstraint(SpringLayout.EAST, btn_addCollection, 0, SpringLayout.EAST, txtFld_collectionName);
		add(btn_addCollection);
		
		//JLabel collection 
		springLayout.putConstraint(SpringLayout.NORTH, lbl_enterCollectionName, 25, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lbl_enterCollectionName, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lbl_enterCollectionName, -100, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, lbl_enterCollectionName, 150, SpringLayout.WEST, this);
		add(lbl_enterCollectionName);
		
		
		//JLabel error 
		springLayout.putConstraint(SpringLayout.NORTH, lbl_error, 0, SpringLayout.NORTH, txtFld_collectionName);
		springLayout.putConstraint(SpringLayout.WEST, lbl_error, 15, SpringLayout.EAST, txtFld_collectionName);
		springLayout.putConstraint(SpringLayout.SOUTH, lbl_error, 0, SpringLayout.SOUTH, txtFld_collectionName);
		springLayout.putConstraint(SpringLayout.EAST, lbl_error, 300, SpringLayout.EAST, txtFld_collectionName);
		add(lbl_error);
		
		
		//REMOVE COLLECTION ZONE
		//Button
		btn_removeCollection = new DefaultButton("Supprimer /!\\", CustomColor.Red, 16, true);
		btn_removeCollection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_removeCollectionActionPerformed(evt);
			}
		});
		
		collectionBox = new DefaultComboBox(new ArrayList<>());
		collectionBox.setModel(model);
		
		//Label
		lbl_removeCollection = new JLabel("Supprimer une collection :");
		
		//JComboBox collection 
		springLayout.putConstraint(SpringLayout.NORTH, collectionBox, 50, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, collectionBox, -200, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, collectionBox, -75, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, collectionBox, -30, SpringLayout.EAST, this);
		add(collectionBox);
		
		//Button remove
		springLayout.putConstraint(SpringLayout.NORTH, btn_removeCollection, 10, SpringLayout.SOUTH, collectionBox);
		springLayout.putConstraint(SpringLayout.WEST, btn_removeCollection, 0, SpringLayout.WEST, collectionBox);
		springLayout.putConstraint(SpringLayout.SOUTH, btn_removeCollection, 70, SpringLayout.NORTH, collectionBox);
		springLayout.putConstraint(SpringLayout.EAST, btn_removeCollection, 0, SpringLayout.EAST, collectionBox);
		add(btn_removeCollection);
		
		//JLabel 
		springLayout.putConstraint(SpringLayout.NORTH, lbl_removeCollection, 25, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lbl_removeCollection, -200, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lbl_removeCollection, -100, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, lbl_removeCollection, -20, SpringLayout.EAST, this);
		add(lbl_removeCollection);;
		
		setLayout(springLayout);
	}
	
	public void btn_addCollectionActionPerformed(ActionEvent evt) {
		
		String collectionName = txtFld_collectionName.getText();
		txtFld_collectionName.setText("");
		
		//If name not already exist and name != empty
		if(!databaseService.checkIfCollectionNameExist(collectionName, userModel.getUser().getId()) && !collectionName.equals("")) {
			//call database to add collection 
			System.out.println("NEW COLLLECTION " + collectionName + " ADDED !");
			lbl_error.setVisible(false);
			databaseService.createNewCollection(collectionName, userModel.getUser());
		}
		else
			lbl_error.setVisible(true);
	}
	
	public void btn_removeCollectionActionPerformed(ActionEvent evt) {
		String deleteCollectionName = collectionBox.getSelectedItem().toString();
		
		//Call database to remove collection
		if(databaseService.checkIfCollectionNameExist(deleteCollectionName, userModel.getUser().getId())) {
			databaseService.removeCollection(deleteCollectionName, userModel.getUser());
			System.out.println("COLLECTION " + deleteCollectionName + " REMOVED !");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updateComboBoxList() {
		// ComboBox collection
		//Change the model
		model = new DefaultComboBoxModel<>(databaseService.getAllCollectionNamesFromUser(userModel.getUser().getId()).toArray());
		collectionBox.setModel(model);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == "userChange") {
			updateComboBoxList();
		}
		else if(evt.getPropertyName() == "collectionListChange") {
			if(evt.getNewValue() == "add")
				System.out.println("Collection change [add] (CreationPanel)");
			else if(evt.getNewValue() == "remove")
				System.out.println("Collection change [remove] (CreationPanel)");

			updateComboBoxList();
		}
	}

}
