package gameplay;
/**
*Computer player AI
*@author Scott MacEwan
*/
import java.util.*;

public class ComputerPlayer extends Player{
	//Memory Thresholds
	private final int EASY = 2;
	private final int MEDIUM = 5;
	private final int HARD = 9;

	//Highest rank that will replace a placed power & unknown cards
	private final int POWER_VALUE = 5;
	private final int UNKNOWN_VALUE = 6;

	//Unknown/Known indicators
	private final int UNKNOWN = 0;
	private final int KNOWN = 1;

	//Call rat-a-tat-cat if sum of the hand is less than this value
	private final int CALL_RAT = 13;


	private int[] knownCards;
	private int turnCount;

	private int difficulty;

	public ComputerPlayer(int difficulty){
		knownCards = new int[]{KNOWN, UNKNOWN, UNKNOWN, KNOWN};
		hand = new Hand();	
		turnCount = 0;
		switch(difficulty){
			case 1:
				this.difficulty = EASY;
				break;
			case 2:
				this.difficulty = MEDIUM;
				break;
			case 3:
				this.difficulty = HARD;
				break;
			default:
				this.difficulty = EASY;
				break;
		}
	}

	/**
	* Function that will randomly forget cards based on the difficulty
	*/
	public void beginTurn(){
		//random chance for forgetting
		for(int i=0; i<4;i++){
			if(knownCards[i] == KNOWN){
				int forget = (int)(Math.random()*10);
				if(forget > difficulty){
					knownCards[i] = UNKNOWN;
				}
			}
		}

	}

	/**
	*Function that will decide whether to draw from the deck or from the discard pile
	*/
	public boolean decideDrawCard(Card discard){
		//return false for draw from deck or true for draw discard
		boolean take = false;
		//if card is less than a known card, or the rank is low enough and we have unknowns than take it
		if(!discard.isPowerCard()){
			for(int i=0;i<4;i++){
				if(knownCards[i] == KNOWN){
					if(hand.getCard(i).isPowerCard()){
						if(discard.getRank() < POWER_VALUE){
							take = true;
						}
					}else if(discard.getRank() < hand.getCard(i).getRank()){
						take = true;
					}
				}else if(discard.getRank() < UNKNOWN_VALUE){
					take = true;
				}
			}
		}
		return take;
	}

	/**
	*Function that play a card, swaping or discarding it. Also performs peek.
	*Use only this if you do not wish to handle swap and draw 2
	*/
	public Card playCard(Card drawnCard){
		
		//Assume we are not going swap it, update the discard if we do swap it
		Card discardCard = drawnCard;

		//if it is a numeric card
		if(!drawnCard.isPowerCard()){
			int index = chooseBestSwap(drawnCard.getRank());
			if(index != 5){
				discardCard = hand.swapCard(index, drawnCard);
				knownCards[index] = KNOWN;
			}
		}else if(drawnCard.getRank() == 11){
			peek();
		}
		return discardCard;

	}


	/**
	*Play Draw 2, will choose wether or not it wishes to use the first.
	*If the option of drawing a second card is available first=true
	*/
	public Card playDrawTwo(Card drawnCard, boolean first){
		//Return a null if the card is not used the first time
		Card discardCard = null;
		if(first){
			int index = chooseBestSwap(drawnCard.getRank());
			if(index != 5){
				discardCard = hand.swapCard(index, drawnCard);
				knownCards[index] = KNOWN;
			}
		}else{
			discardCard = playCard(drawnCard);
		}
		return discardCard;
	}


	/**
	*Chooses the two indicies of cards to swap. Will indicate a 5 for its position if it does not wish to swap
	*@return an array of two integer indicies. The first corresponds to the computers hand, the second to the opponents
	*/
	public int[] playSwap(){
		int highestValue = 0;
		int computerIndex = 5;
		//Choose the worst card to get rid of
		for (int i =0; i<4 ;i++) {
			if(knownCards[i]==KNOWN && !hand.getCard(i).isPowerCard()){
				if(hand.getCard(i).getRank() > highestValue){
					highestValue = hand.getCard(i).getRank();
					computerIndex = i;
				}
			}	
		}
		if(highestValue <= UNKNOWN_VALUE){
			//If it is not worth it to swap then don't do it
			computerIndex = 5;
		}else{
			//if we are swapping a card then we won't know what we get
			knownCards[computerIndex] = UNKNOWN;
		}
		int playerIndex = (int)(Math.random()*4);
		return new int[]{computerIndex, playerIndex};

	}

