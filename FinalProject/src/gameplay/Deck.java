package gameplay;

import java.util.ArrayList;

/**
 * An ordered list of cards
 * @author Adam
 *
 */
public class Deck {
	private ArrayList<Card> contents;
	
	public Deck(){
		
	}
	
	public Card topCard(){
		return contents.get(contents.size());
	}
	
	public Card drawCard(){
		return contents.remove(contents.size());
	}
	
	public void shuffle(){
		/**
		 * TODO: Shuffle deck
		 */
	}
	
	public void buildRatCatDeck(){
		/**
		 * TODO: Set contents equal to a standard Rat-A-Tat deck
		 * Note: It just needs 54 cards, four of each 0-8, nine 9, 3 of each 10-12
		 */
		
		shuffle();
	}
	
	/**
	 * TODO: Add any other required methods or variables
	 */
}
