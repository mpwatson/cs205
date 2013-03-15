package gameplay;

/**
 * Used for both the human and computer players during gameplay
 * @author Adam
 *
 */
public class Player {
	/**
	 * TODO: Add all variables the player needs here
	 */
	private Hand hand;
	
	public Player(){
		/**
		 * TODO: Initialize the player
		 */
	}
	
	/**
	 * Used for dealing opening hands
	 * @param cardDealt
	 */
	public void addCard(Card cardDealt){
		hand.addCard(cardDealt);
	}
	
	/**
	 * TODO: Create all methods required for this class
	 */
}
