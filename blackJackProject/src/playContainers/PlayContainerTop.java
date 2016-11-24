package playContainers;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import mainPackage.PanelContainer;
import mainPackage.GamePlay;
import mainPackage.Card;
import mainPackage.Deck;

public class PlayContainerTop extends PanelContainer{
	//fixes weird error
	private static final long serialVersionUID = 1L;
	
	public JLabel dealerTitle, cardLabel, backCardOneLabel, backCardTwoLabel, dealerCardOne, dealerCardTwo;
	private int dealerHandCount = 0, dealerNumUnusedAces = 0, addedCardCounter = 0;
	
	public PlayContainerTop() {
		initializeTopPanel();
		initializeMiddlePanel();
		initializeBottomPanel();
	}
	
	public void initializeTopPanel() {
		one.setPreferredSize(new Dimension(0, 15));
	}
	
	public void initializeMiddlePanel() {
		dealerTitle = new JLabel("Dealer");
		dealerTitle.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		dealerTitle.setForeground(new Color(255, 251, 153));
		
		two.add(dealerTitle);
		two.setPreferredSize(new Dimension(0, 42));
	}
	
	public void initializeBottomPanel() {
		displayBackCards();

		three.setPreferredSize(new Dimension(0, 250));
	}
	
	public void resetDealerLabel() {
		
		if (addedCardCounter < 2) {
			dealerTitle.setText("Dealer");
		}else if (dealerHandCount > 21) {
			dealerTitle.setText("Dealer -- BUST");
		}else {
			dealerTitle.setText("Dealer -- " + dealerHandCount);
		}
	}
	
	public Card getNextCard() {
		//************change this to accept a Card instead of grabbing from the deck
		//get rid of this method and just add a Card parameter to "addCardToPanel"
		Card card = PlayContentPane.deck.remove(0);
		
		if (card.getCardVal() == 1) {
			++dealerNumUnusedAces;
			dealerHandCount += 11;
		}else {
			dealerHandCount += card.getCardVal();
		}
		++addedCardCounter;
		adjustCount();
		resetDealerLabel();
		
		return card;
	}
	
	public void addCardToDealerPanel () {
		cardLabel = new JLabel();
		cardLabel.setIcon(getNextCard().getImageIcon());
		three.add(cardLabel);
	}
	
	public void addCardToLabel(JLabel cardLabel) {
		cardLabel.setIcon(getNextCard().getImageIcon());
	}
	
	public void displayBackCards () {
		if (backCardOneLabel == null && backCardTwoLabel == null) {
			backCardOneLabel = new JLabel();
			backCardTwoLabel = new JLabel();
			
			backCardOneLabel.setIcon(GamePlay.backCard.getImageIcon());
			backCardTwoLabel.setIcon(GamePlay.backCard.getImageIcon());
			
			three.add(backCardOneLabel);
			three.add(backCardTwoLabel);
		}
		
	}
	
	public int getDealerCount() {
		return dealerHandCount;
	}
	
	public void adjustCount() {
		if (dealerHandCount > 21) {
			while (dealerHandCount > 21 && dealerNumUnusedAces > 0) {
				dealerHandCount -= 10;
				--dealerNumUnusedAces;
			}
		}
	}
	
	public void showFirstCard() {
		dealerCardOne = new JLabel();
		dealerCardTwo = new JLabel();
		three.remove(backCardOneLabel);
		addCardToLabel(dealerCardOne);
		three.add(dealerCardOne);
		
		three.repaint();
		three.revalidate();
	}
	
	public boolean isDealerHitNeeded() {
		if (dealerHandCount < 17) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void showSecondCard() {
		three.removeAll();
		addCardToLabel(dealerCardTwo);
		three.add(dealerCardTwo);
		three.add(dealerCardOne);
		
		three.repaint();
		three.revalidate();
	}
	
	public void showAnotherCard() {
		addCardToDealerPanel();
		resetDealerLabel();
	}
}

