package app.ui.components;

import java.awt.*;
import javax.swing.*;

public class LeftBarButton extends JButton{
	public LeftBarButton(String Text, String hexCode, int FontSize, boolean isBordered) {
		
		super(Text);		
		this.setBorderPainted(true);
		this.setFocusPainted(false);	
		if(isBordered)
			this.setBorder(BorderFactory.createMatteBorder(-1, 4, -1, -1, Color.darkGray));
		this.setFont(new Font("Arial", Font.PLAIN,FontSize));
		this.setBackground(Color.decode(hexCode));
		this.setForeground(Color.white);
		this.setVisible(true);

	}
	
	public void setBorderColorOnFocus() {
		this.setBorder(BorderFactory.createMatteBorder(-1, 4, -1, -1, Color.white));
	}
	
	public void setBorderColorOnUnfocus() {
		this.setBorder(BorderFactory.createMatteBorder(-1, 4, -1, -1, Color.darkGray));
	}
}
