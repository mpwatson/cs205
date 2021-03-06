package gameplay;

/**
 * The four cards in a player's hand
 * @author Adam
 *
 */
public class Hand {
	private Card[] contents;
	
	Hand(){
		contents = new Card[]{null, null, null, null};
	}
	
	/**
	 * Add a new card to hand, Used for dealing opening hands
	 * @param newCard
	 */
	public void addCard(Card newCard){
		for(int i = 0;i < 3;i++){
			if(contents[i] == null){
				contents[i] = newCard;
				return;
			}
		}
	}
	
	/**
	 * put supplied card into supplied index
	 * @param newCard
	 * @param index
	 **/
	public void addCardByIndex(Card newCard, int index) {
		contents[index] = newCard;
	}
	
	/**
	 * Return card with supplied index from hand without removal from hand
	 * @param index
	 * @return
	 */
	public Card getCard(int index){
		return (index >= 0 && index <= 3) ? contents[index] : null;
	}
	
	/**
	 * remove all cards from hand setting each to null
	 *
	 * -S.K.
	**/
	public void clearHand(){
		for(int i = 0;i < 4;i++){
			if(contents[i] != null){
				contents[i] = null;
				//return;
			}//if
		}//for
	}//clearHand
	
	/**
	* remove card @ index i in hand if !null
	* @param index
	*
	* -S.K.
	**/
	public Card removeCard(int index){
		if(contents[index] != null) {
			Card temp = contents[index];
			contents[index] = null;
			return temp;
		}
		return null; 
	}//removeCard
	
	/**
	* adds a new card to the hand and returns the card that has been replaced
	* index is the location in the array of the card to remove,
	* c is the new card to store in the hand
	* @param index, c
	*
	* -S.K.
	**/
	public Card swapCard(int index, Card c){
		Card temp = null;
		if(contents[index] != null){
			temp = contents[index];
			contents[index] = c;
		}//if
	
		return temp;
	
	}//swapCard
	
	/**
	* display hand contents for testing
	*
	* -S.K.
	**/
	public void printHand(){
		for(int i = 0;i < 4;i++){
			if(contents[i] != null){
				System.out.println(contents[i]);
			}//if
		}//for
	}//printHand
}
