package states;

import java.awt.Point;

import main.Game;
import main.GameState;
import main.StateManager;

/**
 * The game's main menu, the initial game state
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
		addObject("title", "mainMenu/menuTitle.png", 275, 30);
		addObject("play", "mainMenu/play.png", 375, 200);
		addObject("instruct", "mainMenu/instruct.png", 375, 325);
		addObject("report", "mainMenu/report.png", 375, 450);
	}
	
	public void entered(){
		
	}
	
	public void handleFrame(Point mousePos) {
		
	}
	
	public void handleMouseClick(Point mousePos) {
		if(getObject("play").isInRange(mousePos)){
			// start new game
			Game.setState(StateManager.modeMenuID);
		}
		
		if(getObject("instruct").isInRange(mousePos)){
			// move to instructions
			Game.setState(StateManager.instructionsID);
		}
		
		if(getObject("report").isInRange(mousePos)){
			// exit game
			Game.setState(StateManager.reportsID);
		}
	}
	
	public void handleMousePressed(Point mousePos) {
		
	}
	
	public void handleMouseReleased(Point mousePos) {
		
	}

	public void handleKeyPressed(int keyID) {
		
	}

	public void handleKeyReleased(int keyID) {
		
	};
	
	public void closeState() {};
}
