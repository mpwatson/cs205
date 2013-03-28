package states;

import gameplay.GameLogic;

import java.awt.Point;

import main.Game;
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
		// TODO Auto-generated method stub

	}

	public void entered() {
		addObject("backdrop","modeMenu/backdrop.png", 0, 0);
		addObject("title", "modeMenu/menuTitle.png", 225, 20);
		
		addObject("main", "modeMenu/main.png", 25, 525);
		addObject("instruct","modeMenu/instruct.png", 950, 525);
		
		addObject("easy","modeMenu/easy.png", 200, 180);
		addObject("med","modeMenu/med.png", 475, 180);
		addObject("hard","modeMenu/hard.png", 750, 180);
		
	}

	public void handleFrame(Point mousePos) {
		/**
		 * TODO: Update the game
		 */
	}
	
	public void handleMouseClick(Point mousePos) {
		if(getObject("main").isInRange(mousePos)){
			// main clicked - return to main menu
			Game.setState(StateManager.mainMenuID);
			return;
		}
		
		if(getObject("instruct").isInRange(mousePos)){
			// instructions clicked - see instructions
			Game.setState(StateManager.instructionsID);
			return;
		}
		
		if(getObject("easy").isInRange(mousePos)){
			// easy clicked - start game
			GameLogic.difficulty = 0;
			Game.setState(StateManager.gameMainID);
			return;
		}
		
		if(getObject("med").isInRange(mousePos)){
			// med clicked - start game
			GameLogic.difficulty = 1;
			Game.setState(StateManager.gameMainID);
			return;
		}
		
		if(getObject("hard").isInRange(mousePos)){
			// hard clicked - start game
			GameLogic.difficulty = 2;
			Game.setState(StateManager.gameMainID);
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
