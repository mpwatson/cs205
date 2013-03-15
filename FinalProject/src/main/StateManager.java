package main;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import misc.UpdateMode;

import states.MainMenu;
import states.OptionMenu;
import states.GameMain;


/**
 * Used by the Game class to handle state-specific input and timer events
 * @author Adam
 *
 */
public final class StateManager {
	/**
	 * Main menu - Built in example state
	 */
	public static final float mainMenuID = 1.0f;
	/**
	 * Option menu - Built in example state
	 */
	public static final float optionMenuID = 1.1f;
	/**
	 * Primary game state for gameplay - Built in example state
	 */
	public static final float gameMainID = 2.0f;
	/**
	 * TODO: Add in all necessary states to this list, they each require their own class
	 */
	
	/**
	 * List of game states
	 */
	public static ArrayList<GameState> stateList;
	
	/**
	 * Setup each game state
	 */
	public static void initGameStates(){
		stateList = new ArrayList<GameState>();
		stateList.add(new MainMenu());
		stateList.add(new OptionMenu());
		stateList.add(new GameMain());
		/**
		 * TODO: Add all necessary states to the stateList
		 */
	}
	
	/**
	 * Update the current game state
	 * @param stateID
	 * @param mousePos
	 * @param mode
	 */
	public static void updateState(float stateID, Point mousePos, UpdateMode mode){
		for(int i = 0;i < stateList.size();i++){
			if(stateID == stateList.get(i).stateID()){
				stateList.get(i).update(mousePos, mode);
				return;
			}
		}
	}
	
	/**
	 * Draw the current game state
	 * @param stateID
	 * @param g
	 */
	public static void drawState(float stateID, Graphics g){
		for(int i = 0;i < stateList.size();i++){
			if(stateID == stateList.get(i).stateID()){
				stateList.get(i).draw(g);
			}
		}
	}
}
