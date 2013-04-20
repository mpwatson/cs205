package objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.StringTokenizer;

/**
 * A text box
 * @author Adam
 *
 */
public class TextBox {
	public static Font defaultFont = new Font("SansSerif", Font.PLAIN, 24);
	public static Font menuFont = new Font("Rockwell Condensed", Font.PLAIN, 60);
	public static Font solidFont = new Font("ModMatrix", Font.PLAIN, 80);
	public static Font introFont = new Font("ModMatrix", Font.PLAIN, 50);
	public static Font bigFont = new Font("ModMatrix", Font.PLAIN, 120);
	
	public String text = "";
	public Font font = defaultFont;
	public int x;
	public int y;
	public Color color = new Color(0, 0, 0);
	
	public TextBox(){
		
	}
	
	public void draw(Graphics g){
		g.setFont(font);
		g.setColor(color);
		if(text.indexOf("/") < 0){
			g.drawString(text, x, y + font.getSize());
			return;
		}
		StringTokenizer parser = new StringTokenizer(text, "/", false);
		int i = 1;
		while(parser.hasMoreTokens()){
			g.drawString(parser.nextToken(), x, y + (i * font.getSize()));
			i++;
		}
	}
	
	public TextBox(String iText, Font iFont, int iX, int iY, Color iColor){
		text = iText;
		font = iFont;
		x = iX;
		y = iY;
		color = iColor;
	}
}
