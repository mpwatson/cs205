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
	
	// the image of a face-up card
	public static String getImage(int iRank){
		return "gameMain/cards/" + Integer.toString(iRank) + ".png";
	}
	
	public boolean isPowerCard(){
		return ((rank == 10) || (rank == 11) || (rank == 12));
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
