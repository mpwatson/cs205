package main;

import java.awt.Graphics;
import java.awt.Point;
//import netscape.javascript.JSObject;
import misc.UpdateMode;


/**
 * Provides a handle for drawing and updating the game and acts as a switchboard to
 * direct input to the current game state.
 * @author Adam
 *
 */
public final class Switchboard {
	/**
	 * The ID number of the current game state, game logic within a game state can freely change this value
	 * to move the game into a different state
	 */
	private static float gameStateID = StateManager.mainMenuID;
	
	/**
	 * The ID number of the previous game state
	 */
	private static float previousStateID = StateManager.mainMenuID;
	
	/**
	 * This controls the length of a frame, set to 30 miliseconds
	 */
	public static int frameLength = 30;
	
	/**
	 * Setup the game
	 */
	public static void initialize(){
		StateManager.initGameStates();
	}
	
	/**
	 * Set the current game state
	 */
	public static void setState(float stateID){
		StateManager.updateState(gameStateID, null, UpdateMode.EXIT);
		
		previousStateID = gameStateID;
		gameStateID = stateID;
		StateManager.updateState(gameStateID, null, UpdateMode.MOVED_TO);
	}
	
	/**
	 * Update the game
	 * @param mousePos
	 */
	public static void update(Point mousePos){
		StateManager.updateState(gameStateID, mousePos, UpdateMode.NEW_FRAME);
	}
	
	/**
	 * Draw the game
	 * @param g
	 */
	public static void draw(Graphics g){
		StateManager.drawState(gameStateID, g);
	}
	
	/**
	 * Handle the mouse being clicked, updating the current game state
	 * @param mousePos
	 */
	public static void handleMouseClick(Point mousePos){
		StateManager.updateState(gameStateID, mousePos, UpdateMode.MOUSE_CLICKED);
	}
	
	/**
	 * Get the ID of the previous state
	 * @return
	 */
	public static float getLastState(){
		return previousStateID;
	}
}
