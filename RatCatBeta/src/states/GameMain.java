package states;

import gameplay.Card;
import gameplay.ComputerPlayer;
import gameplay.Deck;
import gameplay.Player;
import gui.GameApplet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import main.Switchboard;
import main.GameState;
import main.StateManager;

/**
 * The game state in which the game is played
 * @author Adam
 *
 */
public class GameMain extends GameState {
	
	public static int difficulty = 1;
	
	private static boolean loaded = false;
	
	private int longWait = 3000;
	private int shortWait = 2000;
	
	private float timer = 0;
	private float timerId = 0;
	
    private Player player;
    private ComputerPlayer opponent;
    private Deck deck;
    
    private Card picked;
	private Card mySwap;
	private Card yourSwap;
    
	private boolean ratCalled;
    private int gameState;
    
    private boolean newGame;
	
    private int clickIndex;
    
	private final Font displayFont = new Font("Sans Serif", Font.PLAIN, 16);
	
	public float stateID(){
		return StateManager.gameMainID;
	}
	
	public void initialize(){
		addObject("backdrop","gameMain/backdrop.png", 0, 0);
		addObject("exit","gameMain/exit.png", 25, 525);
		addObject("instruct", "gameMain/instruct.png", 488, 525);
		addObject("reports", "gameMain/reports.png", 950, 525);
		
		addObject("deck", "gameMain/cards/deck.png", 25 , 107);
		addObject("discard", Card.backImage, 190 , 107);
		addObject("drawn", Card.blankImage, 107, 307);
		
		addObject("yes","gameMain/yes.png", 900, 237);
		addObject("no","gameMain/no.png", 1025, 237);
		
		addObject("cCard1", Card.backImage, 400 , 25);
		addObject("cCard2", Card.backImage, 600 , 25);
		addObject("cCard3", Card.backImage, 800 , 25);
		addObject("cCard4", Card.backImage, 1000 , 25);
		
		addObject("hCard1", Card.backImage, 400 , 325);
		addObject("hCard2", Card.backImage, 600 , 325);
		addObject("hCard3", Card.backImage, 800 , 325);
		addObject("hCard4", Card.backImage, 1000 , 325);
	}
	
	public void entered(){
		if(!loaded){
			addObject("display", "Welcome.", displayFont, 400, 240, Color.black);
			loaded = true;
		}
		
		System.out.println(Switchboard.getLastState());
		
		if(Switchboard.getLastState() == StateManager.modeMenuID){
			newGame = true;
			System.out.println("RESET");
		} else {
			newGame = false;
		}
	}
	
	public void handleFrame(Point mousePos) {
		if(gameState == 99) return;
		
		if(newGame == true){
			newGame = false;
			System.out.println("NEW");
			startGame();
			return;
		}
		
		if(timer > 0){
			timer = Math.max(timer - 33, 0);
			
			if(timer == 0){
				endWait();
			}
		}
	}
	
	public void handleMouseClick(Point mousePos) {
		if(timer > 0) return;
		
		if(getObject("exit").isInRange(mousePos)){
			// exit clicked - return to main menu
			Switchboard.setState(StateManager.mainMenuID);
			return;
		}
		
		if(getObject("instruct").isInRange(mousePos)){
			// instructions clicked - display instructions
			Switchboard.setState(StateManager.instructionsID);
			return;
		}
		
		if(getObject("reports").isInRange(mousePos)){
			GameApplet.window.call("loadReports", null);
			return;
		}
		
		if(getObject("deck").isInRange(mousePos)){
			// deck clicked
			deckClicked();
			return;
		}
		
		if(getObject("discard").isInRange(mousePos)){
			// discard clicked
			discardPileClicked();
			return;
		}
		
		if(getObject("yes").isInRange(mousePos)){
			// rattat clicked
			clickedYes();
			return;
		}
		
		if(getObject("no").isInRange(mousePos)){
			// rattat clicked
			clickedNo();
			return;
		}
		
		if(getObject("hCard1").isInRange(mousePos)){
			// card clicked
			cardClicked(1);
			return;
		}
		
		if(getObject("hCard2").isInRange(mousePos)){
			// card clicked
			cardClicked(2);
			return;
		}
		
		if(getObject("hCard3").isInRange(mousePos)){
			// card clicked
			cardClicked(3);
			return;
		}
		
		if(getObject("hCard4").isInRange(mousePos)){
			// card clicked
			cardClicked(4);
			return;
		}
		
		if(getObject("cCard1").isInRange(mousePos)){
			// card clicked
			cardClicked(5);
			return;
		}
		
		if(getObject("cCard2").isInRange(mousePos)){
			// card clicked
			cardClicked(6);
			return;
		}
		
		if(getObject("cCard3").isInRange(mousePos)){
			// card clicked
			cardClicked(7);
			return;
		}
		
		if(getObject("cCard4").isInRange(mousePos)){
			// card clicked
			cardClicked(8);
			return;
		}
	}
	
