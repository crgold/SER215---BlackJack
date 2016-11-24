package playContainers;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import mainPackage.Deck;

public class PlayContentPane extends JPanel {
	//fixes weird error
	private static final long serialVersionUID = 1L;
	
	public PlayContainerTop panelContainerOne;
	public PlayContainerMiddle panelContainerTwo;
	public PlayContainerBottom panelContainerThree;
	
	protected static Deck deck;
	
	public double userBank;
	
	public PlayContentPane(String username, double userBank) {
		this.userBank = userBank;
		
		deck = new Deck();
		deck.shuffle();
		
		initializeContentPane();
		
		setUpPanels();
	}
	
	public void initializeContentPane() {
		
	}
	
	public void setUpPanels() {
		panelContainerOne = new PlayContainerTop();
		panelContainerTwo = new PlayContainerMiddle(userBank);
		panelContainerThree = new PlayContainerBottom();
	
		setLayout(new BorderLayout());
	
		add(panelContainerOne, BorderLayout.NORTH);
		add(panelContainerTwo, BorderLayout.CENTER);
		add(panelContainerThree, BorderLayout.SOUTH);
	}
}
