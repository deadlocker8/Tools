package fontAwesome;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FontIcon extends Label {

	public static final String STYLE_CLASS = "fonticon";
	private static String FONT_FILE = "fontAwesome/FontAwesome.otf";
	
	private String fontFile = FONT_FILE;

	public static void setDefaultFontFile(String file) {
		FONT_FILE = file;
	}

	private int size = 14;
	private Font font;
	private Color color;

	public FontIcon(FontIconType type) {
		this(new FontIconType[] { type });
	}

	public FontIcon(FontIconType... types) {
		this(FONT_FILE, types);
	}

	public FontIcon(FontIconType type, String fontFile) {
		this(new FontIconType[] { type });
	}

	public FontIcon(String fontFile, FontIconType... types) {
		this.fontFile = fontFile; 
		setIcons(types);
		loadFont();
		getStyleClass().remove("label");
		getStyleClass().add(STYLE_CLASS);
		
	}

	public void setIcons(FontIconType... types) {
		StringBuilder text = new StringBuilder();
		for (FontIconType type : types) {
			text.append(type.getChar());
		}
		setText(text.toString());
	}

	public void loadFont() {
		font = Font.loadFont(getClass().getClassLoader().getResourceAsStream(fontFile), size);
		setFont(font);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
		loadFont();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		setTextFill(color);
	}
}
