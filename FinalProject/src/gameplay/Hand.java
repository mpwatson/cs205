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
	
	public Card getCard(int index){
		return (index >= 0 && index <= 3) ? contents[index] : null;
	}
	
	/**
	 * TODO: Finish this class
	 * Note: Need method that replaces a card and returns previous card
	 */
}
