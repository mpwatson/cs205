package states;

import gui.GameApplet;

import java.awt.Point;
import main.Switchboard;
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
		addObject("play", "mainMenu/play.png", 100, 200);
		addObject("instruct", "mainMenu/instruct.png", 100, 325);
		addObject("report", "mainMenu/report.png", 100, 450);
		
		addObject("stuff","mainmenu/stuff.png", 400, 180);
	}
	
	public void entered(){
		
	}
	
	public void handleFrame(Point mousePos) {
		
	}
	
	public void handleMouseClick(Point mousePos) {
		if(getObject("play").isInRange(mousePos)){
			// start new game
			Switchboard.setState(StateManager.modeMenuID);
		}
		
		if(getObject("instruct").isInRange(mousePos)){
			// move to instructions
			Switchboard.setState(StateManager.instructionsID);
		}
		
		if(getObject("report").isInRange(mousePos)){
			GameApplet.window.call("loadReports", null);
			return;
		}
	}
	
	public void closeState() {};
}
