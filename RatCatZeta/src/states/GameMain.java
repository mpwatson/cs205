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
	
	// the lengths for timers
	private int longWait = 3000;
	private int shortWait = 2000;
	
	// the timer
	private float timer = 0;
	private float timerId = 0;
	
	// the players and the deck
    private Player player;
    private ComputerPlayer opponent;
    private Deck deck;
    
    // currently drawn or selected cards
    private Card picked;
	private Card mySwap;
	private Card yourSwap;
    
	// made true when someone calls ratcat
	private boolean ratCalled;
	// indicates where in the game flow we are
    private int gameState;
    
    // indicates whether a new game should be started, set when state is entered
    private boolean newGame;
	
    // the running player score
    private int playerTotal;
    // the running computer score
    private int computerTotal;
    // Whether the entire game has ended
    private boolean gameOver;
    
    // remembers which card was last selected, used for power cards
    private int clickIndex;
    
    // Used for swapping cards
    private int[] swap;
    
    // the font of the text box
	private final Font displayFont = new Font("Lucida Calligraphy", Font.PLAIN, 16);
	
	public float stateID(){
		return StateManager.gameMainID;
	}
	
	/**
	 * Called when the applet is loaded
	 */
	public void initialize(){
		addObject("backdrop","gameMain/backdrop.png", 0, 0);
		addObject("exit","gameMain/exit.png", 25, 525);
		addObject("instruct", "gameMain/instruct.png", 488, 525);
		addObject("reports", "gameMain/reports.png", 950, 525);
		
		addObject("deck", "gameMain/cards/deck.png", 25 , 107);
		addObject("discard", Card.backImage, 190 , 107);
		addObject("drawn", Card.backImage, 107, 307);
		getObject("drawn").setVisible(false);
		
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
	
	/**
	 * Called when the state is entered
	 */
	public void entered(){
		if(!loaded){
			addObject("display", "Welcome.", displayFont, 400, 240, Color.black);
			loaded = true;
		}
		
		if(Switchboard.getLastState() == StateManager.modeMenuID){
			newGame = true;
			gameState = 1;
			gameOver = false;
			playerTotal = 0;
			computerTotal = 0;
		} else {
			newGame = false;
		}
	}
	
	/**
	 * Called constantly
	 */
	public void handleFrame(Point mousePos) {
		if(gameState == 99) return;
		
		if(newGame == true){
			newGame = false;
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
	
	/**
	 * Called when mouse is clicked
	 */
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
			if(GameApplet.window != null) GameApplet.window.call("loadReports", null);
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
	
	/**
	 *  Changes the picture of a specified card
	 * @param index
	 * @param newImage
	 */
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
			if(newImage == null){
				getObject("drawn").setVisible(false);
				return;
			}
			
			getObject("drawn").setVisible(true);
			getObject("drawn").setImage(findImage(newImage), 0);
			return;
		}
	}
	
	/**
	 * changes the picture of the discard pile
	 */
	public void changeDiscardPicture(String newImage){
		if(deck.discardHasCards()){
			getObject("discard").setVisible(true);
		}else{
			getObject("discard").setVisible(false);
		}
		
		getObject("discard").setImage(findImage(newImage), 0);
	}
	
	/**
	 * Set the text of the text box
	 * @param newText
	 */
	public void setText(String newText){
		getObject("display").setText(newText, 0);
	}
	
	/**
	 * Initialize a round
	 */
	public void startGame(){
		
	    player = new Player();
	    opponent = new ComputerPlayer(difficulty);
	    deck = new Deck();
	    ratCalled = false;
	    gameState = 0; // State 0 = start of the game
		deck.buildRatCatDeck();
		deck.shuffle();
		
		if(GameApplet.window != null) GameApplet.window.call("setDifficulty", new Object[] {difficulty});
		
		picked = null;
		mySwap = null;
		yourSwap = null;
		
		for(int i = 0;i < 4;i++){
			player.getHand().addCardByIndex(deck.drawCardDeck(), i);
			opponent.getHand().addCardByIndex(deck.drawCardDeck(), i);
		}
		
		// determine opponent's hand value
		int aiHand = (opponent.getHand().getCard(0).getRank() >=9 ? 5 : opponent.getHand().getCard(0).getRank()) + 
				(opponent.getHand().getCard(1).getRank() >=9 ? 5 : opponent.getHand().getCard(1).getRank()) + 
				(opponent.getHand().getCard(2).getRank() >=9 ? 5 : opponent.getHand().getCard(2).getRank()) + 
				(opponent.getHand().getCard(3).getRank() >=9 ? 5 : opponent.getHand().getCard(3).getRank());
		if(GameApplet.window != null) GameApplet.window.call("addInitialHandScore", new Object[] {aiHand});
		
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
		changeCardPicture(9, null);
		
		setText("Peek at two of your cards");
		
		// *WAIT*
		timer = longWait;
		timerId = 1;
	}
	
	/**
	 * Continue initialzing a round
	 */
	public void startGame2(){
		changeCardPicture(1, Card.backImage);
		changeCardPicture(4, Card.backImage);
		
		humanTurn();
	}
	
	/**
	 * Peek at a card
	 */
	public void peek() {
		gameState = 4; // The state of a picked Peek card
	    setText("You have drawn a Peek card, click which card to peek at.");
	}
	
	/**
	 * Swap a card
	 */
	public void swap() {
		gameState = 5; // The state of a picked Swap card
	    
	    setText("You have drawn a Swap card, would you like to use it?");
	    
	    // Need some yes or no buttons to pop up
	    getObject("yes").setVisible(true);
		getObject("no").setVisible(true);
	}
	
	/**
	 *  Use a draw 2 card
	 */
	public void drawTwo() {
		gameState = 8;
		
		setText("You have drawn a draw two card.  Draw your first card.");
		// *WAIT*
		timer = longWait;
		timerId = 2;
	}
	
	public void drawTwo2(){
		picked = deck.drawCardDeck();
		
		changeCardPicture(9, picked.getImage());
		
		if (picked.isPowerCard()) {
			firstDrawPowerCard();
		}
		else {
			firstDrawNormal();
		}
	}
	
	/**
	 * Determine whether the round has ended
	 * @return
	 */
	public boolean isGameOver() {
		if (deck.deckHasCards())
		  return (ratCalled);
		else
			return true;
	}
	
	/**
	 * Handle the end of a round of gameplay
	 */
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
		
		playerTotal += playerOneScore;
		computerTotal += playerTwoScore;
		String resultString = "You: " + playerTotal + ". Opponent: " + computerTotal + ". ";
		
		if(playerOneScore < playerTwoScore){
			resultString += "You won the round. ";
		}else if(playerOneScore > playerTwoScore){
			resultString += "You lost the round. ";
		}else{
			resultString += "This round is a draw. ";
		}
		
		if(playerTotal >= 60 && computerTotal < 60){
			resultString += "You lost the game!";
			gameOver = true;
		}
		if(playerTotal < 60 && computerTotal >= 60){
			resultString += "You won the game!";
			gameOver = true;
		}
		if(playerTotal >= 60 && computerTotal >= 60){
			if(playerTotal > computerTotal){
				resultString += "You lost the game!";
				gameOver = true;
			}
			if(playerTotal < computerTotal){
				resultString += "You won the game!";
				gameOver = true;
			}
			if(playerTotal == computerTotal){
				resultString += "The game is a draw!";
				gameOver = true;
			}
		}
		
		if(!gameOver){
			resultString += "/Would you like to continue playing?";
			
			gameState = 98;
			
			getObject("yes").setVisible(true);
			getObject("no").setVisible(true);
		} else {
			resultString += "/Would you like to play another game?";
			
			gameState = 97;
			
			getObject("yes").setVisible(true);
			getObject("no").setVisible(true);
		}
		
		setText(resultString);
		
		changeCardPicture(1, playerCardOne.getImage());
		changeCardPicture(2, playerCardTwo.getImage());
		changeCardPicture(3, playerCardThree.getImage());
		changeCardPicture(4, playerCardFour.getImage());
		
		changeCardPicture(5, opponentCardOne.getImage());
		changeCardPicture(6, opponentCardTwo.getImage());
		changeCardPicture(7, opponentCardThree.getImage());
		changeCardPicture(8, opponentCardFour.getImage());
		
		if(GameApplet.window != null) GameApplet.window.call("addPlayerScore", new Object[] {playerOneScore});
		if(GameApplet.window != null) GameApplet.window.call("addAiScore", new  Object[] {playerTwoScore});
		if(GameApplet.window != null) GameApplet.window.call("saveGame", null);
	}
	
	/**
	 * Handle the drawing of a power card
	 * @param testCard
	 * @return
	 */
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
	
	/**
	 * Let player know they drew a power card
	 */
	public void handlePowerCard() {
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
	
	/**
	 * Called when player has called rat-a-tat-cat
	 */
	public void ratCatClicked() {
		setText("You have called Rat-a-Tat-Cat!");
		ratCalled = true;
		
		timer = shortWait;
		timerId = 10;
	}
	
	/**
	 * Human drawing from discard pile
	 */
	public void humanDrawDiscard() {
		picked = deck.drawCardDiscard();
		
		changeDiscardPicture(deck.peekDiscard().getImage());
		changeCardPicture(9, picked.getImage());
		
		cardHasBeenPicked();
	}
	
	/**
	 * Human drawing from deck
	 */
	public void humanDrawDeck() {
		picked = deck.drawCardDeck();
		
		changeCardPicture(9, picked.getImage());
		
		cardHasBeenPicked();
	}
	
	/**
	 * Called when a card is drawn
	 */
	public void cardHasBeenPicked() {
		if (picked.isPowerCard()) {
          handlePowerCard();
		} else {
			gameState = 3; // Game State representing when a card is held and waiting to be placed
		    setText("Click where to put your card, or click discard pile to discard.");
		}
		
	}
	
	/**
	 * Called when the deck is clicked
	 */
	public void deckClicked(){
		if(gameState == 1){
			humanDrawDeck();
		}
	}
	
	/**
	 * Called when the discard pile is clicked
	 */
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
	
	/**
	 * Handle end of player's turn
	 */
	public void endPlayerTurn() {
		
		changeCardPicture(9, null);
		
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
	
	/**
	 * Let player know computer's turn is about to begin
	 */
	public void endPlayerTurn2(){
		setText("Computer's turn begins now.");
		
		changeCardPicture(9, Card.backImage);
		
		// *WAIT*
		timer = shortWait;
		timerId = 8;
	}
	
	/**
	 * Called when yes button is clicked
	 */
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
		}// Rat cat was called by player
		else if(gameState == 98){
			timer = 0;
			timerId = 0;
			startGame();
		}// Player wants to play another round
		else if(gameState == 97){
			gameOver = false;
			playerTotal = 0;
			computerTotal = 0;
			timer = 0;
			timerId = 0;
			startGame();
		}// Player wants to play another game
	}
	
	/**
	 * Called when no button is clicked
	 */
	public void clickedNo() {
		
		// Get rid of Yes/No buttons now that they have been clicked
		getObject("yes").setVisible(false);
		getObject("no").setVisible(false);
		
		if (gameState == 5) {
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
		}// player does not call ratcat
		else if(gameState == 98 || gameState == 97){
			Switchboard.setState(StateManager.mainMenuID);
		}// player wants to end the game
	}
	
	public void chooseMyCardToSwap() {
		gameState = 6;
        setText("Click one of your cards to swap.");
	}
	
	public void chooseYourCardToSwap() {
		gameState = 7;
        setText("Choose one of your opponents card to swap with.");
	}
	
	/**
	 * Finish handling a swap power card
	 */
	public void finishSwap() {
        player.getHand().addCard(yourSwap);
        opponent.getHand().addCard(mySwap);
        
        setText("Okay! Swap successful!");
        // *WAIT*
        timer = shortWait;
        timerId = 4;
	}
	
	/**
	 * Called when the player draws a power card
	 */
	public void firstDrawPowerCard() {
		setText("You have drawn a power card!");
		// *WAIT*
		timer = shortWait;
		timerId = 5;
	}
	
	public void firstDrawPowerCard2(){
		if (picked.getRank() == 10) {
			setText("You have drawn another Draw Two card, start again!");
			// *WAIT*
			timer = shortWait;
			timerId = 6;
			return;
		}
		else if (picked.getRank() == 11) {
			gameState = 9;
			setText("You drew a peek card, click yes to use, or no to try your second draw.");
			
			getObject("yes").setVisible(true);
			getObject("no").setVisible(true);
		}
		else {
			gameState = 10;
			
			setText("You drew a swap card, click yes to use, or not to try your second draw.");
			
			getObject("yes").setVisible(true);
			getObject("no").setVisible(true);
		}
	}
	
	public void firstDrawNormal() {
		gameState = 11;
		
		changeCardPicture(9, picked.getImage());
		setText("Select which card to replace, or discard and draw again.");
	}
	
	/**
	 * Handle the second card of a draw two power card
	 */
	public void secondDrawTwo() {
		picked = deck.drawCardDeck();
		
		setText("Starting second draw.");
		changeCardPicture(9, picked.getImage());
		
		cardHasBeenPicked();        
	}
	
	/**
	 * Begin the human's turn
	 */
	public void humanTurn() {
		
		gameState = 1; // State 1 = Start of player's turn
    	
		setText("Your Turn: Choose to draw new card or click discard pile");
	}
	
	/**
	 * Handle the computer's turn
	 */
	public void computerTurn() {
         
        changeCardPicture(9, null);
        
        opponent.beginTurn();
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
		
		String sMessage = "";
		
		if(picked.isPowerCard()){
			if(picked.getRank() == 10){
				sMessage = "The computer is using a draw 2 card.";
			}
			if(picked.getRank() == 11){
				sMessage = "The computer is peeking at your card!";
			}
			if(picked.getRank() == 12){
				sMessage = "The computer is swapping a card!";
			}
		}
		
		setText("Computer's turn is happening. " + sMessage);
        
        // *WAIT*
        timer = shortWait;
        timerId = 7;
	} // Needs some form of AI, even basic for testing purposes
	
	/**
	 * Continue opponent's turn after a wait
	 */
	public void computerTurn2(){
		if(!picked.isPowerCard()){
			//if it is numeric play as usual
			Card discarded = opponent.playCard(picked);
			
			if(!discarded.isPowerCard()){
				deck.discard(discarded);
				changeDiscardPicture(discarded.getImage());
			}
			
			String[] messages = new String[]{"first", "second", "third", "fourth"};
			
			if(picked.getRank() == 11){
				if(opponent.lastSeen < 4){
					setText("The computer peeked at its " + messages[opponent.lastSeen] + " card!");
				}else{
					setText("The computer did not peek at its cards, it knew them all!");
				}
			}
			
			if(picked.getRank() == 12){
				setText("The computer swapped its " + messages[swap[0]] + " card with your " + messages[swap[0]] + " card!");
			}
			
			if(picked.getRank() >= 11){
				// *WAIT*
				timer = shortWait;
				timerId = 11;
				return;
			}
		}else{
			//handle the playing of a power card
			computerPowerCard(picked);
		}

		opponent.incrementTurnCounter();
		
		
		changeCardPicture(9, null);
		
		if (isGameOver()) {
			handleGameOver();
		}
		else {
			ratCalled = opponent.callRatATat();
			humanTurn();
		}
	}
	
	/**
	 * Handle end of opponent's turn
	 */
	public void cpTurnMessage(){
		opponent.incrementTurnCounter();
		
		changeCardPicture(9, null);
		
		if (isGameOver()) {
			handleGameOver();
		}
		else {
			ratCalled = opponent.callRatATat();
			humanTurn();
		}
	}
	
	/**
	 * Handle the computer drawing a power card
	 * @param card
	 */
	public void computerPowerCard(Card card){
		switch(card.getRank()){
			case 10:
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
						if(!newPick.isPowerCard()){
							deck.discard(newPick);
							changeDiscardPicture(newPick.getImage());
						}
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
				if(!discard.isPowerCard()){
					deck.discard(discard);
					changeDiscardPicture(discard.getImage());
				}
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
				swap = opponent.playSwap();
				if(swap[0]!=5){
					Card playerCard = player.getHand().removeCard(swap[1]);
					Card computerCard = opponent.getHand().removeCard(swap[0]);
					player.getHand().addCardByIndex(computerCard, swap[1]);
					opponent.getHand().addCardByIndex(playerCard, swap[0]);  
				}
				break;
		}
	}
	
	/**
	 * Called when a card is clicked
	 * @param index
	 */
	public void cardClicked(int index){
		if(index <= 4){
			humanCardClicked(index);
			return;
		}
		
		computerCardClicked(index - 4);
	}
	
	/**
	 * Called when player clicks one of their own cards
	 * @param index
	 */
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
	
	/**
	 * Called when the player clicked a card from the computer's hand
	 * @param index
	 */
	public void computerCardClicked(int index){ // index is 1-4
		if (gameState == 7) {
			yourSwap = opponent.getHand().removeCard(index);
			finishSwap();
		}
	}
	
	/**
	 * Handle the end of a timer
	 */
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
		
		if(timerId == 11){
			cpTurnMessage();
		}
	}
	
	public void closeState() {}
}