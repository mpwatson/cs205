package states;

import java.awt.Point;
import java.awt.event.KeyEvent;

import main.Game;
import main.GameState;
import main.StateManager;

/**
 * EXAMPLE GAME STATE - Main Menu
 * @author Adam
 *
 */
public class MainMenu extends GameState {
	
	public float stateID(){
		return StateManager.mainMenuID;
	}
	
	public void initialize(){
		/**
		 * TODO: Add all objects here - Examples provided on adding objects
		 */
		addObject("back", "mainMenu/menuBack.png", 0, 0);
		addObject("title", "mainMenu/menuTitle.png", 50, 50);
		addObject("newGame", "mainMenu/item1.png", 100, 250);
		addObject("options", "mainMenu/item2.png", 100, 425);
		addObject("exit", "mainMenu/item3.png", 100, 600);
	}
	
	public void entered(){
		
	}
	
	public void handleFrame(Point mousePos) {
		
	}
	
	public void handleMouseClick(Point mousePos) {
		if(getObject("newGame").isInRange(mousePos)){
			// start new game
			Game.setState(StateManager.gameMainID);
		}
		
		if(getObject("options").isInRange(mousePos)){
			// move to options menu
			Game.setState(StateManager.optionMenuID);
		}
		
		if(getObject("exit").isInRange(mousePos)){
			// exit game
			System.exit(0);
		}
	}
	
	public void handleMousePressed(Point mousePos) {
		
	}
	
	public void handleMouseReleased(Point mousePos) {
		
	}
	
	public void handleKeyPressed(int keyID) {
		if(keyID == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
	}
	
	public void handleKeyReleased(int keyID) {
		
	}
	
	public void closeState() {};
}
