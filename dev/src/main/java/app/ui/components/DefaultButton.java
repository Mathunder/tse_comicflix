package app.ui.components;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import app.ui.themes.CustomColor;

public class DefaultButton extends JButton {

	private String text;
	private Color color;
	private int fontSize;
	private boolean isBordered;
	
	public DefaultButton(String text, Color color, int fontsize, boolean isbordered) {
		
		super(text);
		
		this.text = text;
		this.color = color;
		this.fontSize  = fontsize;
		this.isBordered = isbordered;
		
		
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		if(this.isBordered) {
			this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CustomColor.LightGray));
			this.setBorderPainted(true);
		}
		
		this.setFont(new Font("Tahoma", Font.PLAIN,this.fontSize));
		this.setBackground(this.color);
		this.setForeground(Color.white);
		this.setVisible(true);
		
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
		this.setBackground(this.color);
	}

}
