import java.util.Scanner;


public class Main {

	/**
	 * @param args
	 */
    private static Scanner scanner;
    private static Player player;
    private static ComputerPlayer opponent;
    private static Deck deck;
    private static boolean ratCalled;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    player = new Player();
	    opponent = new ComputerPlayer(1);
	    deck = new Deck();
	    scanner = new Scanner(System.in);
	    ratCalled = false;
		
	    initialize();
		
	    
	    System.out.println("Here are the cards in the First and Last position of your hand");
	    System.out.println("FIRST: " + player.getHand().getCard(0).getRank());
	    System.out.println("LAST: " + player.getHand().getCard(3).getRank());
	    
	    while (!(isGameOver())) {
	    	System.out.println("%%%%%%%%%%%%%%%%%\n");
	    	System.out.println("Player's Hand:\n");
	    	player.printHand();
	    	System.out.println("%%%%%%%%%%%%%%%%%\n");
	        humanTurn();
	        System.out.println("\n\n\n%%%%%%%%%%%%%%%%%\n");
	        System.out.println("Computer's Hand:\n");
	        opponent.printHand();
	        System.out.println("%%%%%%%%%%%%%%%%%\n");
	        computerTurn();
	    }
	    
	    handleGameOver();
	    // Then run a CPU TURN
	    
