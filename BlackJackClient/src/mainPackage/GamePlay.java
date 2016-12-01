package mainPackage;
import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import endContainers.EndContentPane;
import playContainers.PlayContentPane;
import startContainers.StartContainerMiddle;
import startContainers.StartContentPane;

public class GamePlay {
	
	Socket clientSocket;
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	private ObjectInputStream cardFromServer;
	
	public static Card backCard = new Card("cardBack");
	
	public JFrame frame = new JFrame("Black Jack 21");
	
	public StartContentPane startPane;
	public PlayContentPane newRoundPane;
	public EndContentPane endRoundPane;
	boolean gameInProgress = true, waitingForPlayers = true, playerQuit = false;
	
	public String username;
	public double userBank, lastRoundBet, netGainLoss = 0;
	
	public String winLoseOrTie = "";
	
	public int dealerCount, userCount;
	
	public GamePlay() {
		try {

		      // Create a socket to connect to the server
		      clientSocket = new Socket("localhost", 8000);

		      // Create an input stream to receive data from the server
		      fromServer = new DataInputStream(clientSocket.getInputStream() );

		      // Create an output stream to send data to the server
		      toServer =  new DataOutputStream(clientSocket.getOutputStream() );
		      
		      cardFromServer = new ObjectInputStream(clientSocket.getInputStream());
		    }
		    catch (IOException ex) {
		      ex.printStackTrace();
		    }
		
		startSetUp();
		newRound();
	}
	
	public void startSetUp() {
		startPane = new StartContentPane(toServer, fromServer, clientSocket);
		setContentPane(startPane);
		
		while (!startPane.panelContainerTwo.isReadyToPlay()) {
			wait(100);
		}
		
		username = startPane.panelContainerTwo.getUsername();
		userBank = startPane.panelContainerTwo.getBank();
		
		startPane.panelContainerTwo.setGameInProgress();
	}
	
	public void newRound() {
		//wait for the GameManager to signal it's safe to join the game
		try {
			fromServer.readBoolean();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//this will need to be setup based on the number of players via the gamemanager
		newRoundPane = new PlayContentPane(username, userBank, fromServer, toServer, cardFromServer);
		
		setContentPane(newRoundPane);
		
		//this player is placing their bets
		//while (!newRoundPane.panelContainerTwo.getBetBool()) {
		//wait(100);
		//}
		
		//wait for all player bets. Server will notify when it's ready
		try {
			fromServer.readBoolean();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
		
		//shows dealers first cards
		newRoundPane.panelContainerOne.showFirstCard();
		frame.pack();
		
		//show all players cards and display hit/stay button
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
		
		//this logic needs to transfer to the gamemanager. client will wait for responce.
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
		
		//wait for server to tell us that the round is over
		try {
			while (!fromServer.readBoolean())
				wait(100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//updateFile(); //game manager will update player accounts
		try {
			toServer.writeDouble(userBank);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		newRoundPane.panelContainerTwo.setPlayAgainPane(winLoseOrTie); //does the player want to quit?
		frame.pack();
		
		while (!newRoundPane.panelContainerTwo.isPlayAgainYes() && !newRoundPane.panelContainerTwo.isPlayAgainNo()) {
			wait(20);
		}
		
		//signal this to the gamemanager too by sending playerQuit boolean value to the server
		if (newRoundPane.panelContainerTwo.isPlayAgainYes()) {
			try {
				toServer.writeBoolean(false);
			} catch (IOException e) {
				e.printStackTrace();
			}
			newRound();
		}
		else {
			try {
				toServer.writeBoolean(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	
	public void initializeFrame()
	{
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frame.setUndecorated(false);
		//frame.setPreferredSize(new Dimension(800, 800));
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
