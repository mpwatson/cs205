package states;

import java.awt.Point;

import main.Switchboard;
import main.GameState;
import main.StateManager;

/**
 * This state displays instructions on how to play the game
 * @author Adam
 *
 */
public class InstructionsScreen extends GameState {
	
	private int screen = 1;
	
	/**
	 * **** CHANGE THIS NUMBER TO REFLECT THE NUMBER OF INSTRUCTION SCREENS *****
	 */
	private int screenCount = 5;
	
	public float stateID(){
		return StateManager.instructionsID;
	}
	
	public void initialize(){
		addObject("back", "instructions/display.png", 0, 0);
		addObject("main", "instructions/main.png", 25, 525);
		addObject("play", "instructions/play.png", 950, 525);
		
		addObject("title", "instructions/title.png", 275, 15);
		
		addObject("left", "instructions/left.png", 50, 240);
		addObject("right", "instructions/right.png", 1025, 240);
		addObject("display", "instructions/screens/1.png", 280, 120);
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
			Switchboard.setState(Switchboard.getLastState());
			return;
		}
		
		if(getObject("play").isInRange(mousePos)){
			// play clicked - begin game
			Switchboard.setState(StateManager.modeMenuID);
			return;
		}
		
		if(getObject("left").isInRange(mousePos)){
			// left click - move back
			screen = Math.max(1, --screen);
			getObject("display").setImage(findImage("instructions/screens/" + screen + ".png"), 0);
			return;
		}
		
		if(getObject("right").isInRange(mousePos)){
			// right click - move forward
			screen = Math.min(screenCount, ++screen);
			getObject("display").setImage(findImage("instructions/screens/" + screen + ".png"), 0);
			
			return;
		}
	}
	
	public void closeState() {};
}