	    // Check size of Deck, when empty, game over
	    // Give option to yell ratatatcat during player or computer turn (based on AI)
	    // Finalize method could count the hand, any power cards can be thrown away and replaced my discard pile.  Check each hand one at a time for power cards, 
	    // Then add up a final score
	    // Computer turn and Human turn both need option if DECK IS EMPTY
	    
	    

	}

	public static void initialize(){
		
		/**
		 * TODO: Initialize the game here, call all methods that need to be called when the game is started
		 */
		deck.buildRatCatDeck();
		deck.shuffle();
		
		for(int i = 0;i < 4;i++){
			player.getHand().addCardByIndex(deck.drawCardDeck(), i);
			opponent.getHand().addCardByIndex(deck.drawCardDeck(), i);
		}
		
		Card topCard = deck.drawCardDeck();
		while (topCard.isPowerCard()) {
		    // deck.putCardInMiddle(topCard)
			topCard = deck.drawCardDeck();
		}
		deck.discard(topCard);
	}
	
	// Option to discard if don't like what you drew
	public static void humanTurn() {
		Card picked;
		Card left;
		System.out.println("Top of Discard Pile: " + deck.peekDiscard());
		System.out.println("Take from Discard Pile (1) or Draw (2) or Yell Rat-A-Tat-Cat (3)");
		String input = scanner.nextLine();
		int inputInt = Integer.parseInt(input);
		if ((inputInt == 1) || (inputInt == 2)) {
			
			if (inputInt == 1) 
				picked = deck.drawCardDiscard();
			else 
				picked = deck.drawCardDeck();
			
			if (picked.isPowerCard()) {
				System.out.println("YOU PICKED POWER CARD!");
				if (picked.getRank() == 10) {
					drawTwo();
				}
				else if (picked.getRank() == 11) {
					peek();
				}
				else {
					swap();
				}
			} else {
			    System.out.println("PICK WHERE TO PUT " + picked.getRank() + ".  (Or -1 to discard)");
			    String inputTwo = scanner.nextLine();
			    int inputTwoInt = Integer.parseInt(inputTwo);
			    if (inputTwoInt != -1) {
			        left = player.getHand().swapCard(inputTwoInt, picked);
			        if (!(left.isPowerCard()))
			          deck.discard(left);
			    }
			    else
				    deck.discard(picked);
			}
		}
		else if (inputInt == 3)
			callRatCat();
	}
	
	//Some AI is now working
	public static void computerTurn() {
		opponent.beginTurn();
		Card picked;
		if (!deck.discardHasCards()) {
			picked = deck.drawCardDeck();
		}else{
			if(opponent.decideDrawCard(deck.peekDiscard())){
				picked = deck.drawCardDiscard();
			}else{
				picked = deck.drawCardDeck();
			}
		}
		if(!picked.isPowerCard()){
			//if it is numeric play as usual
			deck.discard(opponent.playCard(picked));
		}else{
			//handle the playing of a power card
			computerPowerCard(picked);	
		}

		opponent.incrementTurnCounter();
		ratCalled = opponent.callRatATat();
	} // Needs some form of AI, even basic for testing purposes
	
	// Make separate power card methods for AI
	
	public static void computerPowerCard(Card card){
		switch(card.getRank()){
			case 10:
				//discard the draw 2
				deck.discard(card);
				//pick another card
				Card newPick = deck.drawCardDeck();
				Card discard;

				//I know this next section looks ewwww, but so is trying to account for all possible draw 2 situtaions
				if(newPick.isPowerCard()){
					//if it is a power card just discard it and pick another unless it is a draw 2 again
					if(newPick.getRank() == 10){
						computerPowerCard(newPick);
						return;
					}
					deck.discard(newPick);
					//draw another
					Card newerPick = deck.drawCardDeck();
					//could be another power card
					if(newerPick.isPowerCard()){
						//call the function that handles power cards again with it to handle it then break out
						computerPowerCard(newerPick);
						return;
					}else{
						//its the last card we could choose so have to try and play it
						discard = opponent.playDrawTwo(newerPick,false);
					}
				}else{
					//call the function that will see if it is worthwhile to play
					discard = opponent.playDrawTwo(newPick,true);
					//if discard is null then the AI did not want it
					if(discard == null){
						//discard the new pick to keep the discard pile in order
						deck.discard(newPick);
						//draw another
						Card newerPick = deck.drawCardDeck();
						if(!newerPick.isPowerCard()){
							//if it is numeric make a decision get the discard after the AI decides what to do with it
							discard = opponent.playDrawTwo(newerPick,false);
						}else{
							computerPowerCard(newPick);
							return;
						}
					}	
				}
				//discard the last card
				deck.discard(discard);
				break;
			case 11:
				deck.discard(opponent.playCard(card));
				break;
			case 12:
				//swap[0] = 5 indicates not to use the swap
				int[] swap = opponent.playSwap();
				if(swap[0]!=5){
					Card playerCard = player.getHand().removeCard(swap[1]);
					Card computerCard = opponent.getHand().removeCard(swap[0]);
					player.getHand().addCardByIndex(computerCard, swap[1]);
					opponent.getHand().addCardByIndex(playerCard, swap[0]);  
				}
				deck.discard(card);
				break;
		}
	}
	public static void peek() {
	    System.out.println("YOU DREW PEEK. PICK WHICH CARD TO PEEK AT: ");
	    String peekInput = scanner.nextLine();
	    int intPeekInput = Integer.parseInt(peekInput);
	    System.out.println("PEEKING AT CARD: " + intPeekInput);
	    System.out.println("RANK: " + player.getHand().getCard(intPeekInput).getRank());
	}
	
	public static void swap() {
	    System.out.println("YOU DREW SWAP. DO YOU WANT TO SWAP? (0 - No, 1 - Yes");
	    String swapDecision = scanner.nextLine();
	    int swapDecisionInt = Integer.parseInt(swapDecision);
	    if (swapDecisionInt == 1) {
	    	Card mySwap;
	    	Card yourSwap;
	        System.out.println("CHOOSE WHICH OF YOUR CARDS TO SWAP: ");	
	        String mySwapInput = scanner.nextLine();
	        int mySwapInt = Integer.parseInt(mySwapInput);
	        System.out.println("CHOOSE WHICH OPPONENT CARD TO SWAP: ");
	        String yourSwapInput = scanner.nextLine();
	        int yourSwapInt = Integer.parseInt(yourSwapInput);
	        mySwap = player.getHand().removeCard(mySwapInt);
	        yourSwap = opponent.getHand().removeCard(yourSwapInt);
	        player.getHand().addCard(yourSwap);
	        opponent.getHand().addCard(mySwap);
	    }	    
	}
	
	// Need option for power card
	public static void drawTwo() {
		boolean used = false;
		System.out.println("YOU DREW DRAW TWO. DRAW YOUR FIRST CARD.");
		Card picked = deck.drawCardDeck();
		if (picked.isPowerCard()) {
			System.out.println("YOU DREW A POWER CARD");
			if (picked.getRank() == 10) {
				used = true;
				drawTwo();
			}
			else if (picked.getRank() == 11) {
				System.out.println("YOU DREW A PEEK (0 TO USE OR 1 TO USE YOUR SECOND DRAW)");
				String drawTwoPeekDec = scanner.nextLine();
				int drawTwoPeekDecInt = Integer.parseInt(drawTwoPeekDec);
				if (drawTwoPeekDecInt == 0) {
				  used = true;
				  peek();
				}
			}
			else {
				System.out.println("YOU DREW A SWAP (0 TO USE OR 1 TO USE YOUR SECOND DRAW)");
				String drawTwoSwapDec = scanner.nextLine();
				int drawTwoSwapDecInt = Integer.parseInt(drawTwoSwapDec);
				if (drawTwoSwapDecInt == 0) {
					used = true;
				  swap();
				}
			}
		} else {
			System.out.println("YOU DREW: " + picked.getRank());
			System.out.println("SELECT WHICH CARD TO REPLACE, OR -1 TO USE YOUR SECOND DRAW");
			String twoDecision = scanner.nextLine();
			int twoDecInt = Integer.parseInt(twoDecision);
			if (twoDecInt != -1) {
				used = true;
				Card left = player.getHand().swapCard(twoDecInt, picked);
				deck.discard(left);
			}
		}
		// Card Two
		if (used != true) {
			deck.discard(picked);
		    picked = deck.drawCardDeck();
		    if (picked.isPowerCard()) {
				System.out.println("YOU DREW A POWER CARD");
				if (picked.getRank() == 10) {
					drawTwo();
				}
				else if (picked.getRank() == 11) {
					System.out.println("YOU DREW A PEEK");
					peek();
				}
				else {
					System.out.println("YOU DREW A SWAP (0 TO USE OR 1 TO IGNORE)");
					String drawTwoSwapDec = scanner.nextLine();
					int drawTwoSwapDecInt = Integer.parseInt(drawTwoSwapDec);
					if (drawTwoSwapDecInt == 0) {
					  swap();
					}
				}
			} else { 
		    	System.out.println("YOU DREW: " + picked.getRank());
			    System.out.println("SELECT WHICH CARD TO REPLACE, OR -1 TO DISCARD");
			    String twoDecision2 = scanner.nextLine();
			    int twoDecInt2 = Integer.parseInt(twoDecision2);
			    if (twoDecInt2 != -1) {
			    	Card left2 = player.getHand().swapCard(twoDecInt2, picked);
					deck.discard(left2);
			    }
			    else 
			    	deck.discard(picked);
		    }
		}
		// Draw a card, USE IT AND END TURN 
		// or, DRAW ANOTHER, USE OR DISCARD
	}
	
	public static boolean isGameOver() {
		if (deck.deckHasCards())
		  return (ratCalled);
		else
			return true;
	}
	
	public static void handleGameOver() {
		Card playerCardOne = resolvePowerCard(player.getHand().getCard(0));
		Card playerCardTwo = resolvePowerCard(player.getHand().getCard(1));
		Card playerCardThree = resolvePowerCard(player.getHand().getCard(2));
		Card playerCardFour = resolvePowerCard(player.getHand().getCard(3));
		Card opponentCardOne = resolvePowerCard(opponent.getHand().getCard(0));
		Card opponentCardTwo = resolvePowerCard(opponent.getHand().getCard(1));
		Card opponentCardThree = resolvePowerCard(opponent.getHand().getCard(2));
		Card opponentCardFour = resolvePowerCard(opponent.getHand().getCard(3));
		int playerOneScore = playerCardOne.getRank() + playerCardTwo.getRank() + playerCardThree.getRank() + playerCardFour.getRank();
		int playerTwoScore = opponentCardOne.getRank() + opponentCardTwo.getRank() + opponentCardThree.getRank() + opponentCardFour.getRank();
		System.out.println("Player 1: " + playerOneScore);
		System.out.println("Player 2: " + playerTwoScore);
	}
	
	public static Card resolvePowerCard(Card testCard) {
		Card returnCard = testCard;
		if (!(returnCard.isPowerCard()))
			return returnCard;
		else {
			while (returnCard.isPowerCard()) {
				if (deck.deckHasCards())
					returnCard = deck.drawCardDeck();
				else
					returnCard = deck.drawCardDiscard();
			}
			return returnCard;
			}
	}
	
	public static void callRatCat() {
		System.out.println("YOU CALLED RAT-A-TAT-CAT");
		ratCalled = true;
	}
	
	

}
