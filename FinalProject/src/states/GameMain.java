package states;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;

import main.Game;
import main.GameState;
import main.StateManager;
import misc.FontList;

/**
 * EXAMPLE GAME STATE - Main State
 * @author Adam
 *
 */
public class GameMain extends GameState {
	
	/**
	 * TODO: Add variables global to this game state here
	 */
	
	public float stateID(){
		return StateManager.gameMainID;
	}
	
	public void initialize(){
		/**
		 * TODO: Initialize the game state when the game begins running
		 */
	}
	
	public void entered(){
		/**
		 * TODO: Initialize a new or saved game
		 */
		addObject("text","INSERT GAME HERE!",FontList.exampleFont,100,290,Color.BLACK);
	}
	
	public void handleFrame(Point mousePos) {
		/**
		 * TODO: Update the game
		 */
	}
	
	public void handleMouseClick(Point mousePos) {
		
	}
	
	public void handleMousePressed(Point mousePos) {
		
	}
	
	public void handleMouseReleased(Point mousePos) {
		
	}
	
	public void handleKeyPressed(int keyID) {
		keyboard.keyPressed(keyID);
		
		if(keyID == KeyEvent.VK_ESCAPE){
			Game.setState(StateManager.mainMenuID);
			return;
		}
	}
	
	public void handleKeyReleased(int keyID) {
		keyboard.keyReleased(keyID);
	}
	
	public void closeState() {};
}