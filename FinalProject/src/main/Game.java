package main;


import gameplay.Deck;
import gameplay.Player;

import java.awt.Graphics;
import java.awt.Point;

import misc.UpdateMode;


/**
 * Provides a handle for game logic and handling game states
 * @author Adam
 *
 */
public final class Game {
	/**
	 * The ID number of the current game state, game logic within a game state can freely change this value
	 * to move the game into a different state
	 */
	private static float gameStateID = StateManager.mainMenuID;
	
	/**
	 * The ID number of the previous game state
	 */
	private static float previousStateID = StateManager.mainMenuID;
	
	/**
	 * This controls the length of a frame, set to 30 miliseconds
	 */
	public static int frameLength = 30;
	
	/**
	 * TODO: Add in all game level variables here
	 */
	
	/**
	 * Store both players
	 */
	public static Player player = new Player();
	
	public static Player opponent = new Player();
	
	/**
	 * Store the deck
	 */
	public static Deck deck = new Deck();
	
	/**
	 * Store the discard pile
	 */
	public static Deck discard = new Deck();
	
	/**
	 * Setup the game
	 */
	public static void initialize(){
		StateManager.initGameStates();
		
		/**
		 * TODO: Initialize the game here, call all methods that need to be called when the game is started
		 */
		deck.buildRatCatDeck();
		
		for(int i = 0;i < 3;i++){
			player.addCard(deck.drawCard());
			opponent.addCard(deck.drawCard());
		}
	}
	
	/**
	 * Set the current game state
	 */
	public static void setState(float stateID){
		StateManager.updateState(gameStateID, null, UpdateMode.EXIT);
		
		previousStateID = gameStateID;
		gameStateID = stateID;
		StateManager.updateState(gameStateID, null, UpdateMode.MOVED_TO);
	}
	
	/**
	 * Update the game
	 * @param mousePos
	 */
	public static void update(Point mousePos){
		StateManager.updateState(gameStateID, mousePos, UpdateMode.NEW_FRAME);
	}
	
	/**
	 * Draw the game
	 * @param g
	 */
	public static void draw(Graphics g){
		StateManager.drawState(gameStateID, g);
	}
	
	public static void handleMouseClick(Point mousePos){
		StateManager.updateState(gameStateID, mousePos, UpdateMode.MOUSE_CLICKED);
	}
	
	public static void handleMousePress(Point mousePos){
		StateManager.updateState(gameStateID, mousePos, UpdateMode.MOUSE_PRESSED);
	}
	
	public static void handleMouseRelease(Point mousePos){
		StateManager.updateState(gameStateID, mousePos, UpdateMode.MOUSE_RELEASED);
	}
	
	public static void handleKeyPress(int keyID){
		StateManager.updateState(gameStateID, new Point(-1, keyID), UpdateMode.KEY_PRESSED);
	}
	
	public static void handleKeyRelease(int keyID){
		StateManager.updateState(gameStateID, new Point(-1, keyID), UpdateMode.KEY_RELEASED);
	}
	
	/**
	 * Get the ID of the previous state
	 * @return
	 */
	public static float getLastState(){
		return previousStateID;
	}
}
