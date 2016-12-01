package endContainers;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class EndContentPane extends JPanel {
	//fixes weird error
	private static final long serialVersionUID = 1L;
	
	public EndTop panelContainerOne;
	public EndMiddle panelContainerTwo;
	public EndBottom panelContainerThree;
	
	public double userBank, netGainLoss;
	public String username;
	
	public EndContentPane(String username, double userBank, double netGainLoss) {
		this.netGainLoss = netGainLoss;
		this.userBank = userBank;
		this.username = username;
		
		setUpPanels();
	}
	
	public void setUpPanels() {
		panelContainerOne = new EndTop(username, netGainLoss);
		panelContainerTwo = new EndMiddle(userBank);
		panelContainerThree = new EndBottom();
	
		setLayout(new BorderLayout());
	
		add(panelContainerOne, BorderLayout.NORTH);
		add(panelContainerTwo, BorderLayout.CENTER);
		add(panelContainerThree, BorderLayout.SOUTH);
	}
}