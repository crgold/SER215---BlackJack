package mainPackage;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Card {
	
	//variables for card
	private String cardFace, cardSuit, name;
	private int cardValue;
	final double CARD_RATIO = 5.00 / 7.00;
	int cardSize = 120;
	
	public Card(String face, String suit, int val) {
		cardFace = face;
		cardSuit = suit;
		name = face + "_of_" + suit;
		if (val < 10) {
			cardValue = val;
		}
		else {
			cardValue = 10;
		}
	}
	
	//for back card
	public Card(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public ImageIcon getImageIcon() {
		
		ImageIcon imageIcon = new ImageIcon("Images/" + name + ".png");
		Image rawImage = imageIcon.getImage();
		Image scaledImage = rawImage.getScaledInstance((int)(cardSize * CARD_RATIO), cardSize, java.awt.Image.SCALE_SMOOTH);
		imageIcon.setImage(scaledImage);
		
		return imageIcon;
	}
	
	public int getCardVal() {
		return cardValue;
	}
}
