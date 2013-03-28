package states;

import gameplay.Card;
import gameplay.GameLogic;

import java.awt.Point;

import main.Game;
import main.GameState;
import main.StateManager;

/**
 * The game state in which the game is played
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
		addObject("backdrop","gameMain/backdrop.png", 0, 0);
		addObject("exit","gameMain/exit.png", 25, 525);
		addObject("instruct", "gameMain/instruct.png", 488, 525);
		addObject("reports", "gameMain/reports.png", 950, 525);
		
		//addObject("deck","gameMain/deck.png", 0 ,0);
		
		addObject("cCard1", Card.backImage, 300 , 25);
		addObject("cCard2", Card.backImage, 500 , 25);
		addObject("cCard3", Card.backImage, 700 , 25);
		addObject("cCard4", Card.backImage, 900 , 25);
		
		addObject("hCard1", Card.backImage, 300 , 325);
		addObject("hCard2", Card.backImage, 500 , 325);
		addObject("hCard3", Card.backImage, 700 , 325);
		addObject("hCard4", Card.backImage, 900 , 325);
		
		// initialize the game
		GameLogic.startGame();
	}
	
	public void handleFrame(Point mousePos) {
		/**
		 * TODO: Update the game
		 */
	}
	
	public void handleMouseClick(Point mousePos) {
		if(getObject("exit").isInRange(mousePos)){
			// exit clicked - return to main menu
			Game.setState(StateManager.mainMenuID);
			return;
		}
		
		if(getObject("instruct").isInRange(mousePos)){
			// instructions clicked - display instructions
			Game.setState(StateManager.instructionsID);
			return;
		}
		
		if(getObject("reports").isInRange(mousePos)){
			// reports clicked - display reports
			Game.setState(StateManager.reportsID);
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