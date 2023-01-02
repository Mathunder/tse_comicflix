package app.ui.components;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.helpers.ComicVineSearchFilter;
import app.helpers.ComicVineSearchStatus;
import app.services.ComicVineService;
import app.ui.frames.MainFrame;
import app.ui.themes.CustomColor;
import lombok.Data;

@Data
@SuppressWarnings("serial")
public class PaginationPanel extends JPanel implements PropertyChangeListener {
	private DefaultButton prevPageButton;
	private DefaultButton nextPageButton;
	private JLabel pageNumberLabel;
	protected ComicVineService comicVineService;

	public PaginationPanel(ComicVineService comicVineService) {
		comicVineService.addPropertyChangeListener(this);
		this.comicVineService = comicVineService;
		// Styles
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(CustomColor.WhiteCloud);
		this.setBounds(200, 120, 850, 30);
		this.setLayout(null);

		// * Page Number
		pageNumberLabel = new JLabel("0/sure");
		pageNumberLabel.setFont(new Font("Roboto", Font.BOLD, 22));

		pageNumberLabel.setBounds(420, 0, 100, 30);
		pageNumberLabel.setVisible(true);

		// * Previous Page
		prevPageButton = new DefaultButton("Previous", CustomColor.Red, 16, true);
		prevPageButton.setBounds(10, 0, 100, 30);
		prevPageButton.setVisible(false);
		prevPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comicVineService.previousSearch();
			}
		});
		// * Next Page
		nextPageButton = new DefaultButton("Next", CustomColor.Red, 16, true);
		nextPageButton.setBounds(730, 0, 100, 30);
		nextPageButton.setVisible(false);
		nextPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comicVineService.nextSearch();

			}
		});
		// * Add Components
		this.add(pageNumberLabel);
		this.add(prevPageButton);
		this.add(nextPageButton);
		this.setVisible(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if (evt.getPropertyName() == "currentPageChanged" || evt.getPropertyName() == "totalNumberOfPagesChanged") {
			
			
			this.pageNumberLabel.setText(Integer.toString((int)comicVineService.getCurrentPage()) + " / "
					+ Integer.toString((int)comicVineService.getTotalNumberOfPages()));
			if (this.comicVineService.getCurrentPage() + 1 > this.comicVineService.getTotalNumberOfPages()) {
				this.nextPageButton.setVisible(false);
			} else {
				this.nextPageButton.setVisible(true);

			}
			
			if (this.comicVineService.getCurrentPage() - 1 >= 1) {
				this.prevPageButton.setVisible(true);
			} else {
				this.prevPageButton.setVisible(false);

			}
			this.setVisible(true);
			this.repaint();
			this.revalidate();
			
		}
	}

}
