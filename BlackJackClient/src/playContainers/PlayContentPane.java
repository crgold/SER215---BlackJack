package playContainers;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;

import javax.swing.JPanel;

public class PlayContentPane extends JPanel {
	//fixes weird error
	private static final long serialVersionUID = 1L;
	
	public PlayContainerTop panelContainerOne;
	public PlayContainerMiddle panelContainerTwo;
	public PlayContainerBottom panelContainerThree;
	public PlayContainerLeft panelContainerLeft;
	public PlayContainerRight panelContainerRight;
	DataInputStream fromServer; 
	DataOutputStream toServer;
	ObjectInputStream cardFromServer;
	
	public double userBank;
	
	public PlayContentPane(String username, double userBank, DataInputStream fromServer, DataOutputStream toServer, ObjectInputStream cardFromServer) {
		this.userBank = userBank;
		this.fromServer = fromServer;
		this.toServer = toServer;
		this.cardFromServer = cardFromServer;
		
		initializeContentPane();
		
		setUpPanels();
	}
	
	public void initializeContentPane() {
		
	}
	
	public void setUpPanels() {
		panelContainerOne = new PlayContainerTop(toServer, fromServer, cardFromServer);
		panelContainerTwo = new PlayContainerMiddle(userBank, fromServer, toServer);
		panelContainerLeft = new PlayContainerLeft(toServer, fromServer, cardFromServer);
		//panelContainerRight = new PlayContainerRight(toServer, fromServer, cardFromServer);
		panelContainerThree = new PlayContainerBottom(toServer, fromServer, cardFromServer);
	
		setLayout(new BorderLayout());
	
		add(panelContainerOne, BorderLayout.NORTH);
		add(panelContainerTwo, BorderLayout.CENTER);
		add(panelContainerThree, BorderLayout.SOUTH);
		//add(panelContainerLeft, BorderLayout.WEST);
		//add(panelContainerRight, BorderLayout.EAST);
	}
}
