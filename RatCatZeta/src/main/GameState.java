package main;

import gui.GameApplet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import misc.UpdateMode;
import misc.Vector2D;

import objects.DisplayObject;
import objects.ImageEx;
import objects.TextBox;


/**
 * Generic game state structure
 */
public abstract class GameState {
	/**
	 * List of objects currently present in the game state
	 */
	protected ArrayList<DisplayObject> display;
	
	/**
	 * Camera object allows the movement of every object on the screen, creating the effect of
	 * a moving camera.
	 */
	public Vector2D camera = new Vector2D(0, 0);
	
	/**
	 * Returns the ID number associated with the state
	 * @return
	 */
	public abstract float stateID();
	
	public GameState(){
		display = new ArrayList<DisplayObject>();
		initialize();
	}
	
	/**
	 * Called when the applet is initialized
	 */
	public abstract void initialize();
	
	/**
	 * Called upon entering the state
	 */
	public abstract void entered();
	
	/**
	 * Update the game state
	 * @param mousePos
	 * @param mode
	 */
	public void update(Point mousePos, UpdateMode mode){
		if(mode.equals(UpdateMode.NEW_FRAME)){
			handleFrame(mousePos);
			return;
		}
		if(mode.equals(UpdateMode.MOVED_TO)){
			entered();
			return;
		}
		if(mode.equals(UpdateMode.MOUSE_CLICKED)){
			handleMouseClick(mousePos);
			return;
		}
		if(mode.equals(UpdateMode.EXIT)){
			handleExit();
			return;
		}
	}
	
	/**
	 * Called on each frame to update game logic
	 * @param mousePos
	 */
	public abstract void handleFrame(Point mousePos);
	
	/**
	 * Called on each mouse click to handle game logic
	 * @param mousePos
	 */
	public abstract void handleMouseClick(Point mousePos);
	
	/**
	 * Handle leaving the current game state
	 */
	public void handleExit(){
		closeState();
	}
	
	/**
	 * Called upon the state being left
	 */
	public abstract void closeState();
	
	/**
	 * Draw all objects, first object drawn first (furthest back)
	 * @param g
	 */
	public void draw(Graphics g){
		g.clearRect(0, 0, (int)GameApplet.dimensions.x, (int)GameApplet.dimensions.y);
		
		for(int i = 0;i < display.size();i++){
			display.get(i).draw(g, camera);
		}
	}
	
	/**
	 * Remove all objects
	 */
	public void resetObjects(){
		display.clear();
	}
	
	/**
	 * Add an object to the display
	 * @param newObject
	 */
	public void addObject(DisplayObject newObject){
		display.add(newObject);
	}
	
	/**
	 * Add an object to the display - Files are read here
	 * @param imgFile
	 * @param x
	 * @param y
	 */
	public void addObject(String iName, String imgFile, int x, int y){
		try{
			display.add(new DisplayObject(iName, new ImageEx(new ImageIcon(getClass().getResource("/" + imgFile)).getImage(), x, y)));
		}catch(NullPointerException e){
			System.out.println(imgFile);
			System.out.println(Thread.currentThread().getStackTrace());
		}
	}
	
	/**
	 * Add a text object to the display
	 * @param iName
	 * @param text
	 * @param font
	 * @param x
	 * @param y
	 */
	public void addObject(String iName, String text, Font font, int x, int y, Color color){
		display.add(new DisplayObject(iName, new TextBox(text, font, x, y, color)));
	}
	
	/**
	 * Get an image from a file name
	 * @param imgFile
	 * @param x
	 * @param y
	 */
	public Image findImage(String imgFile){
		return new ImageIcon(getClass().getResource("/" + imgFile)).getImage();
	}
	
	/**
	 * Get an object from the display
	 * @param nameToCheck
	 * @return
	 */
	public DisplayObject getObject(int index){
		if(index < 0 || index >= display.size()) return null;
		
		return display.get(index);
	}
	
	/**
	 * Get an object from the display
	 * @param nameToCheck
	 * @return
	 */
	public DisplayObject getObject(String nameToCheck){
		for(int i = 0;i < display.size();i++){
			if(display.get(i).getName().equals(nameToCheck)){
				return display.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Move an object to the back of the list
	 * @param index
	 */
	public void moveToTop(int index){
		if(index < 0 || index >= display.size()) return;
		
		DisplayObject temp = display.get(index);
		
		display.remove(index);
		
		display.add(temp);
	}
	
	/**
	 * Move an object to the front of the list
	 * @param index
	 */
	public void moveToBottom(int index){
		if(index < 0 || index >= display.size()) return;
		
		DisplayObject temp = display.get(index);
		
		display.remove(index);
		
		display.add(0, temp);
	}
}
