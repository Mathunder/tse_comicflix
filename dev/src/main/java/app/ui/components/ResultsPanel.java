package app.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import app.models.UserModel;
import app.services.ComicVineService;
import app.services.DatabaseService;
import app.ui.themes.CustomColor;

/**
 * Render a list of Results with a label on the top of it
 *
 */
@SuppressWarnings("serial")
public abstract class ResultsPanel extends JPanel implements PropertyChangeListener {
	JPanel resultsList;

	public ResultsPanel(String resultType, UserModel userModel, ComicVineService comicVineService,
			DatabaseService databaseService) {
		comicVineService.addPropertyChangeListener(this);
		userModel.addPropertyChangeListener(this);

		// Panel Styles
		this.setBackground(CustomColor.WhiteCloud);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBounds(0, 0, 1350, 900);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel resultTypeLabel = new JLabel(resultType);
		// Label Styles
		resultTypeLabel.setFont(new Font("Segoe UI Emoji", 1, 14)); // NOI18N
		resultTypeLabel.setForeground(new Color(46, 46, 46));
		resultTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultTypeLabel.setText(resultType);
		resultTypeLabel.setMaximumSize(new Dimension(120, 50));
		resultTypeLabel.setPreferredSize(new Dimension(120, 50));
		// Panel where we will show the List of results
		resultsList = new JPanel();
		resultsList.setBorder(BorderFactory.createEmptyBorder());
		resultsList.setLayout(new GridLayout(2, 5, 0, 40));
		resultsList.setBackground(CustomColor.WhiteCloud);
		// Adding All Elements to the Main Panel
		this.add(resultTypeLabel);
		this.add(resultsList);
		this.setVisible(false);

	}

}
