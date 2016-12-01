package endContainers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JLabel;

import mainPackage.PanelContainer;

public class EndMiddle extends PanelContainer {
	//fixes weid error
	private static final long serialVersionUID = 1L;
	
	private double userBank;
	private JLabel bankLabel;

	NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
	
	public EndMiddle(double userBank) {
		this.userBank = userBank;
		
		initializeOne();
		initializeTwo();
		initializeThree();
	}
	
	public void initializeOne() {
		bankLabel = new JLabel("You have " + moneyFormat.format(userBank) + " in your account");
		bankLabel.setForeground(new Color(255, 251, 153));
		bankLabel.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		one.add(bankLabel);
		one.setPreferredSize(new Dimension(800, 100));
	}
	
	public void initializeTwo() {
		two.setPreferredSize(new Dimension(800, 10));
	}

	public void initializeThree() {
		three.setPreferredSize(new Dimension(800, 10));
	}
}

