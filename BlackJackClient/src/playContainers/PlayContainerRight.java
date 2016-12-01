package playContainers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JLabel;

import mainPackage.Card;
import mainPackage.GamePlay;
import mainPackage.PanelContainer;

public class PlayContainerRight extends PanelContainer{

	private static final long serialVersionUID = 1L;
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	private ObjectInputStream cardFromServer;
	public JLabel dealerTitle, cardLabel, backCardOneLabel, backCardTwoLabel, dealerCardOne, dealerCardTwo;
	private int dealerHandCount = 0, dealerNumUnusedAces = 0, addedCardCounter = 0;
	Card card = null;
	
	public PlayContainerRight(DataOutputStream toServer, DataInputStream fromServer, ObjectInputStream cardFromServer) {
		this.toServer = toServer;
		this.fromServer = fromServer;
		this.cardFromServer = cardFromServer;
		initializeTopPanel();
		initializeMiddlePanel();
		//initializeBottomPanel();
	}

	public void initializeTopPanel() {
		dealerTitle = new JLabel("Player Three");
		dealerTitle.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		dealerTitle.setForeground(new Color(255, 251, 153));
		one.add(dealerTitle);
		one.setPreferredSize(new Dimension(0, 42));
	}
	
	public void initializeMiddlePanel() {
		displayBackCards();

		two.setPreferredSize(new Dimension(400, 200));
		
		//dealerTitle = new JLabel("Dealer");
		//dealerTitle.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		//dealerTitle.setForeground(new Color(255, 251, 153));
		
		//two.add(dealerTitle);
		//two.setPreferredSize(new Dimension(0, 42));
	}
	
	public void initializeBottomPanel() {
		//displayBackCards();

		//three.setPreferredSize(new Dimension(0, 250));
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
		
		//let server know we need a card
		try {
			toServer.writeBoolean(true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			card = (Card)cardFromServer.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
			
			two.add(backCardOneLabel);
			two.add(backCardTwoLabel);
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
		two.remove(backCardOneLabel);
		two.remove(backCardTwoLabel);
		addCardToLabel(dealerCardOne);
		addCardToLabel(dealerCardTwo);
		two.add(dealerCardOne);
		two.add(dealerCardTwo);
		
		dealerCardOne.setVisible(true);
		dealerCardTwo.setVisible(true);
		
		two.repaint();
		two.revalidate();
	}
	
	
	public void showAnotherCard() {
		addCardToDealerPanel();
		resetDealerLabel();
	}
}
