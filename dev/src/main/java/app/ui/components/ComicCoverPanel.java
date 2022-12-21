package app.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import app.entities.Issue;
import app.entities.User;
import app.services.DatabaseService;
import app.ui.themes.CustomColor;

public class ComicCoverPanel extends JPanel{
	
	private BufferedImage imageBrute;
	private BufferedImage resizedImageBg;
	private DefaultButton button_fav;
	private Issue issue;
	private int userId;
	
	public ComicCoverPanel(Issue issue, boolean isFavorite, int uId){
		
		super();
		this.issue=issue;
		this.userId = uId;
		
		//Load a test image, resize and paint of the panel background
		try {
			imageBrute = ImageIO.read(new URL(this.issue.getImage().getMedium_url()));
		} catch (IOException e) {
			System.out.println("Problem load img");
			e.printStackTrace();
		}
		
		resizedImageBg = resizeBuffImage(imageBrute,206,310);
		
		setPreferredSize(new Dimension(206,310));
		
		// Création du label titre
		JLabel titleLabel = new JLabel(" " + this.issue.getName() + " ");
		titleLabel.setOpaque(true);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN,20));
		titleLabel.setBackground(CustomColor.Black);
		titleLabel.setForeground(CustomColor.WhiteCloud); 
		titleLabel.setHorizontalTextPosition(JLabel.CENTER);
		titleLabel.setVerticalTextPosition(JLabel.BOTTOM);

		// Add the label to the panel
		add(titleLabel);

		if(userId != 0)	{// Button favorite creation // \u2665 : unicode for full heart symbol
			button_fav = new DefaultButton(String.valueOf("\u2665"), CustomColor.Red, 24, true);
			
			//Change color if Comics already in user favorite
			if(isFavorite)
				button_fav.setColor(CustomColor.Green);
			
			button_fav.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	button_favActionPerformed(evt);
	            }
			});
			// Add buttons to the panel
			add(button_fav);
		}
		
	}
	
	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	    g.drawImage(resizedImageBg, 0, 0, null);
	}
	
	private void button_favActionPerformed(ActionEvent e) {
		
		DatabaseService database = new DatabaseService();
		
		if(button_fav.getColor() == CustomColor.Green) {
			button_fav.setColor(CustomColor.Red);
			
			//Delete issue favorite link in the database
			database.removeOneUserFavorite(userId, issue.getId());
			
		}
		else {
			button_fav.setColor(CustomColor.Green);
			
			if(issue.getIssue_number() == null) { //IF CHARACTER
				issue.setIssue_number("0");
			}
			
			//Add issues in the database
			database.addNewIssue(issue.getId(), Integer.parseInt(issue.getIssue_number()), issue.getName(), issue.getApi_detail_url(), issue.getImage().getMedium_url(), "21/12/2022");
			
			//Add link between user and favorite issue
			database.addNewUserFavorite(userId, issue.getId());
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
	
}
