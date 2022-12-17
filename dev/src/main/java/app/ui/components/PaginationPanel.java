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
	private int currentPageNumber = 1;

	public PaginationPanel(ComicVineService comicVineService) {
		comicVineService.addPropertyChangeListener(this);
		this.comicVineService = comicVineService;
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(CustomColor.WhiteCloud);
		this.setBounds(200, 120, 850, 30);
		this.setLayout(null);

		// * Page Number
		pageNumberLabel = new JLabel("0");
		pageNumberLabel.setFont(new Font("Roboto", Font.BOLD, 22));

		pageNumberLabel.setBounds(420, 0, 100, 30);
		pageNumberLabel.setVisible(true);

		// * Previous Page
		prevPageButton = new DefaultButton("Previous", CustomColor.Red, 16, true);
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
		nextPageButton = new DefaultButton("Next", CustomColor.Red, 16, true);
		nextPageButton.setBounds(730, 0, 100, 30);
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
