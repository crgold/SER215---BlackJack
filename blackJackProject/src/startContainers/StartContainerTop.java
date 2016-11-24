package startContainers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import mainPackage.PanelContainer;

public class StartContainerTop extends PanelContainer{
	//fixes weird error
	private static final long serialVersionUID = 1L;
	
	public JLabel titleLabelOne, titleLabelTwo, buttonPrompt;
	
	public StartContainerTop() {
		initializeLabelsAndButtons();
		initializeTopPanel();
		initializeMiddlePanel();
		initializeBottomPanel();
	}
	
	public void initializeTopPanel() {
		one.setPreferredSize(new Dimension(800, 50));
	}
	
	public void initializeMiddlePanel() {
		two.add(titleLabelOne);
		two.setPreferredSize(new Dimension(0, 50));
	}
	
	public void initializeBottomPanel() {
		three.setPreferredSize(new Dimension(0, 200));
		three.add(titleLabelTwo);
	}
	
	public void initializeLabelsAndButtons() {

		titleLabelOne = new JLabel("Welcome to");
		titleLabelOne.setForeground(new Color(255, 51, 153));
		titleLabelOne.setFont(new Font("Times New Roman", Font.BOLD, 40));
		
		titleLabelTwo = new JLabel("Black Jack 21!");
		titleLabelTwo.setForeground(new Color(51, 153, 255));
		titleLabelTwo.setFont(new Font("Times New Roman", Font.BOLD, 55));
	}
}


