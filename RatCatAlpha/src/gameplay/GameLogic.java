package gameplay;

/**
 * Handle playing out the game
 * @author Adam
 *
 */
public final class GameLogic {
	
	/**
	 * Difficulty setting
	 */
	public static int difficulty = 0;
	
	/**
	 * Store both players
	 */
	public static Player player = new Player();
	
	public static Player opponent = new Player();
	
	/**
	 * Store the deck
	 */
	public static Deck deck = new Deck();
	
	public static void startGame(){
		// draw hands for both players
		for(int i = 0;i < 3;i++){
			player.addCard(deck.drawCardDeck());
			opponent.addCard(deck.drawCardDeck());
		}
	}
	
}
