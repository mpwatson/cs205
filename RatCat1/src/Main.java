import java.util.Scanner;


public class Main {

	/**
	 * @param args
	 */
    private static Scanner scanner;
    private static Player player;
    private static Player opponent;
    private static Deck deck;
    private static boolean ratCalled;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    player = new Player();
	    opponent = new Player();
	    deck = new Deck();
	    scanner = new Scanner(System.in);
	    ratCalled = false;
		
	    initialize();
		
	    System.out.println("Here are the cards in the First and Last position of your hand");
	    System.out.println("FIRST: " + player.getHand().getCard(0).getRank());
	    System.out.println("LAST: " + player.getHand().getCard(0).getRank());
	    
	    while (!(isGameOver())) {
	        humanTurn();
	        computerTurn();
	    }
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
			player.addCard(deck.drawCardDeck());
			opponent.addCard(deck.drawCardDeck());
		}
		
		Card topCard = deck.drawCardDeck();
		while (topCard.isPowerCard()) {
		    // deck.putCardInMiddle(topCard)
			topCard = deck.drawCardDeck();
		}
		deck.discard(topCard);
	}
	
	public static void humanTurn() {
		Card picked;
		Card left;
		System.out.println("Top of Discard Pile: " + deck.peekDiscard());
		System.out.println("Take from Discard Pile (1) or Draw (2)");
		String input = scanner.nextLine();
		int inputInt = Integer.parseInt(input);
		if (inputInt == 1) 
			picked = deck.drawCardDiscard();
		else 
			picked = deck.drawCardDeck();
		if (picked.isPowerCard()) {
			System.out.println("YOU PICKED POWER CARD!");
		}
		else {
		  System.out.println("PICK WHERE TO PUT " + picked.getRank());
		  String inputTwo = scanner.nextLine();
		  int inputTwoInt = Integer.parseInt(inputTwo);
		  left = player.getHand().swapCard(inputTwoInt, picked);
		  deck.discard(left);
		}
	}
	
	public static void computerTurn() {
		
	} // Needs some form of AI, even basic for testing purposes
	
	public static void peek() {
	
	}
	
	public static void swap() {
		
	}
	
	public static void drawTwo() {
		
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
		Card opponentCardOne = resolvePowerCard(player.getHand().getCard(0));
		Card opponentCardTwo = resolvePowerCard(player.getHand().getCard(1));
		Card opponentCardThree = resolvePowerCard(player.getHand().getCard(2));
		Card opponentCardFour = resolvePowerCard(player.getHand().getCard(3));
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
	
	

}
