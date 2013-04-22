package states;

import java.awt.Point;

import main.Switchboard;
import main.GameState;
import main.StateManager;

/**
 * The menu where users decide which opponent to play against
 * @author Adam
 *
 */
public class ModeMenu extends GameState {

	public float stateID(){
		return StateManager.modeMenuID;
	}
	
	public void initialize() {
		addObject("backdrop","modeMenu/backdrop.png", 0, 0);
		addObject("title", "modeMenu/menuTitle.png", 225, 20);
		
		addObject("main", "modeMenu/main.png", 25, 525);
		addObject("instruct","modeMenu/instruct.png", 950, 525);
		
		addObject("easy","modeMenu/easy.png", 200, 180);
		addObject("med","modeMenu/medium.png", 475, 180);
		addObject("hard","modeMenu/hard.png", 750, 180);
	}

	public void entered() {
		
	}

	public void handleFrame(Point mousePos) {
		/**
		 * TODO: Update the game
		 */
	}
	
	public void handleMouseClick(Point mousePos) {
		if(getObject("main").isInRange(mousePos)){
			// main clicked - return to main menu
			Switchboard.setState(StateManager.mainMenuID);
			return;
		}
		
		if(getObject("instruct").isInRange(mousePos)){
			// instructions clicked - see instructions
			Switchboard.setState(StateManager.instructionsID);
			return;
		}
		
		if(getObject("easy").isInRange(mousePos)){
			// easy clicked - start game
			GameMain.difficulty = 1;
			Switchboard.setState(StateManager.gameMainID);
			return;
		}
		
		if(getObject("med").isInRange(mousePos)){
			// med clicked - start game
			GameMain.difficulty = 2;
			Switchboard.setState(StateManager.gameMainID);
			return;
		}
		
		if(getObject("hard").isInRange(mousePos)){
			// hard clicked - start game
			GameMain.difficulty = 3;
			Switchboard.setState(StateManager.gameMainID);
			return;
		}
	}
	
	public void closeState() {}

}
