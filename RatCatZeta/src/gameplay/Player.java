package gameplay;

/**
 * Used for both the human and computer players during gameplay
 * @author Adam
 *
 */
public class Player {

	private String name;
	protected Hand hand;
	
	public Player(){
		hand = new Hand();
	}
	
	/**
	 * store player name
	 * @param n
	 *
	 * -S.K.
	**/
	public void setName(String n){
		name = n;
	}
	
	/**
	 * return the name of the player
	 *
	 * -S.K.
	**/
	public String getName(){
		return name;
	}
	
	/**
	 * returns direct access to the player's hand
	 **/
	public Hand getHand(){
		return hand;
	}
	
	/**
	 * Used for dealing opening hands
	 * @param cardDealt
	*/
	public void addCard(Card cardDealt){
		hand.addCard(cardDealt);
	}
	
  	/**
  	 * display the players hand
  	 *
  	 * -S.K.
	**/
	public void printHand(){
		hand.printHand();
	}
	
	/**
	 * clear the players hand
	 *
	 * -S.K.
	**/
	public void clearHand(){
		hand.clearHand();
	}
	
	/**
	 * removes card at specified index in hand
	 * @param removeCard
	 *
	 * -S.K.
	**/
	public void removeCard(int removeCard){
		hand.removeCard(removeCard);
	}
	
	
	/**
	 * return card at specified index if found in hand
	 * @param index
	 *
	 * -S.K.
	**/
	public Card getCard(int index){
		Card c = hand.getCard(index);
		return c;
	}
}
