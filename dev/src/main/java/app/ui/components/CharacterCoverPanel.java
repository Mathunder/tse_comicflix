package app.ui.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import app.entities.VineCharacter;
import app.services.DatabaseService;
import app.ui.themes.CustomColor;

@SuppressWarnings("serial")
public class CharacterCoverPanel extends JPanel {

	
	private BufferedImage imageBrute;
	private BufferedImage resizedImageBg;

	
	private VineCharacter character;
	protected DatabaseService databaseService;

	
	public CharacterCoverPanel(VineCharacter character, DatabaseService dbS) throws Exception {
		super();
		
		this.character=character;
		this.databaseService = dbS;
		this.setToolTipText(returnToolTipText(character));
		
		
		//Load a test image, resize and paint of the panel background
		try {
			imageBrute = ImageIO.read(new URL(this.character.getImage().getMedium_url()));
		} catch (IOException e) {
			System.out.println("Problem load img");
			e.printStackTrace();
		}
		

		resizedImageBg = resizeBuffImage(imageBrute,206,310);
		
		setPreferredSize(new Dimension(206,310));
		
		// Création du label titre
		JLabel titleLabel = new JLabel(titleUpdate(character.getName(), 13));
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
		springLayout.putConstraint(SpringLayout.NORTH, titleLabel, labelHeight , SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, titleLabel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, titleLabel, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, titleLabel, 0, SpringLayout.EAST, this);
		setLayout(springLayout);
		// Add the label to the panel
		add(titleLabel);

		
	}
	

	

	
	
	
	
	@Override
	protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	    g.drawImage(resizedImageBg, 0, 0, null);
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
	
	private String returnToolTipText (VineCharacter character) {
		//Return the string which is going to be printed in the ToolTip
		// Print those API features : deck > description > aliases > name
		
		String deck = titleUpdate(character.getDeck(), 30);
		if (deck.length() > 15) {
			return deck;
		}
		String description = titleUpdate(character.getDescription(), 30);
		if (description.length() > 15){
			if (description.length() > 450) {
				return description.substring(0, 450);
			}
			else {
				return description;
			}
		}
		String aliases = titleUpdate(character.getAliases(), 30);
		if (aliases.length()>15) {
			return aliases;
		}
		else {
			return titleUpdate(character.getName(), 30);
			
		}
		
	}
}
