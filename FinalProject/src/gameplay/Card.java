package gameplay;

/**
 * This class represents an individual card
 * @author Adam
 *
 */
public class Card {
	// The card back image
	public static String backImage = "mainGame/cards/cardBack.png";
	
	private int rank;
	
	Card(int iRank){
		rank = iRank;
	}
	
	public int getRank(){
		return rank;
	}
	
	/**
	 * TODO: Complete this class
	 * Note: Need method that gets image given the rank, for example if rank is 1 it
	 * 		 returns "mainGame/cards/1.png"
	 */
}