	public void changeCardPicture(int index, String newImage){
		if(index >= 1 && index <= 4){
			getObject("hCard" + index).setImage(findImage(newImage), 0);
			return;
		}
		
		if(index >= 5 && index <= 8){
			getObject("cCard" + (index - 4)).setImage(findImage(newImage), 0);
			return;
		}
		
		if(index == 9){
			getObject("drawn").setImage(findImage(newImage), 0);
			return;
		}
	}
	
	public void changeDiscardPicture(String newImage){
		getObject("discard").setImage(findImage(newImage), 0);
	}
	
	public void setText(String newText){
		getObject("display").setText(newText, 0);
	}
	
	public void startGame(){
		
	    player = new Player();
	    opponent = new ComputerPlayer(difficulty);
	    deck = new Deck();
	    ratCalled = false;
	    gameState = 0; // State 0 = start of the game
		deck.buildRatCatDeck();
		deck.shuffle();
		
		picked = null;
		mySwap = null;
		yourSwap = null;
		
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
		changeDiscardPicture(topCard.getImage());
		
		getObject("yes").setVisible(false);
		getObject("no").setVisible(false);
		
		changeCardPicture(1, player.getHand().getCard(0).getImage());
		changeCardPicture(2, Card.backImage);
		changeCardPicture(3, Card.backImage);
		changeCardPicture(4, player.getHand().getCard(3).getImage());
		changeCardPicture(5, Card.backImage);
		changeCardPicture(6, Card.backImage);
		changeCardPicture(7, Card.backImage);
		changeCardPicture(8, Card.backImage);
		
		setText("Peek at two of your cards");
		
		System.out.println("DoneStartup");
		
		// *WAIT*
		timer = longWait;
		timerId = 1;
	}
	
	public void startGame2(){
		changeCardPicture(1, Card.backImage);
		changeCardPicture(4, Card.backImage);
		
		humanTurn();
		
	    //System.out.println("Here are the cards in the First and Last position of your hand");
	    //System.out.println("FIRST: " + player.getHand().getCard(0).getRank());
	    //System.out.println("LAST: " + player.getHand().getCard(3).getRank());
	}
	
	public void peek() {
		gameState = 4; // The state of a picked Peek card
	    //System.out.println("YOU DREW PEEK. PICK WHICH CARD TO PEEK AT: ");
	    
	    setText("You have drawn a Peek card, click which card to peek at.");	    
        /* Method ends, wait for click
	    
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
	    }*/
	}
	
	public void swap() {
		gameState = 5; // The state of a picked Swap card
		// Need some YES OR NO buttons to pop up 
	    //System.out.println("YOU DREW SWAP. DO YOU WANT TO SWAP? (0 - No, 1 - Yes");
	    
	    setText("You have drawn a Swap card, would you like to use it?");
	    // Need some yes or no buttons to pop up
        /* Method ends, wait for click
	    
	    String swapDecision = scanner.nextLine();
	    int swapDecisionInt = Integer.parseInt(swapDecision);
	    if (swapDecisionInt == 1) {
            clickedYes();
	    }
	    else {
	    	clickedNo();
	    }*/
	    
	    getObject("yes").setVisible(true);
		getObject("no").setVisible(true);
	}
	
	// Need option for power card
	public void drawTwo() {
		gameState = 8;
		
		setText("You have drawn a draw two card.  Draw your first card.");
		// *WAIT*
		timer = longWait;
		timerId = 2;
	}
	
	public void drawTwo2(){
		//System.out.println("YOU DREW DRAW TWO. DRAW YOUR FIRST CARD.");
		picked = deck.drawCardDeck();
		
		changeCardPicture(9, picked.getImage());
		
		if (picked.isPowerCard()) {
			firstDrawPowerCard();
		}
		else {
			firstDrawNormal();
		}
	}
	
	public boolean isGameOver() {
		if (deck.deckHasCards())
		  return (ratCalled);
		else
			return true;
	}
	
