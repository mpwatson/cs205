/**
 * Used for both the human and computer players during gameplay
 * @author Adam
 *
 */
public class Player {
	
	private String name;
	private Hand playerHand;
	
	public Player(){
		 playerHand = new Hand();	
	}
	
	/**
	 * store player name
	 * @param n
	 *
	 *	 -S.K.
	 **/
	public void setName(String n){
		 name = n;
	}
	
	/**
	 * return the name of the player
	 *
	 *	 -S.K.
	 **/
	public String getName(){
		return name;
	}
	
	/**
	 * returns direct access to the player's hand
	 **/
	public Hand getHand(){
		return playerHand;
	}
	
	/**
	 * Used for dealing opening hands
	 * @param cardDealt
	 */
	public void addCard(Card cardDealt){
		playerHand.addCard(cardDealt);
	}
	
 	 /**
	 * display the players hand
	 *
	 *	 -S.K.
	 **/
	 public void printHand(){
	 	playerHand.printHand();
	 }
	 
	 /**
	 * clear the players hand
	 *
	 *	 -S.K.
	 **/
	 public void clearHand(){
	 	playerHand.clearHand();
	 }
	 
	 /**
	 * removes card at specified index in hand
	 * @param removeCard
	 *
	 *	 -S.K.
	 **/
	 public void removeCard(int removeCard){
	 	playerHand.removeCard(removeCard);
	 }
	 
	 
	 /**
	 * return card at specified index if found in hand
	 * @param index
	 *
	 *	 -S.K.
	 **/
	 public Card getCard(int index){
		Card c = playerHand.getCard(index);
		return c;
	}
}
