package endContainers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;

import javax.swing.JLabel;

import mainPackage.PanelContainer;

public class EndTop extends PanelContainer {
	//fixes weid error
	private static final long serialVersionUID = 1L;
	
	private JLabel thanksLabel, netWinningsLabel;
	private String username;
	private double netWinnings;

	NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
	
	public EndTop(String username, double netWinnings) {
		this.username = username;
		this.netWinnings = netWinnings;
		
		initializeOne();
		initializeTwo();
		initializeThree();
	}
	
	public void initializeOne() {
		one.setPreferredSize(new Dimension(800, 150));
	}
	
	public void initializeTwo() {
		thanksLabel = new JLabel("Thanks for playing " + username + "!");
		thanksLabel.setForeground(new Color(255, 251, 153));
		thanksLabel.setFont(new Font("Times New Roman", Font.PLAIN, 48));
		two.add(thanksLabel);
		two.setPreferredSize(new Dimension(800, 150));
	}

	public void initializeThree() {
		netWinningsLabel = new JLabel("Your net earnings are $" + netWinnings + "0");
		netWinningsLabel.setForeground(new Color(255, 251, 153));
		netWinningsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		three.add(netWinningsLabel);
		
		three.setPreferredSize(new Dimension(800, 150));
	}
}
