package ClassPractice;

import java.util.Random;

public class Card {
	
	//everything that represents a playing card
	protected String suit;
	protected int faceValue;
	
	//set face cards to permanent values
	protected final int ACE = 1, JACK = 10, QUEEN = 10, KING = 10;
	
	//arrays of faceValues
	int[] values = {ACE,2,3,4,5,6,7,8,9,10,JACK,QUEEN,KING};
	
	//random generator
	Random dealer = new Random();
	
	//array of suits
	String[] suits = {"heart", "club", "spade", "diamond"};
	
	
	//generates a card with a given faceValue and suit
	public Card() {
		
		suit = suits[dealer.nextInt(suits.length)];
		faceValue = values[dealer.nextInt(values.length)];
		
	}

	//sets the suit of a given card
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	//sets the faceValue of a given card
	public void setFaceValue(int value) {
		this.faceValue = value;
	}
	
	//returns the suit of the card
	public String getSuit() {
		return this.suit;
	}
	
	//returns the faceValue of the card
	public int getFaceValue() {
		return this.faceValue;
	}
	
	//string representation of a card
	public String toString() {
		String hand = "";
		hand += "This card: " + this.faceValue + " of " + this.suit;
		return hand;
	}
}
