package mainPackage;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import endContainers.EndContentPane;
import playContainers.PlayContentPane;
import startContainers.StartContainerMiddle;
import startContainers.StartContentPane;

public class GamePlay {
	
	public static Card backCard = new Card("cardBack");
	
	public JFrame frame = new JFrame("Black Jack 21");
	
	public StartContentPane startPane;
	public PlayContentPane newRoundPane;
	public EndContentPane endRoundPane;
	
	public String username;
	public double userBank, lastRoundBet, netGainLoss = 0;
	
	public String winLoseOrTie = "";
	
	public int dealerCount, userCount;
	
	public GamePlay() {
		startSetUp();
		newRound();
	}
	
	public void startSetUp() {
		startPane = new StartContentPane();
		setContentPane(startPane);
		
		while (!startPane.panelContainerTwo.isReadyToPlay()) {
			wait(100);
		}
		
		username = startPane.panelContainerTwo.getUsername();
		userBank = startPane.panelContainerTwo.getBank();
	}
	
	public void newRound() {
		newRoundPane = new PlayContentPane(username, userBank);
		setContentPane(newRoundPane);
		
		while (!newRoundPane.panelContainerTwo.getBetBool()) {
			wait(100);
		}
		
		newRoundPane.panelContainerOne.showFirstCard();
		frame.pack();
		
		newRoundPane.panelContainerThree.startHitting();
		
		while (!newRoundPane.panelContainerThree.getStayBool()) {
			wait(100);
		}
		
		newRoundPane.panelContainerOne.showSecondCard();
		frame.pack();
		
		while (newRoundPane.panelContainerOne.isDealerHitNeeded()) {
			
			wait(1500);
			
			newRoundPane.panelContainerOne.showAnotherCard();
			frame.pack();
		}
		
		lastRoundBet = newRoundPane.panelContainerTwo.getBetAmount();
		
		userCount = newRoundPane.panelContainerThree.getUserCount();
		dealerCount = newRoundPane.panelContainerOne.getDealerCount();
		
		if (userCount > 21 || (dealerCount < 22 && dealerCount > userCount)) {
			winLoseOrTie = "You lost ";
			userBank -= lastRoundBet;
			netGainLoss -= lastRoundBet;
		}else if (userCount == dealerCount) {
			winLoseOrTie = "Push!";
		}else {
			winLoseOrTie = "You won ";
			userBank += lastRoundBet;
			netGainLoss += lastRoundBet;
		}
		updateFile();
		newRoundPane.panelContainerTwo.setPlayAgainPane(winLoseOrTie);
		frame.pack();
		
		while (!newRoundPane.panelContainerTwo.isPlayAgainYes() && !newRoundPane.panelContainerTwo.isPlayAgainNo()) {
			wait(20);
		}
		
		if (newRoundPane.panelContainerTwo.isPlayAgainYes()) {
			newRound();
		}
		else {
			updateFile();

			endRoundPane = new EndContentPane(username, userBank, netGainLoss);
			setContentPane(endRoundPane);
		}
	}
	
	public void updateFile() {
		String tempString;
		double tempDouble;
		boolean usernameNew = true;
		
		File oldFile = new File("textfile.txt");
		File newFile = new File("newFile.txt");
		
		Scanner scan = null;
		PrintWriter writer = null;
		FileWriter rite = null;
		
		try {
			scan = new Scanner(oldFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			writer = new PrintWriter(new FileWriter(newFile));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		while(scan.hasNext()) {
			tempString = scan.next();
			tempDouble = scan.nextDouble();
			
			if (username.equals(tempString)) {
				writer.println(tempString + " " + userBank);
				usernameNew = false;
			}else {
				writer.println(tempString + " " + tempDouble);
			}
		}
		
		if (usernameNew) {
			writer.println(username + " " + userBank);
		}
		
		writer.flush();
		writer.close();
		
		try {
			scan = new Scanner(new File("newFile.txt"));
			rite = new FileWriter("textfile.txt", false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		while (scan.hasNext()) {
			tempString = scan.next();
			tempDouble = scan.nextDouble();
			
			try {
				rite.write(tempString + " " + tempDouble + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			rite.flush();
			rite.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setContentPane(JPanel pane) {
		frame.setContentPane(pane);
		initializeFrame();
	}
	
	public void initializeFrame() {
		frame.setPreferredSize(new Dimension(800, 800));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void wait(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
