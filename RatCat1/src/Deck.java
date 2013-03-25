import java.util.*;

/**
 * An ordered list of cards
 * @author Adam
 *
 */
public class Deck {
	private Stack<Card> deck;
	private Stack<Card> discard;
	
	public Deck(){
		deck = new Stack<Card>();
		discard = new Stack<Card>();
		buildRatCatDeck();
	}

	//return top card of the deck
	public Card drawCardDeck(){
		return deck.pop();	
	}


	//return top card from discard pile
	public Card drawCardDiscard(){
		return discard.pop();
	}

	//check the top card of the deck
	public Card peekDeck(){
		return deck.peek();
	}

	//check top card of discard
	public Card peekDiscard(){
		return discard.peek();
	}

	//discard a card onto the discard pile
	public void discard(Card card){
		discard.push(card);
	}

	//boolean check to see if more cards are in the deck
	public boolean deckHasCards(){
		return (deck.size() > 0);
	}

	//boolean check to see if more card are in the discard pile
	public boolean discardHasCards(){
		return (discard.size() > 0);
	}


	//build the deck with the right number of cards
	public void buildRatCatDeck(){
		/**
		 * TODO: Set contents equal to a standard Rat-A-Tat deck
		 * Note: It just needs 54 cards, four of each 0-8, nine 9, 3 of each 10-12
		 */
		
		for(int i = 0; i<9; i++){
			if(i<3){
				//At least 3 of every card
				for(int j = 0; j<13; j++){
					Card card = new Card(j);
					deck.push(card);
				}
			}else if(i<4){
				//Now one more each of the numerics
				for(int j = 0; j<10; j++){
					Card card = new Card(j);
					deck.push(card);
				}
			}else{
				//Now some more 9's
				Card card = new Card(9);
				deck.push(card);
			}
		}

		shuffle();
	}
	
	//shuffle the deck
	public void shuffle(){
		Collections.shuffle(deck);
	}
}