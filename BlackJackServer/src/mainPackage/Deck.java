package mainPackage;
import java.util.ArrayList;
import java.util.Random;

public class Deck extends ArrayList<Card> {

	private static final long serialVersionUID = 1L;

	private int numDecks = 1;
	
	private final String[] faces = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
	private final String[] suits = {"clubs", "diamonds", "hearts", "spades"};	
	
	public Deck() {
		createDeck();
	}
	
	public Deck(int numDecks) {
		this.numDecks = numDecks;
		createDeck();
	}
	
	public void createDeck(){
		int val = 1;
		
		for (String cardFace : faces) {
			for (String cardSuit : suits) {
				this.add(new Card(cardFace, cardSuit, val));	
			}
			++val;
		}
	}
	
	public void shuffle() {
		Random generator = new Random(System.currentTimeMillis());
		int randNum, swapIndex;
		Card tempCard;
		
		for (int index = 0; index < this.size(); ++index) {
			// Generates a number between 0 and 50
			randNum = generator.nextInt(51);
			// Sets the swapIndex to a random number that isn't equal to the 
			// original index.
			swapIndex = (index + randNum) % 51;
			// Removes the indexed card from the array and assigns it to tempCard.
			tempCard = this.remove(index);
			// adds the card back into the deck at the random swapIndex
			this.add(swapIndex, tempCard);
		}
	}
}