	/**
	*Choose a random Unknown card to peek at
	*/
	private void peek(){
		//Create an arraylist of the indexes of the unkowncards
		ArrayList<Integer> unknownIndexes = new ArrayList<Integer>();
		for(int i = 0; i<4; i++){
			if(knownCards[i] == UNKNOWN){
				unknownIndexes.add(i);
			}
		}
		//If there are unknown cards randomly choose one to peek at
		if(unknownIndexes.size() > 0){
			int learn = (int)(Math.random()*unknownIndexes.size());
			knownCards[unknownIndexes.get(learn)] = KNOWN;
		}
	}


	/**
	*choose the best card in the hand to swap with the drawn card
	*/
	private int chooseBestSwap(int rank){
		int index = 5;
		int swapValue = 0;
		for(int i = 0 ; i<4; i++){
			//Make sure the card is known before we look at it's rank
			if(knownCards[i] == KNOWN){
				if(hand.getCard(i).isPowerCard()){
					int tempSwapValue = POWER_VALUE - rank;
					if(tempSwapValue > swapValue){
						swapValue = tempSwapValue;
						index = i;
					}
				}else{
					//only switch the index if the swap value of swapping this card is higher
					int tempSwapValue = hand.getCard(i).getRank() - rank;
					if(tempSwapValue > swapValue){
						swapValue = tempSwapValue;
						index = i;
					}
				}
			}
		}
		//if we have not decided on a card to swap, swap with unknown if the rank is low enough
		if(index == 5 && rank < UNKNOWN_VALUE){
			//check to see if there is any unknowns we can swap out
			for(int j = 0; j<4; j++){
				if(knownCards[j] == UNKNOWN){
					index = j;
				}
			}
		}
		return index;
	}


	//for testing purposes
	public void printKnownCards(){
		for (int i =0;i<4 ;i++ ) {
			if(knownCards[i] == KNOWN){
				System.out.println(hand.getCard(i).toString());		
			}else
			{
				System.out.println("UNKNOWN");
			}
			
		}

	}

	public void incrementTurnCounter(){
		turnCount++;
	}

	public boolean callRatATat(){
		boolean response = false;
		int sum = 0;
		for(int i = 0;i<4;i++){
			if(knownCards[i] == KNOWN){
				if(hand.getCard(i).isPowerCard()){
					sum += POWER_VALUE;
				}else{
					sum += hand.getCard(i).getRank();
				}
			}else{
				sum += UNKNOWN_VALUE;
			}
		}
		//EVENTUALLY WORK TURN COUNTER INTO THIS
		if(sum < CALL_RAT){
			response = true;
		}
		return response;
	}

