package states;

import java.awt.Point;
import java.awt.event.KeyEvent;

import main.Game;
import main.GameState;
import main.StateManager;

/**
 * EXAMPLE GAME STATE - Option Menu
 * @author Adam
 *
 */
public class OptionMenu extends GameState {
	
	public float stateID(){
		return StateManager.optionMenuID;
	}
	
	public void initialize(){
		addObject("back", "optionMenu/menuBack.png", 0, 0);
		addObject("title", "optionMenu/menuTitle.png", 50, 50);
		addObject("exit", "optionMenu/exit.png", 50, 650);
		/**
		 * TODO: Add all other objects needed
		 */
	}
	
	public void entered(){
		/**
		 * TODO: Update options to reflect current values
		 */
	}
	
	public void handleFrame(Point mousePos) {
		
	}
	
	public void handleMouseClick(Point mousePos) {
		if(getObject("exit").isInRange(mousePos)){
			// exit clicked - return to main menu
			Game.setState(StateManager.mainMenuID);
			return;
		}
	}
	
	public void handleMousePressed(Point mousePos) {
		
	}
	
	public void handleMouseReleased(Point mousePos) {
		
	}
	
	public void handleKeyPressed(int keyID) {
		if(keyID == KeyEvent.VK_ESCAPE){
			Game.setState(StateManager.mainMenuID);
		}
	}
	
	public void handleKeyReleased(int keyID) {
		
	}
	
	public void closeState() {};
}