	public void handleGameOver() {
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
		//System.out.println("Player 1: " + playerOneScore);
		//System.out.println("Player 2: " + playerTwoScore);
		String resultString = "Player 1: " + playerOneScore + ". Player 2: " + playerTwoScore + ".";
		
		setText(resultString);
		
		changeCardPicture(1, playerCardOne.getImage());
		changeCardPicture(2, playerCardTwo.getImage());
		changeCardPicture(3, playerCardThree.getImage());
		changeCardPicture(4, playerCardFour.getImage());
		
		changeCardPicture(5, opponentCardOne.getImage());
		changeCardPicture(6, opponentCardTwo.getImage());
		changeCardPicture(7, opponentCardThree.getImage());
		changeCardPicture(8, opponentCardFour.getImage());
	}
	
	public Card resolvePowerCard(Card testCard) {
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
	
	public void handlePowerCard() {
		//System.out.println("YOU PICKED POWER CARD!");
		
		setText("You drew a power card!");
		// *WAIT*
		timer = shortWait;
		timerId = 3;
	}
	
	public void handlePowerCard2(){
		if (picked.getRank() == 10) {
			drawTwo();
		}
		else if (picked.getRank() == 11) {
			peek();
		}
		else {
			swap();
		}
		return;
	}
	
	public void ratCatClicked() {
		//System.out.println("YOU CALLED RAT-A-TAT-CAT");
		
		setText("You have called Rat-a-Tat-Cat!");
		ratCalled = true;
		
		timer = shortWait;
		timerId = 10;
	}
	
	public void humanDrawDiscard() {
		picked = deck.drawCardDiscard();
		
		changeDiscardPicture(deck.peekDiscard().getImage());
		changeCardPicture(9, picked.getImage());
		
		cardHasBeenPicked();
	}
	
	public void humanDrawDeck() {
		picked = deck.drawCardDeck();
		
		changeCardPicture(9, picked.getImage());
		
		cardHasBeenPicked();
	}
	
	public void cardHasBeenPicked() {
		if (picked.isPowerCard()) {
          handlePowerCard();
		} else {
			gameState = 3; // Game State representing when a card is held and waiting to be placed
		    //System.out.println("PICK WHERE TO PUT " + picked.getRank() + ".  (Or -1 to discard)");
		    
		    setText("Click where to put your card, or click discard pile to discard.");
	        /*/ Method ends, wait for click
		    
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
		    */
		}
		
	}
	
	public void deckClicked(){
		if(gameState == 1){
			humanDrawDeck();
		}
	}
	
	public void discardPileClicked() {
		if(gameState == 1){
			humanDrawDiscard();
			return;
		}
		
		if (gameState == 3) {
		    if(!picked.isPowerCard()){
		    	deck.discard(picked);
		    	changeDiscardPicture(picked.getImage());
		    }
		    
		    endPlayerTurn();
		} // Handle discarding of normal card
		else if (gameState == 11) {
			if(!picked.isPowerCard()){
				deck.discard(picked);
				changeDiscardPicture(picked.getImage());
			}
		    
			secondDrawTwo();
		} // Throw away first "draw two" card and draw another
	}
	
	public void endPlayerTurn() {
		
		changeCardPicture(9, Card.blankImage);
		
		if (isGameOver()) {
			handleGameOver();
		}
		else {
			
			// DO YOU CALL RAT CAT?
			setText("Would you like to call rat cat?");
			gameState = 69;
			
			getObject("yes").setVisible(true);
			getObject("no").setVisible(true);
		}
	}
	
	public void endPlayerTurn2(){
		setText("Computer's turn begins now.");
		
		// *WAIT*
		timer = shortWait;
		timerId = 8;
	}
	
	public void clickedYes() {
		
		// Get rid of Yes/No buttons now that they have been clicked
		getObject("yes").setVisible(false);
		getObject("no").setVisible(false);
		
		if (gameState == 5) {
			chooseMyCardToSwap();
		} // Answered yes to "swap"
		else if (gameState == 9) {
			peek();
		} // Answered yes to "use draw two first card peek"
		else if (gameState == 10) {
			chooseMyCardToSwap();
		} // Answered yes to "use draw two first card swap"
		else if(gameState == 69){
			ratCatClicked();
		}
	}
	
	public void clickedNo() {
		
		// Get rid of Yes/No buttons now that they have been clicked
		getObject("yes").setVisible(false);
		getObject("no").setVisible(false);
		
		if (gameState == 5) {
			//System.out.println("Okay your turn is over.");
			
            setText("Okay, your turn is over.");
			
			endPlayerTurn();
		}  // Answered no to "swap"
		else if (gameState == 9) {
			secondDrawTwo();
		} // Answered no to "use draw two first card peek"
		else if (gameState == 10) {
			secondDrawTwo();
		} // Answered no to "use draw two first card swap"
		else if(gameState == 69){
			endPlayerTurn2();
		}
	}
	
	public void chooseMyCardToSwap() {
		gameState = 6;
        //System.out.println("CHOOSE WHICH OF YOUR CARDS TO SWAP: ");	
        
        setText("Click one of your cards to swap.");
        /* Method ends, wait for click
        
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
	    }*/
	}
	
	public void chooseYourCardToSwap() {
		gameState = 7;
        //System.out.println("CHOOSE WHICH OPPONENT CARD TO SWAP: ");
        
        setText("Choose one of your opponents card to swap with.");
        /* Method ends, wait for click
        
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
	    */
        
	}
	
	public void finishSwap() {
        player.getHand().addCard(yourSwap);
        opponent.getHand().addCard(mySwap);
        //System.out.println("swap successful");
        
        setText("Okay! Swap successful!");
        // *WAIT*
        timer = shortWait;
        timerId = 4;
	}
	
	public void firstDrawPowerCard() {
		//System.out.println("YOU DREW A POWER CARD");
		
		setText("You have drawn a power card!");
		// *WAIT*
		timer = shortWait;
		timerId = 5;
	}
	
	public void firstDrawPowerCard2(){
		if (picked.getRank() == 10) {
			//System.out.println("You drew another Draw Two, start again!");
			
			setText("You have drawn another Draw Two card, start again!");
			// *WAIT*
			timer = shortWait;
			timerId = 6;
			return;
		}
		else if (picked.getRank() == 11) {
			gameState = 9;
			//System.out.println("YOU DREW A PEEK (0 TO USE OR 1 TO USE YOUR SECOND DRAW)");
			
			setText("You drew a peek card, click yes to use, or no to try your second draw.");
			/* Method ends, wait for click
			// Need yes or no buttons to pop up
			
			String drawTwoPeekDec = scanner.nextLine();
			int drawTwoPeekDecInt = Integer.parseInt(drawTwoPeekDec);
			if (drawTwoPeekDecInt == 0) {
			    clickedYes();
			}
			else if (drawTwoPeekDecInt == 1) {
				clickedNo();
			}
			*/
			
			getObject("yes").setVisible(true);
			getObject("no").setVisible(true);
		}
		else {
			gameState = 10;
			//System.out.println("YOU DREW A SWAP (0 TO USE OR 1 TO USE YOUR SECOND DRAW)");
			
			setText("You drew a swap card, click yes to use, or not to try your second draw.");
			/* Method ends, wait for click
			// Need yes or no buttons to pop up
			
			String drawTwoSwapDec = scanner.nextLine();
			int drawTwoSwapDecInt = Integer.parseInt(drawTwoSwapDec);
			if (drawTwoSwapDecInt == 0) {
				clickedYes();
			} else if (drawTwoSwapDecInt == 1) {
				clickedNo();
			}
			*/
			
			getObject("yes").setVisible(true);
			getObject("no").setVisible(true);
		}
	}
	
	public void firstDrawNormal() {
		gameState = 11;
		//System.out.println("YOU DREW: " + picked.getRank());
		//System.out.println("SELECT WHICH CARD TO REPLACE, OR -1 TO DISCARD/USE YOUR SECOND DRAW");
		
		changeCardPicture(9, picked.getImage());
		setText("Select which card to replace, or discard and draw again.");
		/* Method ends, wait for click
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
	    */
	}
	
	public void secondDrawTwo() {
	    //System.out.println("Starting second draw");
		picked = deck.drawCardDeck();
		
		setText("Starting second draw.");
		changeCardPicture(9, picked.getImage());
		
		cardHasBeenPicked();        
	}
	
	public void humanTurn() {
		
		gameState = 1; // State 1 = Start of player's turn
    	//System.out.println("%%%%%%%%%%%%%%%%%\n");
    	//System.out.println("Player's Hand:\n");
    	player.printHand();
    	//System.out.println("%%%%%%%%%%%%%%%%%\n");
		//System.out.println("Top of Discard Pile: " + deck.peekDiscard());
		//System.out.println("Take from Discard Pile (1) or Draw (2) or Yell Rat-A-Tat-Cat (3)");
		
		setText("Player 1 Turn: Choose to draw new card or click discard pile");
		
		/* Method ends, now wait for a click
		
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
		*/
	}
	
	public void computerTurn() {
        //System.out.println("\n\n\n%%%%%%%%%%%%%%%%%\n");
        //System.out.println("Computer's Hand:\n");
        opponent.printHand();
        //System.out.println("%%%%%%%%%%%%%%%%%\n");
        
        setText("Computer Turn");
        
        gameState = 2; // State 2 is during the opponent's turn
        
		if (!deck.discardHasCards()) {
			picked = deck.drawCardDeck();
		}else{
			if(opponent.decideDrawCard(deck.peekDiscard())){
				picked = deck.drawCardDiscard();
			}else{
				picked = deck.drawCardDeck();
			}
		}
        
        // *WAIT*
        timer = shortWait;
        timerId = 7;
	} // Needs some form of AI, even basic for testing purposes
	
	public void computerTurn2(){
		if(!picked.isPowerCard()){
			//if it is numeric play as usual
			Card discarded = opponent.playCard(picked);
			
			deck.discard(discarded);
			changeDiscardPicture(discarded.getImage());
		}else{
			//handle the playing of a power card
			computerPowerCard(picked);
		}

		opponent.incrementTurnCounter();
		
		
		changeCardPicture(9, Card.blankImage);
		
		if (isGameOver()) {
			//System.out.println("game over");
			handleGameOver();
		}
		else {
			ratCalled = opponent.callRatATat();
			
			//System.out.println("human turn");
			humanTurn();
		}
	}
	
	// Make separate power card methods for AI
	
	public void computerPowerCard(Card card){
		switch(card.getRank()){
			case 10:
				//discard the draw 2
				//deck.discard(card);
				//changeDiscardPicture(card.getImage());
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
					//deck.discard(newPick);
					//changeDiscardPicture(newPick.getImage());
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
						changeDiscardPicture(newPick.getImage());
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
				changeDiscardPicture(discard.getImage());
				break;
			case 11:
				Card discarded = opponent.playCard(card);
				
				if(!discarded.isPowerCard()){
					deck.discard(discarded);
					changeDiscardPicture(discarded.getImage());
				}
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
				//deck.discard(card);
				//changeDiscardPicture(card.getImage());
				break;
		}
	}
	
	public void cardClicked(int index){
		if(index <= 4){
			humanCardClicked(index);
			return;
		}
		
		computerCardClicked(index - 4);
	}
	
	public void humanCardClicked(int index){ // index is 1-4
		if (gameState == 3) {
			Card left = player.getHand().swapCard(index - 1, picked);
	        if (!(left.isPowerCard())) {
	          deck.discard(left);
	          changeDiscardPicture(left.getImage());
	        }
	        
	        endPlayerTurn();
		} // Handle normal picking and swapping
		else if (gameState == 4) {
		    //System.out.println("PEEKING AT CARD ONE");
		    //System.out.println("RANK: " + player.getHand().getCard(index - 1).getRank());
		    
		    setText("Peeking at card.");
		    changeCardPicture(index, player.getHand().getCard(0).getImage());
		    
		    clickIndex = index;
		    // *WAIT*
		    timer = longWait;
		    timerId = 9;
		    return;
		} // Handle Peek Case
		else if (gameState == 6) {
	        mySwap = player.getHand().removeCard(index - 1);
	        chooseYourCardToSwap();
		} // First card picked for swap
		else if (gameState == 11) {
			Card left = player.getHand().swapCard(index - 1, picked);
	        if(!left.isPowerCard()){
	           deck.discard(left);
	           changeDiscardPicture(left.getImage());
	        }
	        
	        endPlayerTurn();	
		} // Using first draw two card like normal
	}
	
	public void computerCardClicked(int index){ // index is 1-4
		if (gameState == 7) {
			yourSwap = opponent.getHand().removeCard(index);
			finishSwap();
		}
	}
	
	public void endWait(){
		if(timerId == 1){
			startGame2();
			return;
		}
		
		if(timerId == 2){
			drawTwo2();
			return;
		}
		
		if(timerId == 3){
			handlePowerCard2();
			return;
		}
		
		if(timerId == 4){
			endPlayerTurn();
			return;
		}
		
		if(timerId == 5){
			firstDrawPowerCard2();
			return;
		}
		
		if(timerId == 6){
			drawTwo();
			return;
		}
		
		if(timerId == 7){
			computerTurn2();
			return;
		}
		
		if(timerId == 8){
			computerTurn();
			return;
		}
		
		if(timerId == 9){
			changeCardPicture(clickIndex, Card.backImage);
			clickIndex = 0;
		    endPlayerTurn();
		    return;
		}
		
		if(timerId == 10){
			endPlayerTurn2();
		}
	}
	
	public void handleMousePressed(Point mousePos) {}
	
	public void handleMouseReleased(Point mousePos) {}
	
	public void handleKeyPressed(int keyID) {}

	public void handleKeyReleased(int keyID) {}
	
	public void closeState() {}
}