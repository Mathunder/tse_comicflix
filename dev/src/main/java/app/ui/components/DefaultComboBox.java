package app.ui.components;

import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicComboBoxUI;

import app.ui.themes.CustomColor;

public class DefaultComboBox extends JComboBox {

	@SuppressWarnings("unchecked")
	public DefaultComboBox(List<String> items) {
		super(items.toArray());
		
		this.setUI(new BasicComboBoxUI());
		this.setBackground(CustomColor.Gray);
		this.setForeground(CustomColor.WhiteCloud);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CustomColor.LightGray));
		this.setEditable(false);
		this.setFont(new Font("Tahoma", Font.PLAIN,12));
		this.setFocusable(false);
		
	}
}
