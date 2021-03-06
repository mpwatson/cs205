package gameplay;

/**
 * This class represents an individual card
 * @author Adam
 *
 */
public class Card {	
	// The card back image
	public static String backImage = "gameMain/cards/back.png";
	
	private int rank;
	
	Card(int iRank){
		rank = iRank;
	}
	
	public int getRank(){
		return rank;
	}
	
	// the image of a face-up card
	public String getImage(){
		return "gameMain/cards/" + Integer.toString(rank) + ".png";
	}
	
	/**
	 * Determine if the card is a power card
	 * @return
	 */
	public boolean isPowerCard(){
		return ((rank == 10)       // DRAW 2
				|| (rank == 11)    // PEEK
				|| (rank == 12));  // SWAP
	}
	
	public String toString(){
		String returnString = new String();
		switch(rank){
			case 10:
				returnString = "Draw 2";
				break;
			
			case 11:
				returnString = "Peek";
				break;
			
			case 12:
				returnString = "Swap";
				break;
			
			default:
				returnString = Integer.toString(rank);
		}
		
		return returnString;
	}
}
