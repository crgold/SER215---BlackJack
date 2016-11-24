package ClassPractice;

import java.util.Random;


public class Hand{
	
	//variables of a hand
	private Card[] hand;
	private int numOfCards;
	private int totalValue = 0;
	
	//random generator to create hand
	Random dealer = new Random();
	
	
	//creates an initial array of cards
	public Hand() {
		
		hand = new Card[2];
		//fill hand with card objects
		for (int index = 0; index < hand.length; index++) {
			hand[index] = new Card();
			totalValue += hand[index].getFaceValue();
		}
		
		numOfCards = 2;
		
		
	}
	
	//hit me method adds a card to your hand
	public void hitMe() {
		//increase the number of cards by 1 every time the method is called
		numOfCards++;
		totalValue = 0;
		
		Card[] temp = new Card[numOfCards];
		
		for(int index = 0; index < hand.length; index++) {
			temp[index] = hand[index];
		}
		
		temp[numOfCards - 1] = new Card();
		
		hand = temp;
		
		for(int index = 0; index < hand.length; index++) {
			totalValue += hand[index].getFaceValue();
		}
		
	}
	
	//show top card of dealer hand
	public String dealerCard() {
		
		String g = hand[1].toString();
		return g;
	}

	//returns the number of cards in a players hand
	public int getNumOfCards() {
		return numOfCards;
	}

	//sets the number of cards in a players hand
	public void setNumOfCards(int numOfCards) {
		this.numOfCards = numOfCards;
	}

	//gets the total value of the cards within a players hand
	public int getTotalValue() {
		return totalValue;
	}

	//sets the total value of the cards within a players hand
	public void setTotalValue(int totalValue) {
		this.totalValue = totalValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * creates a string representation of a hand object
	 */
	@Override
	public String toString() {
		String g = "";
		
		for(int index = 0; index < hand.length; index++) {
			g += hand[index].toString() + "\n";
		}
		
		g += "The total value of your hand is: " + totalValue;
		
		return g;
	}
	
	//creates a string representation of a hand object
	

	
}
