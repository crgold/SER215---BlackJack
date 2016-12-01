package playContainers;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JLabel;

import mainPackage.PanelContainer;

public class PlayContainerMiddle  extends PanelContainer {
	//fixes weird error
	private static final long serialVersionUID = 1L;
	DataInputStream fromServer; 
	DataOutputStream toServer;

	NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
	double betAmount = 0;
	private double playerAmount;
	String spaceBuffer = "         ";
	JLabel betLabel, playerLabel, winLoseLabel, bankLabel, playAgainLabel;
	
	JButton clearButton, add1, add5, add10, add50, betWarning, placeBetButton;
	BetButtonListener betListener = new BetButtonListener();
	
	JButton yesButton, noButton;
	PlayAgainButtonListener playAgainListener = new PlayAgainButtonListener();
	
	private boolean betCompleteBool = false, playAgainYes = false, playAgainNo = false;

	
	
	public PlayContainerMiddle(double bank, DataInputStream fromServer, DataOutputStream toServer) {
		//initialize playerAmount
		playerAmount = bank;
		this.fromServer = fromServer;
		this.toServer = toServer;
		
		initializeTopPanel();
		initializeMiddlePanel();
		initializeBottomPanel();
	}
	
	public void initializeTopPanel() {
		betLabel = new JLabel("Bank:  " + moneyFormat.format(playerAmount) + spaceBuffer + "Bet:  " + moneyFormat.format(betAmount));
		betLabel.setForeground(new Color(255, 251, 153));
		betLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		one.add(betLabel);
		
		one.setPreferredSize(new Dimension(0, 40));
	}
	
	public void initializeMiddlePanel() {
		add1 = new JButton ("Add $1");
		add5 = new JButton ("Add $5");
		add10 = new JButton ("Add $10");
		add50 = new JButton ("Add $50");
		clearButton = new JButton("Clear");
		betWarning = new JButton("You don't have enough money! (Click)");
		
		betWarning.setVisible(false);
		
		add1.addActionListener(betListener);
		add5.addActionListener(betListener);
		add10.addActionListener(betListener);
		add50.addActionListener(betListener);
		clearButton.addActionListener(betListener);
		betWarning.addActionListener(betListener);
		
		two.add(add1);
		two.add(add5);
		two.add(add10);
		two.add(add50);
		two.add(clearButton);
		two.add(betWarning);
		
		two.setPreferredSize(new Dimension(0, 50));
	}
	
	public void initializeBottomPanel() {
		placeBetButton = new JButton("Place Bet!");
		placeBetButton.addActionListener(betListener);
		
		three.add(placeBetButton);
		
		three.setPreferredSize(new Dimension(400, 105)); //was 800, 135
	}
	
	public void resetBetLabel() {
		betLabel.setText("Bank:  " + moneyFormat.format(playerAmount) + spaceBuffer + "Bet:  " + moneyFormat.format(betAmount));
	}
	
	public void displayBetWarning (boolean visible) {
		betWarning.setVisible(visible);
	}
	
	public void enableBetButtons(boolean isEnabled) {
		add1.setEnabled(isEnabled);
		add5.setEnabled(isEnabled);
		add10.setEnabled(isEnabled);
		add50.setEnabled(isEnabled);
		clearButton.setEnabled(isEnabled);
		betWarning.setEnabled(isEnabled);
		placeBetButton.setEnabled(isEnabled);
	}
	
	public void setPlayAgainPane(String winLoseOrTie) {
		one.removeAll();
		one.repaint();
		one.revalidate();
	
		two.removeAll();
		two.repaint();
		two.revalidate();
		
		three.removeAll();
		three.repaint();
		three.revalidate();
		
		if (winLoseOrTie.equals("Push!")) {
			playerAmount += betAmount;
			winLoseLabel = new JLabel(winLoseOrTie);
		}else {
			winLoseLabel = new JLabel(winLoseOrTie + moneyFormat.format(betAmount) + "!");
		}
		winLoseLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		winLoseLabel.setForeground(new Color(255, 251, 153));
		
		if (winLoseOrTie.equals("You won ")) {
			playerAmount += 2* betAmount;
		}
			
		bankLabel = new JLabel("Bank: " + moneyFormat.format(playerAmount));
		bankLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		bankLabel.setForeground(new Color(255, 251, 153));
		
		playAgainLabel = new JLabel("Play Again?");
		playAgainLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		playAgainLabel.setForeground(new Color(255, 251, 153));
		
		yesButton = new JButton("Yes");
		noButton = new JButton("No");
		
		yesButton.addActionListener(playAgainListener);
		noButton.addActionListener(playAgainListener);
		
		one.add(winLoseLabel);
		
		two.add(bankLabel);
		
		three.add(playAgainLabel);
		three.add(yesButton);
		three.add(noButton);
	}
	
	public boolean getBetBool() {
		return betCompleteBool;
	}
	
	public double getBetAmount() {
		return betAmount;
	}
	
	public boolean isPlayAgainYes() {
		return playAgainYes;
	}
	
	public boolean isPlayAgainNo() {
		return playAgainNo;
	}
	
	public class BetButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int betIncrement = 0;
			
			displayBetWarning(false);
			
			if  (e.getSource() == add1) {
				betIncrement = 1;
			}else if (e.getSource() == add5) {
				betIncrement = 5;
			}else if (e.getSource() == add10) {
				betIncrement = 10;
			}else if (e.getSource() == add50) {
				betIncrement = 50;
			}else if (e.getSource() == clearButton) {
				playerAmount += betAmount;
				betAmount = 0;
			}else if (e.getSource() == placeBetButton) {
				enableBetButtons(false);
				try {
					//let the serve know we are done betting
					toServer.writeBoolean(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			betCompleteBool = true;
			
			
			if (betIncrement > playerAmount) {
				displayBetWarning(true);
			}else {
				betAmount += betIncrement;
				playerAmount -= betIncrement;
				}
				resetBetLabel();
			}
		}
	
	public class PlayAgainButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if  (e.getSource() == yesButton) {
				playAgainYes = true;
			}else if (e.getSource() == noButton) {
				playAgainNo = true;
			}
		}
	}
}