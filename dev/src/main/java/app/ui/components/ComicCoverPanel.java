package app.ui.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import app.dto.ResultDto;
import app.entities.Issue;
import app.entities.User;
import app.services.DatabaseService;
import app.ui.themes.CustomColor;

@SuppressWarnings("serial")
public class ComicCoverPanel extends JPanel{
	
	private BufferedImage imageBrute;
	private BufferedImage resizedImageBg;
	private DefaultButton button_fav;
	private DefaultButton button_read;
	
	private Issue issue;
	protected DatabaseService databaseService;
	private User user;
	
	public ComicCoverPanel(Issue issue, DatabaseService dbS, User u){
		super();
		this.issue = issue;
		this.databaseService = dbS;
		this.user = u;
		this.setToolTipText(returnToolTipText(issue));
		
		//Load a test image, resize and paint of the panel background
		try {
			imageBrute = ImageIO.read(new URL(this.issue.getImage().getMedium_url()));
		} catch (IOException e) {
			System.out.println("Problem load img");
			e.printStackTrace();
		}
		
		resizedImageBg = resizeBuffImage(imageBrute,206,310);
		
		setPreferredSize(new Dimension(206,310));
		setBackground(CustomColor.WhiteCloud);
		
		// Creation of the label title
		String str = "";
		if (issue.getName() == null) {
			try {
				str = issue.getVolume().getName() + " (" + issue.getIssue_number() + ")";
			} catch (NullPointerException e) {
				str = "";
			}
		} else {
			str = issue.getName();
		}
		JLabel titleLabel = new JLabel(titleUpdate(str, 13));
		titleLabel.setOpaque(true);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN,20));
		titleLabel.setBackground(CustomColor.DarkGray);
		titleLabel.setForeground(CustomColor.WhiteCloud); 
		titleLabel.setHorizontalTextPosition(JLabel.CENTER);
		titleLabel.setVerticalTextPosition(JLabel.BOTTOM);

		//Count the number of "<br>" in order to adapt the label height
		int labelHeight = (countOccurrences(titleLabel.getText(), "<br>"))*(-25);
		if(titleLabel.getText() != "")
			labelHeight -= 25;
		
		//Put constraint on Label 
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, titleLabel, labelHeight, SpringLayout.SOUTH, titleLabel);
		springLayout.putConstraint(SpringLayout.WEST, titleLabel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, titleLabel, this.resizedImageBg.getHeight(), SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, titleLabel, 0, SpringLayout.EAST, this);
		setLayout(springLayout);
		// Add the label to the panel
		add(titleLabel);

		if(this.user.getId() != 0)	{// Button favorite creation // \u2665 : unicode for full heart symbol
			button_fav = new DefaultButton(String.valueOf("\u2665"), CustomColor.Red, 24, true);
			
			button_fav.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	button_favActionPerformed(evt);
	            }
			});
			
			//Put Constraints on fav button
			springLayout.putConstraint(SpringLayout.NORTH, button_fav, -25 , SpringLayout.NORTH, titleLabel);
			springLayout.putConstraint(SpringLayout.WEST, button_fav, 0, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.SOUTH, button_fav, 0, SpringLayout.NORTH, titleLabel);
			springLayout.putConstraint(SpringLayout.EAST, button_fav, -140, SpringLayout.EAST, this);
			// Add buttons to the panel
			add(button_fav);
			
			//Read button
			button_read = new DefaultButton("Non lu", CustomColor.Red,16,true);
			
			button_read.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					button_readActionPerformed(evt);
				}
			});
			
			//Put Constraints on read button
			springLayout.putConstraint(SpringLayout.NORTH, button_read, -25, SpringLayout.NORTH, titleLabel);
			springLayout.putConstraint(SpringLayout.WEST, button_read, 70, SpringLayout.WEST, this);
			springLayout.putConstraint(SpringLayout.SOUTH, button_read, 0, SpringLayout.NORTH, titleLabel);
			springLayout.putConstraint(SpringLayout.EAST, button_read, -70, SpringLayout.EAST, this);
			// Add button to the panel
			add(button_read);
			
		}
		
	}
	
	//Refresh buttons state according to the current state of the user list (favorites and reading)
	public void refreshStateButtons(boolean state_fav, int state_read) {
		if(state_fav)
			button_fav.setColor(CustomColor.Green);
		else
			button_fav.setColor(CustomColor.Red);
		
		switch (state_read) {
			case 0:
				button_read.setColor(CustomColor.Red);
				button_read.setText("Non lu");
				break;
			case 1:
				button_read.setColor(CustomColor.Orange);
				button_read.setText("En cours");
				break;
			case 2:
				button_read.setColor(CustomColor.Green);
				button_read.setText("Lu");
				break;
			default:
				button_read.setColor(CustomColor.LightGray);
				button_read.setText("Error");
		}
	}
	
	//Refresh buttons state according to the current state of the user list (favorite only)
	public void refreshStateButtons(boolean state_fav) {
		
		if(state_fav)
			button_fav.setColor(CustomColor.Green);
		else
			button_fav.setColor(CustomColor.Red);

	}
	
	//Refresh buttons state according to the current state of the user list (reads only)
	public void refreshStateButtons(int state_read) {
		
		switch (state_read) {
			case 0:
				button_read.setColor(CustomColor.Red);
				button_read.setText("Non lu");
				break;
			case 1:
				button_read.setColor(CustomColor.Orange);
				button_read.setText("En cours");
				break;
			case 2:
				button_read.setColor(CustomColor.Green);
				button_read.setText("Lu");
				break;
			default:
				button_read.setColor(CustomColor.LightGray);
				button_read.setText("Error");
		}
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	    g.drawImage(resizedImageBg, 0, 0, null);
	}
	
	private void button_favActionPerformed(ActionEvent e) {
		
		if(button_fav.getColor() == CustomColor.Green) {
			button_fav.setColor(CustomColor.Red);
			
			//Delete issue favorite link in the database
			databaseService.removeOneUserFavorite(user, issue);
			
		}
		else {
			button_fav.setColor(CustomColor.Green);
			
			//Add issues in the database
			databaseService.addNewIssue(issue);
			
			//Add link between user and favorite issue
			databaseService.addNewUserFavorite(user, issue);
		}
	}
	
	
	private void button_readActionPerformed(ActionEvent e) {
		if(button_read.getColor() == CustomColor.Red) { //IF ISSUE NOT ALREADY READ OR READING OR READED
			button_read.setColor(CustomColor.Orange);
			button_read.setText("En cours");
			//Add issue to reading
			databaseService.addNewUserReading(user, issue);
		}
		else if(button_read.getColor() == CustomColor.Orange) { //IF ISSUE IS READING
			button_read.setColor(CustomColor.Green);
			button_read.setText("Lu");
			//Add issue to readed
			databaseService.addNewUserReaded(user, issue);
		}
		else if(button_read.getColor() == CustomColor.Green) { //IF ISSUE IS READED
			button_read.setColor(CustomColor.Red);
			button_read.setText("Pas lu");
			//Remove issue from read
			databaseService.removeUserRead(user, issue);
		}
	}
	
	private static BufferedImage resizeBuffImage(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	} 
	
	
	public String titleUpdate(String title, int n) {
		//If the title is too long, transformation into HTML and add of line break
		String titleDisplayed = new String("<html>");
		if(title == null) { return ""; 
		}
		if(title.length()>n) {

			for (int j=0;j<title.length();j++) {
				titleDisplayed = titleDisplayed.concat(String.valueOf(title.charAt(j)));
				if(j != 0 && (j % n == 0)) {
					for (int k=j+1;k<title.length();k++) {
						if(title.charAt(k) == ' ' || title.charAt(k) == '-') {
							titleDisplayed = titleDisplayed.concat("<br>");
							j = k;
							break;
						}
						else {
							titleDisplayed = titleDisplayed.concat(String.valueOf(title.charAt(k)));
						}
						j=k;
					}
				}
			}
			return titleDisplayed.concat("</html>");
		}
		else {
			return title;
		}
	}
	
	public static int countOccurrences(String str, String sub) {
	    int count = 0;
	    int index = 0;
	    while ((index = str.indexOf(sub, index)) != -1) {
	        ++count;
	        ++index;
	    }
	    return count;
	}
	
	private String returnToolTipText (Issue issue) {
		//Return the string which is going to be printed in the ToolTip
		// Print those API features : deck > description > aliases > name
		
		String deck = titleUpdate(issue.getDeck(), 30);
		if (deck.length() > 15) {
			return deck;
		}
		String description = titleUpdate(issue.getDescription(), 30);
		if (description.length() > 15 && description.indexOf("<p> Translates") == -1){
			if (description.indexOf("List") != -1) {
				return description.substring(0, description.indexOf("List"))+"</html>";
			}
			else if (description.length() > 450) {
				return description.substring(0, 450)+"</html>";
			}
			else {
				return description;
			}
		}
		String aliases = titleUpdate(issue.getAliases(), 30);
		if (aliases.length()>15) {
			return aliases;
		}
		else {
			return titleUpdate(issue.getName(), 30);
			
		}
	}
}
