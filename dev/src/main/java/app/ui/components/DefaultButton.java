package app.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.Constructor;

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
		
		
		this.setBorderPainted(true);
		this.setFocusPainted(false);
		if(this.isBordered) {
			this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CustomColor.LightGray));
		}
		
		this.setFont(new Font("Tahoma", Font.PLAIN,this.fontSize));
		this.setBackground(this.color);
		this.setForeground(Color.white);
		this.setVisible(true);
		
	}

}