	public static void main(String[] args) {
		Deck deck = new Deck();

		ComputerPlayer computer1 = new ComputerPlayer(1);
		ComputerPlayer computer2 = new ComputerPlayer(2);
		ComputerPlayer computer3 = new ComputerPlayer(3);
		for(int i = 0; i < 4; i++){
			computer1.addCard(deck.drawCardDeck());
			computer2.addCard(deck.drawCardDeck());
			computer3.addCard(deck.drawCardDeck());
		}


/*
		//Test memory and peeking
		System.out.println("%%%%%%%%%%%%%%TURN 1%%%%%%%%%%%%%");
		System.out.println("COMPUTER 1");
		computer1.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 2");
		computer2.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 3");
		computer3.printKnownCards();
		System.out.println("\n");

		computer1.beginTurn();
		computer2.beginTurn();
		computer3.beginTurn();

		System.out.println("%%%%%%%%%%%%%%TURN 2%%%%%%%%%%%%%");

		System.out.println("COMPUTER 1");
		computer1.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 2");
		computer2.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 3");
		computer3.printKnownCards();
		System.out.println("\n");

		computer1.beginTurn();
		computer2.beginTurn();
		computer3.beginTurn();

		System.out.println("%%%%%%%%%%%%%%TURN 3%%%%%%%%%%%%%");

		System.out.println("COMPUTER 1");
		computer1.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 2");
		computer2.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 3");
		computer3.printKnownCards();
		System.out.println("\n");

		Card card = new Card(11);
		Card tempCard = new Card(0);

		tempCard = computer1.playCard(card);
		tempCard = computer2.playCard(card);
		tempCard = computer3.playCard(card);

		System.out.println("%%%%%%%%%%%%%%AFTER PEEK%%%%%%%%%%%%%");

		System.out.println("COMPUTER 1");
		computer1.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 2");
		computer2.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 3");
		computer3.printKnownCards();
		System.out.println("\n");

		tempCard = computer1.playCard(card);
		tempCard = computer2.playCard(card);
		tempCard = computer3.playCard(card);

		System.out.println("%%%%%%%%%%%%%%ANOTHER PEEK%%%%%%%%%%%%%");

		System.out.println("COMPUTER 1");
		computer1.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 2");
		computer2.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 3");
		computer3.printKnownCards();
		System.out.println("\n");

		tempCard = computer1.playCard(card);
		tempCard = computer2.playCard(card);
		tempCard = computer3.playCard(card);

		System.out.println("%%%%%%%%%%%%%%ANOTHER PEEK%%%%%%%%%%%%%");

		System.out.println("COMPUTER 1");
		computer1.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 2");
		computer2.printKnownCards();
		System.out.println("\n");
		System.out.println("COMPUTER 3");
		computer3.printKnownCards();
		System.out.println("\n");
*/

/*
		//Check to see the playing of cards and swapping from hand
		Card card0 = new Card(0);
		Card card1 = new Card(1);
		Card card2 = new Card(2);
		Card card3 = new Card(3);
		Card card4 = new Card(4);
		Card card5 = new Card(5);
		Card card6 = new Card(6);
		Card card7 = new Card(7);
		Card card8 = new Card(8);
		Card card9 = new Card(9);
		Card tempCard = new Card(9);

		System.out.println("Initial Hand:");
		computer1.printKnownCards();
		System.out.println("\n");
		System.out.println("Input: " + card0.toString());
		tempCard = computer1.playCard(card0);
		computer1.printKnownCards();
		System.out.println("Discard: " + tempCard.toString());
		System.out.println("\n");
		System.out.println("Input: " + card8.toString());
		tempCard = computer1.playCard(card8);
		computer1.printKnownCards();
		System.out.println("Discard: " + tempCard.toString());
		System.out.println("\n");
		System.out.println("Input: " + card2.toString());
		tempCard = computer1.playCard(card2);
		computer1.printKnownCards();
		System.out.println("Discard: " + tempCard.toString());
		System.out.println("\n");
		System.out.println("Input: " + card5.toString());
		tempCard = computer1.playCard(card5);
		computer1.printKnownCards();
		System.out.println("Discard: " + tempCard.toString());
		System.out.println("\n");
		System.out.println("Input: " + card3.toString());
		tempCard = computer1.playCard(card3);
		computer1.printKnownCards();
		System.out.println("Discard: " + tempCard.toString());
		System.out.println("\n");
*/
/*
		//Test auto power card swap choices
		int[] swap1 =computer1.playSwap();
		computer1.printKnownCards();
		System.out.print("\nComputer: "+ Integer.toString(swap1[0]) + " Player: " + Integer.toString(swap1[1])+ "\n");
		int[] swap2 =computer2.playSwap();
		computer2.printKnownCards();
		System.out.print("\nComputer: "+ Integer.toString(swap2[0]) + " Player: " + Integer.toString(swap2[1])+ "\n");
		int[] swap3 =computer3.playSwap();
		computer3.printKnownCards();
		System.out.print("\nComputer: "+ Integer.toString(swap3[0]) + " Player: " + Integer.toString(swap3[1])+ "\n");
*/		

		//Test play 2 cards
		Card card1 = new Card(6);
		Card card2 = new Card(3);


		computer1.printKnownCards();
		System.out.println("\nFirst card");
		Card tempCard = computer1.playDrawTwo(card1,true);
		if(tempCard == null){
			System.out.println("Second card");
			tempCard = computer1.playDrawTwo(card2,false);
		}
		System.out.println("Discard: "+ tempCard.getRank());

		computer1.printKnownCards();

	}
	
	public int getTurnCount(){
		return turnCount;
	}
}