package app.ui.themes;

import java.awt.Color;

public enum CustomColor {
	Red("#AC0101"),CrimsonRed("#790000"),DarkRed("#5F0000"),WhiteCloud("#F9F6F1"),
	LightGray("#DCDAD5"),Gray("#BEBDB8"),DarkGray("#83847F"),Black("#0C120C");

	private String ColorHexValue;
	
	CustomColor(String ColorHexValue) {
		if(!ColorHexValue.contains("#")) {
			this.ColorHexValue = "#" + ColorHexValue;
		}
		else this.ColorHexValue = ColorHexValue;
	}

	public Color getColor() {
		return Color.decode(ColorHexValue);
	}
	
	public String getColorHexValue() {
		return ColorHexValue;
	}
}
