package objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import misc.Vector2D;

/**
 * A generic game object that can hold arrays of images and text boxes
 * @author Adam
 *
 */
public class DisplayObject {
	private String name;
	
	private boolean visible = true;
	
	/**
	 * All of the images contained in the object
	 */
	private ArrayList<ImageEx> display;
	
	/**
	 * All of the text boxes contained in the object
	 */
	private ArrayList<TextBox> texts;
	
	/**
	 * Coordinates of the DisplayObject
	 */
	private Vector2D cords;
	
	public DisplayObject(){
		display = new ArrayList<ImageEx>();
		texts = new ArrayList<TextBox>();
		cords = new Vector2D(0, 0);
	}
	
	public DisplayObject(String initName, ImageEx initDisplay){
		name = initName;
		display = new ArrayList<ImageEx>();
		texts = new ArrayList<TextBox>();
		cords = new Vector2D(0, 0);
		display.add(initDisplay);
	}
	
	public DisplayObject(String initName, TextBox initText){
		name = initName;
		display = new ArrayList<ImageEx>();
		texts = new ArrayList<TextBox>();
		cords = new Vector2D(0, 0);
		texts.add(initText);
	}
	
	/**
	 * Draw all components of the object
	 * @param g
	 * @param camera
	 */
	public void draw(Graphics g, Vector2D camera){
		if(!visible) return;
		int x, y, width, height;
		
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform oldT = g2d.getTransform(), newT;
		
		/**
		 * FRAGILE CODE - DO NOT TOUCH ************************************
		 */
		for(int i = 0;i < display.size();i++){
			if((x = display.get(i).x + (int)cords.x - (int)camera.x) + (width = display.get(i).width()) < 0) continue;
			if((y = display.get(i).y + (int)cords.y - (int)camera.y) + (height = display.get(i).height()) < 0) continue;
			if(x > 1366) continue;
			if(y > 768) continue;
			(newT = (AffineTransform)oldT.clone()).rotate(display.get(i).rotation, x + (width / 2), y + (height / 2));
			g2d.setTransform(newT);
			g2d.drawImage(display.get(i).image, x, y, width, height, null);
		}
		g2d.setTransform(oldT);
		/**
		 * FRAGILE CODE - DO NOT TOUCH ************************************
		 */
		
		// Draw text boxes
		for(int i = 0;i < texts.size();i++){
			texts.get(i).draw(g);
		}
	}
	
	/**
	 * Determine if any images are touching a point
	 * This can be used for collision detection and simulation of physics
	 * @param testPt
	 * @return
	 */
	public boolean isInRange(Point testPt){
		for(int i = 0; i < display.size();i++){
			if(testPt.x >= display.get(i).x && testPt.y >= display.get(i).y && testPt.x <= (display.get(i).x + display.get(i).width()) && testPt.y <= (display.get(i).y + display.get(i).height())){
				return true;
			}
		}
		return false;
	}
	
	public Vector2D getCords(){
		return cords;
	}
	
	public void setCords(Vector2D newCords){
		cords = newCords;
	}
	
	public ImageEx getImage(int index){
		if(index >= display.size()) return null;
		return display.get(index);
	}
	
	public String getText(int index){
		if(index < 0 || index >= texts.size()) return null;
		return texts.get(index).text;
	}
	
	public void setText(String newTxt, int index){
		if(index < 0 || index >= texts.size()) return;
		texts.get(index).text = newTxt;
	}
	
	public void addImage(ImageEx newImage){
		display.add(newImage);
	}
	
	public void addText(String iText, Font iFont, int iX, int iY, Color iColor){
		texts.add(new TextBox(iText, iFont, iX, iY, iColor));
	}
	
	public void setVisible(boolean isVisible){
		visible = isVisible;
	}
	
	public void erase(){
		display = new ArrayList<ImageEx>();
		texts = new ArrayList<TextBox>();
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public String getName(){
		return name;
	}
}
