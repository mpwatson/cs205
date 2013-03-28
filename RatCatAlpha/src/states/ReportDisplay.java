package states;

import java.awt.Point;

import main.Game;
import main.GameState;
import main.StateManager;

/**
 * The screen displaying our reports
 * @author Adam
 *
 */
public class ReportDisplay extends GameState {

	public float stateID(){
		return StateManager.reportsID;
	}
	
	public void initialize(){
		addObject("backdrop", "reports/backdrop.png", 0, 0);
		addObject("return", "reports/return.png", 25, 525);
	}
	
	public void entered(){
		/**
		 * TODO: Update options to reflect current values
		 */
	}

	public void handleFrame(Point mousePos) {
		
	}
	
	public void handleMouseClick(Point mousePos) {
		if(getObject("return").isInRange(mousePos)){
			// return clicked - return to last state
			Game.setState(Game.getLastState());
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
	
	public void closeState() {}

}
