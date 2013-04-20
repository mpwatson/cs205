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
import objects.MusicBox;
import objects.TextBox;


/**
 * Generic game state structure
 */
public abstract class GameState {
	protected ArrayList<DisplayObject> display;
	
	protected MusicBox audio = new MusicBox(true);
	
	public Vector2D camera = new Vector2D(0, 0);
	
	public abstract float stateID();
	
	public GameState(){
		display = new ArrayList<DisplayObject>();
		initialize();
	}
	
	public abstract void initialize();
	
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
		if(mode.equals(UpdateMode.MOUSE_PRESSED)){
			handleMousePressed(mousePos);
			return;
		}
		if(mode.equals(UpdateMode.MOUSE_RELEASED)){
			handleMouseReleased(mousePos);
			return;
		}
		if(mode.equals(UpdateMode.KEY_PRESSED)){
			handleKeyPressed(mousePos.y);
			return;
		}
		if(mode.equals(UpdateMode.KEY_RELEASED)){
			handleKeyReleased(mousePos.y);
			return;
		}
		if(mode.equals(UpdateMode.EXIT)){
			handleExit();
			return;
		}
	}
	
	public abstract void handleFrame(Point mousePos);
	public abstract void handleMouseClick(Point mousePos);
	public abstract void handleMousePressed(Point mousePos);
	public abstract void handleMouseReleased(Point mousePos);
	public abstract void handleKeyPressed(int keyID);
	public abstract void handleKeyReleased(int keyID);
	
	/**
	 * Handle leaving the current game state
	 */
	public void handleExit(){
		audio.turnOff();
		
		closeState();
	}
	
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
		display.add(new DisplayObject(iName, new ImageEx(new ImageIcon(getClass().getResource("/" + imgFile)).getImage(), x, y)));
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
