package gameplay;

import java.util.*;

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
	 *
	 *	Changed loop to i<4 instead of 3, need 4 cards:0,1,2,3 - S.K.
	 *
	 */
	public void addCard(Card newCard){
		for(int i = 0;i < 4;i++){
			if(contents[i] == null){
				contents[i] = newCard;
				return;
			}//if
		}//for
	}//addCard
	
	/**
	 * return card at supplied index in hand without removal from hand
	 * @param index
	 *
	 * -S.K.
	 **/
	public Card getCard(int index){
		return (index >= 0 && index <= 3) ? contents[index] : null;
	}//getCard
	
	 
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
	 *	 -S.K.
	 **/
	public void removeCard(int index){
		if(contents[index] != null)
			contents[index] = null;
	}//removeCard
	
	/**
	* adds a new card to the hand and returns the card that has been replaced
	* index is the location in the array of the card to remove, 
	* c is the new card to store in the hand
	* @param index, c
	*
	*  -S.K.
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
	 *	 -S.K.
	 **/
	public void printHand(){
		for(int i = 0;i < 4;i++){
			if(contents[i] != null){
				System.out.println(contents[i]);
			}//if
		}//for
	}//printHand
	
