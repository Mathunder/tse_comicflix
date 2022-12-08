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
	private JButton prevPageButton;
	private JButton nextPageButton;
	private JLabel pageNumberLabel;
	protected ComicVineService comicVineService;
	private int currentPageNumber = 1;

	public PaginationPanel(ComicVineService comicVineService) {
		comicVineService.addPropertyChangeListener(this);
		this.comicVineService = comicVineService;
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(CustomColor.WhiteCloud);
//		this.setBackground(CustomColor.DarkRed);
		this.setBounds(250, 120, 1250, 30);
		this.setLayout(null);

		// * Page Number
		pageNumberLabel = new JLabel("0");
		pageNumberLabel.setFont(new Font("Roboto", Font.BOLD, 22));

		pageNumberLabel.setBounds(600, 0, 100, 30);
		pageNumberLabel.setVisible(true);

		// * Previous Page
		prevPageButton = new JButton("Previous");
		prevPageButton.setBounds(10, 0, 100, 30);
		prevPageButton.setVisible(false);
		prevPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<ComicVineSearchFilter> filters = new ArrayList<>();
				filters.add(ComicVineSearchFilter.CHARACTER);
				comicVineService.search(comicVineService.getKeyword(), filters, comicVineService.getLimit(),
						 --currentPageNumber);
			}
		});
		// * Next Page
		nextPageButton = new JButton("Next");
		nextPageButton.setBounds(1130, 0, 100, 30);
		nextPageButton.setVisible(false);
		nextPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<ComicVineSearchFilter> filters = new ArrayList<>();
				filters.add(ComicVineSearchFilter.CHARACTER);
				comicVineService.search(comicVineService.getKeyword(), filters, comicVineService.getLimit(),
						 ++currentPageNumber);
			}
		});
		// * Add Components
		this.add(pageNumberLabel);
		this.add(prevPageButton);
		this.add(nextPageButton);
		this.setVisible(false);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName() == "searchResults" && evt.getNewValue() != null) {

		}

		if (evt.getPropertyName() == "searchStatus") {
			this.setVisible(true);
			int currentOffset = comicVineService.getOffset();
			int totalResults = comicVineService.getTotalResults();

//			System.out.println(currentOffset);
//			System.out.println(totalResults);
			this.pageNumberLabel.setText(Integer.toString(currentPageNumber));
			if (evt.getNewValue() == ComicVineSearchStatus.FETCHING) {
		

			} else if (evt.getNewValue() == ComicVineSearchStatus.DONE) {
				if(totalResults != -1) {
					if (currentOffset - comicVineService.getLimit() + 1 <= 0) {
						this.prevPageButton.setVisible(false);
					} else {
						this.prevPageButton.setVisible(true);
					}
					if (currentOffset + comicVineService.getLimit() + 1 >= totalResults) {
						this.nextPageButton.setVisible(false);
					} else {
						this.nextPageButton.setVisible(true);
					
				}
	}

			}
			this.revalidate();
			this.repaint();
		}
	}

}
