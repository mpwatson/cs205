package states;

import java.awt.Point;

import main.Game;
import main.GameState;
import main.StateManager;

/**
 * This state displays instructions on how to play the game
 * @author Adam
 *
 */
public class InstructionsScreen extends GameState {
	
	private int screen = 1;
	
	private int screenCount = 3;
	
	public float stateID(){
		return StateManager.instructionsID;
	}
	
	public void initialize(){
		addObject("back", "instructions/display.png", 0, 0);
		addObject("main", "instructions/main.png", 25, 525);
		addObject("play","instructions/play.png", 950, 525);
		
		//addObject("left", "instructions/left.png", 0, 0);
		//addObject("right", "instructions/right.png", 0, 0);
		//addObject("main", "instructions/screens/1.png", 0, 0);
	}
	
	public void entered(){
		/**
		 * TODO: Update options to reflect current values
		 */
	}
	
	public void handleFrame(Point mousePos) {
		
	}
	
	public void handleMouseClick(Point mousePos) {
		if(getObject("main").isInRange(mousePos)){
			// main clicked - return to last state
			Game.setState(Game.getLastState());
			return;
		}
		
		if(getObject("play").isInRange(mousePos)){
			// play clicked - begin game
			Game.setState(StateManager.modeMenuID);
			return;
		}
		
		if(getObject("left").isInRange(mousePos)){
			// left click - move back
			screen = Math.max(1, screen--);
			//getObject("main").
			return;
		}
		
		if(getObject("right").isInRange(mousePos)){
			// left click - move forward
			screen = Math.min(screenCount, screen--);
			return;
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
