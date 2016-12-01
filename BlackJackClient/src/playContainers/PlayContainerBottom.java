package playContainers;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mainPackage.Card;
import mainPackage.GamePlay;
import mainPackage.PanelContainer;

import mainPackage.*;

public class PlayContainerBottom extends PanelContainer {
	//fixes weird error
	private static final long serialVersionUID = 1L;
	
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	private ObjectInputStream cardFromServer;
	
	public JLabel tempCardLabel;

	public JLabel backCardOne, backCardTwo;

	public JLabel playerLabel;
	public JButton hit, stay;
	Card card = null;
	int test = 0;
	
	private boolean doneHittingBool = false, isBust = false;

	public moveListener listener = new moveListener();
	
	private int userHandCount = 0, userNumUnusedAces = 0, hitCounter = 0;
	
	public PlayContainerBottom(DataOutputStream toServer, DataInputStream fromServer, ObjectInputStream cardFromServer) {
		this.toServer = toServer;
		this.fromServer = fromServer;
		this.cardFromServer = cardFromServer;
		initializeTopPanel();
		initializeMiddlePanel();
		initializeBottomPanel();
	}

	public void initializeTopPanel() {
		playerLabel = new JLabel("Your Hand");
		playerLabel.setForeground(new Color(255, 251, 153));
		playerLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		one.add(playerLabel);
		
		one.setPreferredSize(new Dimension(800, 40));
	}
	
	public void resetUserLabel() {
		
		if (userHandCount == 0) {
			playerLabel.setText("Your Hand");
		}else if (userHandCount > 21) {
			playerLabel.setText("Your Hand -- BUST");
		}else {
			playerLabel.setText("Your Hand -- " + userHandCount);
		}
	}
	
	public void initializeMiddlePanel() {
		setTwoBackCards(true);
		two.setPreferredSize(new Dimension(800, 140));
	}
	
	public void initializeBottomPanel() {
		hit = new JButton("Hit");
		stay = new JButton("Stay");
		
		hit.addActionListener(listener);
		stay.addActionListener(listener);
		
		three.add(hit);
		three.add(stay);
		
		enableHitStayButtons(false);
		
		three.setPreferredSize(new Dimension(0, 60));
	}
	
	public void startHitting() {
		enableHitStayButtons(true);
		setTwoBackCards(false);
		dealFirstTwoCards();
		resetUserLabel();
	}

	public void doneHitting() {
		enableHitStayButtons(false);
		doneHittingBool = true;
		//let the server know we are done hitting
		try {
			toServer.writeBoolean(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enableHitStayButtons(boolean isEnabled) {
		hit.setEnabled(isEnabled);
		stay.setEnabled(isEnabled);
	}

	public Card getNextCard() {
		//************change this to accept a Card instead of grabbing from the deck
		//get rid of this method and just add a Card parameter to "addCardToPanel"
		
		//let server know we need a card for the dealer
		try {
			toServer.writeBoolean(true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			//this value will serve to see if there are card updates for other players
			//test = fromServer.readInt();
			//if (test != 0)
			//{
				
			//}
			card = (Card)cardFromServer.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (card.getCardVal() == 1) {
			++userNumUnusedAces;
			userHandCount += 11;
		}else {
			userHandCount += card.getCardVal();
		}
		adjustCount();
		
		++hitCounter;
		if (hitCounter == 9) {
			elongatePane();
		}
		
		return card;
	}
	
	public void addCardToPlayerPanel() {
		tempCardLabel = new JLabel();
		tempCardLabel.setIcon(getNextCard().getImageIcon());
		two.add(tempCardLabel);
		
		two.revalidate();
		two.repaint();
	}
	
	public void setTwoBackCards(boolean isVisible) {
		if (backCardOne == null && backCardTwo == null) {
			backCardOne = new JLabel();
			backCardTwo = new JLabel();
			
			backCardOne.setIcon(GamePlay.backCard.getImageIcon());
			backCardTwo.setIcon(GamePlay.backCard.getImageIcon());
			
			two.add(backCardOne);
			two.add(backCardTwo);
		}
		
		backCardOne.setVisible(isVisible);
		backCardTwo.setVisible(isVisible);
		
		System.out.println("Is used");
		
	}
	
	public void dealFirstTwoCards() {
		addCardToPlayerPanel();
		addCardToPlayerPanel();
	}
	
	public void displayTwoBackCards(boolean isDisplay) {
		backCardOne.setVisible(isDisplay);
		backCardTwo.setVisible(isDisplay);
	}
	
	public void adjustCount() {
		if (userHandCount > 21) {
			while (userHandCount > 21 && userNumUnusedAces > 0) {
				userHandCount -= 10;
				--userNumUnusedAces;
			}
		}
	}
	
	public void elongatePane() {
		two.setPreferredSize(new Dimension(800, 260));
	}
	
	
	public boolean getStayBool() {
		return doneHittingBool;
	}
	public boolean getBustBool() {
		return isBust;
	}
	
	public class moveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == hit) {
				addCardToPlayerPanel();
				resetUserLabel();
				if (userHandCount > 21) {
					isBust = true;
					doneHitting();
				}
			}else if (e.getSource() == stay) {
				doneHitting();
			}
		}
	}
	
	public int getUserCount() {
		return userHandCount;
	}
	
}
