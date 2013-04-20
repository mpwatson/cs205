package main;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import misc.UpdateMode;

import states.InstructionsScreen;
import states.MainMenu;
import states.GameMain;
import states.ModeMenu;
import states.ReportDisplay;


/**
 * Used by the Game class to handle state-specific input and timer events
 * @author Adam
 *
 */
public final class StateManager {
	/**
	 * Main menu
	 */
	public static final float mainMenuID = 1.0f;
	/**
	 * Option menu
	 */
	public static final float instructionsID = 1.1f;
	/**
	 * Report display
	 */
	public static final float reportsID = 1.2f;
	/**
	 * Screen for selecting opponent
	 */
	public static final float modeMenuID = 2.0f;
	/**
	 * Primary game state for gameplay
	 */
	public static final float gameMainID = 3.0f;
	
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
		stateList.add(new InstructionsScreen());
		stateList.add(new ReportDisplay());
		stateList.add(new ModeMenu());
		stateList.add(new GameMain());
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
