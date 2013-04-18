import java.util.Scanner;


public class Main {

	/**
	 * @param args
	 */
    private static Scanner scanner;
    private static Player player;
    private static ComputerPlayer opponent;
    private static Deck deck;
    private static Card picked;
	private static Card mySwap;
	private static Card yourSwap;
    private static boolean ratCalled;
    private static int gameState;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	    startGame();	    
	    humanTurn();
	    
	    // Check size of Deck, when empty, game over
	    // Give option to yell ratatatcat during player or computer turn (based on AI)
	    // Finalize method could count the hand, any power cards can be thrown away and replaced my discard pile.  Check each hand one at a time for power cards, 
	    // Then add up a final score
	    // Computer turn and Human turn both need option if DECK IS EMPTY
	    
	    

	}

	public static void startGame(){
		
		/**
		 * TODO: Initialize the game here, call all methods that need to be called when the game is started
		 */
	    player = new Player();
	    opponent = new ComputerPlayer(1);
	    deck = new Deck();
	    scanner = new Scanner(System.in);
	    ratCalled = false;
	    gameState = 0; // State 0 = start of the game
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
		// FLAG
		// changeDiscardPicture(topCard.getImage());
		// changeCardPicture(0, player.getHand().getCard(0).getImage());
		// changeCardPicture(3, player.getHand().getCard(3).getImage());
		// WAIT 5 SECONDS 
		// changeCardPicture(0, cardBack);
		// changeCardPicture(3, cardBack);
		
	    System.out.println("Here are the cards in the First and Last position of your hand");
	    System.out.println("FIRST: " + player.getHand().getCard(0).getRank());
	    System.out.println("LAST: " + player.getHand().getCard(3).getRank());
	}
	
	public static void humanTurn() {
		
		gameState = 1; // State 1 = Start of player's turn
    	System.out.println("%%%%%%%%%%%%%%%%%\n");
    	System.out.println("Player's Hand:\n");
    	player.printHand();
    	System.out.println("%%%%%%%%%%%%%%%%%\n");
		System.out.println("Top of Discard Pile: " + deck.peekDiscard());
		System.out.println("Take from Discard Pile (1) or Draw (2) or Yell Rat-A-Tat-Cat (3)");
		
		// FLAG
		// setText("Player 1 Turn, Choose to draw new card, click discard pile, or yell Rat-A-Tat-Cat.");
		
		// Method ends, now wait for a click
		
		String input = scanner.nextLine();
		int inputInt = Integer.parseInt(input);
		if (inputInt == 1) {
			humanDrawDiscard();
		}
		else if (inputInt == 2) {
			humanDrawDeck();
		}
		else if (inputInt == 3) {
			ratCatClicked();
		}
		
	}
	
	public static void computerTurn() {
		opponent.beginTurn();
		Card picked;
        System.out.println("\n\n\n%%%%%%%%%%%%%%%%%\n");
        System.out.println("Computer's Hand:\n");
        opponent.printHand();
        System.out.println("%%%%%%%%%%%%%%%%%\n");
        
        // FLAG 
        // setText("Computer Turn")
        // We could add in more text here to say what the computer is doing, have delays
        // Text/delays should happen throughout the method to show computer's progress
        
        gameState = 2; // State 2 is during the opponent's turn
        
>>>>>>> 5ed553bf894b7f8d0e96d1bac303d8fbceaec9da
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
		if (ratCalled == false)
		  ratCalled = opponent.callRatATat();
		
		if (isGameOver()) {
			System.out.println("game over");
			handleGameOver();
		}
		else {
			System.out.println("human turn");
			humanTurn();
		}
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
		gameState = 4; // The state of a picked Peek card
	    System.out.println("YOU DREW PEEK. PICK WHICH CARD TO PEEK AT: ");
	    
	    // FLAG
	    // setText("You have drawn a Peek card, click which card to peek at.");	    
        // Method ends, wait for click
	    
	    String peekInput = scanner.nextLine();
	    int intPeekInput = Integer.parseInt(peekInput);	    
	    if (intPeekInput == 0) {
	    	humanCardOneClicked();
	    }
	    else if (intPeekInput == 1) {
	    	humanCardTwoClicked();
	    }
	    else if (intPeekInput == 2) {
	    	humanCardThreeClicked();
	    }
	    else if (intPeekInput == 3) {
	    	humanCardFourClicked();
	    }
	}
	
	public static void swap() {
		gameState = 5; // The state of a picked Swap card
		// Need some YES OR NO buttons to pop up 
	    System.out.println("YOU DREW SWAP. DO YOU WANT TO SWAP? (0 - No, 1 - Yes");
	    
	    // FLAG
	    // setText("You have drawn a Swap card, would you like to use it?");
	    // Need some yes or no buttons to pop up
        // Method ends, wait for click
	    
	    String swapDecision = scanner.nextLine();
	    int swapDecisionInt = Integer.parseInt(swapDecision);
	    if (swapDecisionInt == 1) {
            clickedYes();
	    }
	    else {
	    	clickedNo();
	    }
	}
	
	// Need option for power card
	public static void drawTwo() {
		gameState = 8;
		
		// FLAG 
		// setText("You have drawn a draw two card.  Draw your first card.");
		// WAIT 5 SECONDS, THEN DRAW
		
		System.out.println("YOU DREW DRAW TWO. DRAW YOUR FIRST CARD.");
		picked = deck.drawCardDeck();
		
		// FLAG
		// changeCardPicture(9, picked.getImage());
		
		if (picked.isPowerCard()) {
			firstDrawPowerCard();
		}
		else {
			firstDrawNormal();
		}
	}
	
	public static boolean isGameOver() {
		if (deck.deckHasCards())
		  return (ratCalled);
		else
			return true;
	}
	
	public static void handleGameOver() {
		gameState = 99;
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
		String resultString = "Player 1: " + playerOneScore + ". Player 2: " + playerTwoScore + ".";
		
		// FLAG
		// setText(resultString);
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
	
	public static void ratCatClicked() {
		System.out.println("YOU CALLED RAT-A-TAT-CAT");
		
		// FLAG
		// setText("You have called Rat-a-Tat-Cat!");
		ratCalled = true;
		computerTurn();
	}
	
	public static void humanDrawDiscard() {
		picked = deck.drawCardDiscard();
		
		// FLAG
		// changeDiscardPicture(discard.peekDiscard().getImage());
		// changeCardPicture(9, picked.getImage());
		
		cardHasBeenPicked();
	}
	
	public static void humanDrawDeck() {
		picked = deck.drawCardDeck();
		
		// FLAG
		// changeCardPicture(9, picked.getImage());
		
		cardHasBeenPicked();
	}
	
	public static void cardHasBeenPicked() {
		if (picked.isPowerCard()) {
          handlePowerCard();
		} else {
			gameState = 3; // Game State representing when a card is held and waiting to be placed
		    System.out.println("PICK WHERE TO PUT " + picked.getRank() + ".  (Or -1 to discard)");
		    
		    // FLAG
		    // setText("Click where to put your card, or click discard pile to discard.");
	        // Method ends, wait for click
		    
		    String inputTwo = scanner.nextLine();
		    int inputTwoInt = Integer.parseInt(inputTwo);		    
		    if (inputTwoInt == -1) {
		    	discardPileClicked();
		    }
		    else if (inputTwoInt == 0) {
		    	humanCardOneClicked();
		    }
		    else if (inputTwoInt == 1) {
		    	humanCardTwoClicked();
		    }
		    else if (inputTwoInt == 2) {
		    	humanCardThreeClicked();
		    }
		    else if (inputTwoInt == 3) {
		    	humanCardFourClicked();
		    }
		}
		
	}
	
	public static void discardPileClicked() {
		if (gameState == 3) {
		    deck.discard(picked);
		    
		    // FLAG
		    // changeDiscardPicture(picked.getImage());
		    
		    endPlayerTurn();
		} // Handle discarding of normal card
		else if (gameState == 11) {
			deck.discard(picked);
		    
		    // FLAG
		    // changeDiscardPicture(picked.getImage());
		    
			secondDrawTwo();
		} // Throw away first "draw two" card and draw another
	}
	
	public static void humanCardOneClicked() {
		if (gameState == 3) {
			Card left = player.getHand().swapCard(0, picked);
	        if (!(left.isPowerCard()))
	          deck.discard(left);
	        
	        // FLAG
	        // changeDiscardPicture(left.getImage());
	        
	        endPlayerTurn();
		} // Handle normal picking and swapping
		else if (gameState == 4) {
		    System.out.println("PEEKING AT CARD ONE");
		    System.out.println("RANK: " + player.getHand().getCard(0).getRank());
		    
		    // FLAG
		    // setText("Peeking at card one.");
		    // changeCardPicture(0, player.getHand().getCard(0).getImage());
		    // WAIT 5 SECONDS
		    // changeCardPicture(0, cardBack);
		    
		    endPlayerTurn();
		} // Handle Peek Case
		else if (gameState == 6) {
	        mySwap = player.getHand().removeCard(0);
	        chooseYourCardToSwap();
		} // First card picked for swap
		else if (gameState == 11) {
			Card left = player.getHand().swapCard(0, picked);
	        if (!(left.isPowerCard()))
	          deck.discard(left);
	        
	        // FLAG
	        // changeDiscardPicture(left.getImage());
	        
	        endPlayerTurn();	
		} // Using first draw two card like normal
	}
	
	public static void humanCardTwoClicked() {
		if (gameState == 3) {
			Card left = player.getHand().swapCard(1, picked);
	        if (!(left.isPowerCard()))
	          deck.discard(left);
	        
	        // FLAG
	        // changeDiscardPicture(left.getImage());
	        
	        endPlayerTurn();
		} // Handle normal picking and swapping
		else if (gameState == 4) {
		    System.out.println("PEEKING AT CARD TWO");
		    System.out.println("RANK: " + player.getHand().getCard(1).getRank());
		    
		    // FLAG
		    // setText("Peeking at card one.");
		    // changeCardPicture(1, player.getHand().getCard(1).getImage());
		    // WAIT 5 SECONDS
		    // changeCardPicture(1, cardBack);
		    
		    endPlayerTurn();
		} // Handle Peek Case
		else if (gameState == 6) {
	        mySwap = player.getHand().removeCard(1);
	        chooseYourCardToSwap();
		} // First card picked for swap
		else if (gameState == 11) {
			Card left = player.getHand().swapCard(1, picked);
	        if (!(left.isPowerCard()))
	          deck.discard(left);
	        
	        // FLAG
	        // changeDiscardPicture(left.getImage());
	        
	        endPlayerTurn();	
		} // Using first draw two card like normal
	}
	
	public static void humanCardThreeClicked() {
		if (gameState == 3) {
			Card left = player.getHand().swapCard(2, picked);
	        if (!(left.isPowerCard()))
	          deck.discard(left);
	        
	        // FLAG
	        // changeDiscardPicture(left.getImage());
	        
	        endPlayerTurn();
		} // Handle normal picking and swapping
		else if (gameState == 4) {
		    System.out.println("PEEKING AT CARD THREE");
		    System.out.println("RANK: " + player.getHand().getCard(2).getRank());
		    
		    // FLAG
		    // setText("Peeking at card one.");
		    // changeCardPicture(2, player.getHand().getCard(2).getImage());
		    // WAIT 5 SECONDS
		    // changeCardPicture(2, cardBack);
		    
		    endPlayerTurn();
		} // Handle Peek Case
		else if (gameState == 6) {
	        mySwap = player.getHand().removeCard(2);
	        chooseYourCardToSwap();
		} // First card picked for swap
		else if (gameState == 11) {
			Card left = player.getHand().swapCard(2, picked);
	        if (!(left.isPowerCard()))
	          deck.discard(left);
	        
	        // FLAG
	        // changeDiscardPicture(left.getImage());
	        
	        endPlayerTurn();	
		} // Using first draw two card like normal
	}
	
	public static void humanCardFourClicked() {
		if (gameState == 3) {
			Card left = player.getHand().swapCard(3, picked);
	        if (!(left.isPowerCard()))
	          deck.discard(left);
	        
	        // FLAG
	        // changeDiscardPicture(left.getImage());
	        
	        endPlayerTurn();
		} // Handle normal picking and swapping
		else if (gameState == 4) {
		    System.out.println("PEEKING AT CARD FOUR");
		    System.out.println("RANK: " + player.getHand().getCard(3).getRank());
		    
		    // FLAG
		    // setText("Peeking at card one.");
		    // changeCardPicture(3, player.getHand().getCard(3).getImage());
		    // WAIT 5 SECONDS
		    // changeCardPicture(3, cardBack);
		    
		    endPlayerTurn();
		} // Handle Peek Case
		else if (gameState == 6) {
	        mySwap = player.getHand().removeCard(3);
	        chooseYourCardToSwap();
		} // First card picked for swap
		else if (gameState == 11) {
			Card left = player.getHand().swapCard(3, picked);
	        if (!(left.isPowerCard()))
	          deck.discard(left);
	        
	        // FLAG
	        // changeDiscardPicture(left.getImage());
	        
	        endPlayerTurn();	
		} // Using first draw two card like normal
	}
	
	public static void computerCardOneClicked() {
		if (gameState == 7) {
			yourSwap = opponent.getHand().removeCard(0);
			finishSwap();
		} // Second card picked for swap
	}
	
	public static void computerCardTwoClicked() {
		if (gameState == 7) {
			yourSwap = opponent.getHand().removeCard(1);
			finishSwap();
		} // Second card picked for swap
	}
	
	public static void computerCardThreeClicked() {
		if (gameState == 7) {
			yourSwap = opponent.getHand().removeCard(2);
			finishSwap();
		} // Second card picked for swap
	}
	
	public static void computerCardFourClicked() {
		if (gameState == 7) {
			yourSwap = opponent.getHand().removeCard(3);
			finishSwap();
		} // Second card picked for swap
	}
	
	public static void endPlayerTurn() {
		
		// FLAG
		// changedCardPicture(9, cardBack);
		
		if (isGameOver()) {
			handleGameOver();
		}
		else {
			
			// FLAG 
			// setText("Computer's turn begins now.")
			
			computerTurn();
		}
	}
	
	public static void handlePowerCard() {
		System.out.println("YOU PICKED POWER CARD!");
		
		// FLAG 
		// setText("You drew a power card!");
		// WAIT 3 SECONDS
		
		if (picked.getRank() == 10) {
			drawTwo();
		}
		else if (picked.getRank() == 11) {
			peek();
		}
		else {
			swap();
		}
	}
	
	public static void clickedYes() {
		
		// Get rid of Yes/No buttons now that they have been clicked
		
		if (gameState == 5) {
			chooseMyCardToSwap();
		} // Answered yes to "swap"
		else if (gameState == 9) {
			peek();
		} // Answered yes to "use draw two first card peek"
		else if (gameState == 10) {
			chooseMyCardToSwap();
		} // Answered yes to "use draw two first card swap"
	}
	
	public static void clickedNo() {
		
		// Get rid of Yes/No buttons now that they have been clicked
		
		if (gameState == 5) {
			System.out.println("Okay your turn is over.");
			
            // FLAG
			// setText("Okay, your turn is over.");
			
			endPlayerTurn();
		}  // Answered no to "swap"
		else if (gameState == 9) {
			secondDrawTwo();
		} // Answered no to "use draw two first card peek"
		else if (gameState == 10) {
			secondDrawTwo();
		} // Answered no to "use draw two first card swap"
	}
	
	public static void chooseMyCardToSwap() {
		gameState = 6;
        System.out.println("CHOOSE WHICH OF YOUR CARDS TO SWAP: ");	
        
        // FLAG
        // setText("Click one of your cards to swap.");
        // Method ends, wait for click
        
        String mySwapInput = scanner.nextLine();
        int mySwapInt = Integer.parseInt(mySwapInput);
	    if (mySwapInt == 0) {
	    	humanCardOneClicked();
	    }
	    else if (mySwapInt == 1) {
	    	humanCardTwoClicked();
	    }
	    else if (mySwapInt == 2) {
	    	humanCardThreeClicked();
	    }
	    else if (mySwapInt == 3) {
	    	humanCardFourClicked();
	    }
	}
	
	public static void chooseYourCardToSwap() {
		gameState = 7;
        System.out.println("CHOOSE WHICH OPPONENT CARD TO SWAP: ");
        
        // FLAG
        // setText("Choose one of your opponents card to swap with.");
        // Method ends, wait for click
        
        String yourSwapInput = scanner.nextLine();
        int yourSwapInt = Integer.parseInt(yourSwapInput);
	    if (yourSwapInt == 0) {
	    	computerCardOneClicked();
	    }
	    else if (yourSwapInt == 1) {
	    	computerCardTwoClicked();
	    }
	    else if (yourSwapInt == 2) {
	    	computerCardThreeClicked();
	    }
	    else if (yourSwapInt == 3) {
	    	computerCardFourClicked();
	    }
        
	}
	
	public static void finishSwap() {
        player.getHand().addCard(yourSwap);
        opponent.getHand().addCard(mySwap);
        System.out.println("swap successful");
        
        // FLAG
        // setText("Okay! Swap successful!");
        // WAIT 3 SECONDS
        
        endPlayerTurn();
	}
	
	public static void firstDrawPowerCard() {
		System.out.println("YOU DREW A POWER CARD");
		
		// FLAG
		// setText("You have drawn a power card!");
		// WAIT 3 SECONDS
		
		if (picked.getRank() == 10) {
			System.out.println("You drew another Draw Two, start again!");
			
			// FLAG
			// setText("You have drawn another Draw Two card, start again!");
			// WAIT 3 SECONDS
			
			drawTwo();
		}
		else if (picked.getRank() == 11) {
			gameState = 9;
			System.out.println("YOU DREW A PEEK (0 TO USE OR 1 TO USE YOUR SECOND DRAW)");
			
			// FLAG
			// setText("You drew a peek card, click yes to use, or no to try your second draw.");
			// Method ends, wait for click
			// Need yes or no buttons to pop up
			
			String drawTwoPeekDec = scanner.nextLine();
			int drawTwoPeekDecInt = Integer.parseInt(drawTwoPeekDec);
			if (drawTwoPeekDecInt == 0) {
			    clickedYes();
			}
			else if (drawTwoPeekDecInt == 1) {
				clickedNo();
			}
		}
		else {
			gameState = 10;
			System.out.println("YOU DREW A SWAP (0 TO USE OR 1 TO USE YOUR SECOND DRAW)");
			
			// FLAG
			// setText("You drew a swap card, click yes to use, or not to try your second draw.");
			// Method ends, wait for click
			// Need yes or no buttons to pop up
			
			String drawTwoSwapDec = scanner.nextLine();
			int drawTwoSwapDecInt = Integer.parseInt(drawTwoSwapDec);
			if (drawTwoSwapDecInt == 0) {
				clickedYes();
			} else if (drawTwoSwapDecInt == 1) {
				clickedNo();
			}
		}
	}
	
	public static void firstDrawNormal() {
		gameState = 11;
		System.out.println("YOU DREW: " + picked.getRank());
		System.out.println("SELECT WHICH CARD TO REPLACE, OR -1 TO DISCARD/USE YOUR SECOND DRAW");
		
		// FLAG
		// changeCardPicture(9, picked.getImage());
		// setText("Select which card to replace, or discard and draw again.");
		// Method ends, wait for click
		// Click discard to use your second draw
		
		String twoDecision = scanner.nextLine();
		int twoDecInt = Integer.parseInt(twoDecision);
	    if (twoDecInt == -1) {
	    	discardPileClicked();
	    }
	    else if (twoDecInt == 0) {
	    	humanCardOneClicked();
	    }
	    else if (twoDecInt == 1) {
	    	humanCardTwoClicked();
	    }
	    else if (twoDecInt == 2) {
	    	humanCardThreeClicked();
	    }
	    else if (twoDecInt == 3) {
	    	humanCardFourClicked();
	    }
	}
	
	public static void secondDrawTwo() {
	    System.out.println("Starting second draw");
		picked = deck.drawCardDeck();
		
		// FLAG
		// setText("Starting second draw.");
		// changeCardPicture(9, picked.getImage());
		
		cardHasBeenPicked();        
	}
	

	
	
	

}
