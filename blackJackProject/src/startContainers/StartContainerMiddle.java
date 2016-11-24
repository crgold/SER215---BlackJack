package startContainers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import mainPackage.PanelContainer;

public class StartContainerMiddle extends PanelContainer {
	//fixes weird error
	private static final long serialVersionUID = 1L;
	
	private JLabel newUserQuestionLabel, enterUsernameLabel, usernameWarningLabel;
	private JButton yesButton, noButton, backButton, playButton;
	private JTextField usernameTextField;
	private ButtonListener buttonListener;
	private Component spaceBufferOne, spaceBufferTwo, spaceBufferThree;
	
	private boolean newUserBool, found = false;
	private final double STARTBANK = 1000.00;
	
	private String usernameEntered = "";
	private double userBank = 0;
	private boolean playBool = false;
	
	private File file;
	private Scanner input;	

	public StartContainerMiddle() {
		initializeButtonsAndLabels();
		initializePreferredSizes(35, 35, 35);
		setFirstPane();
	}

	public void initializePreferredSizes(int oneHeight, int twoHeight, int threeHeight) {
		one.setPreferredSize(new Dimension(0, oneHeight));
		two.setPreferredSize(new Dimension(0, twoHeight));
		three.setPreferredSize(new Dimension(0, threeHeight));
	}
	
	public void initializeButtonsAndLabels() {
		newUserQuestionLabel = new JLabel("Are you an existing user?");
		newUserQuestionLabel.setForeground(new Color(255, 251, 153));
		newUserQuestionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		enterUsernameLabel = new JLabel("Enter userename:");
		enterUsernameLabel.setForeground(new Color(255, 251, 153));
		enterUsernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		usernameWarningLabel = new JLabel("");
		usernameWarningLabel.setForeground(new Color(255, 251, 153));
		usernameWarningLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		spaceBufferOne = Box.createRigidArea(new Dimension(20, 0));		
		spaceBufferTwo = Box.createRigidArea(new Dimension(20, 0));		
		spaceBufferThree = Box.createRigidArea(new Dimension(20, 0));
		
		buttonListener = new ButtonListener();
		
		yesButton = new JButton("Yes");
		yesButton.addActionListener(buttonListener);
		noButton = new JButton("No");
		noButton.addActionListener(buttonListener);
		backButton = new JButton("Back");
		backButton.addActionListener(buttonListener);
		playButton = new JButton("Play");
		playButton.addActionListener(buttonListener);
		
		
		usernameTextField = new JTextField();
		usernameTextField.setPreferredSize(new Dimension(120, 24));
		//automatically selects the text box to type in
		usernameTextField.enableInputMethods(true);
		
		one.add(newUserQuestionLabel);
	
		two.add(yesButton);
		two.add(spaceBufferOne);
		two.add(noButton);
		
		one.add(enterUsernameLabel);
		one.add(spaceBufferTwo);
		one.add(usernameTextField);
		
		two.add(usernameWarningLabel);
		
		three.add(playButton);
		three.add(spaceBufferThree);
		three.add(backButton);
	}
	
	public void setFirstPane() {
		newUserQuestionLabel.setVisible(true);
		yesButton.setVisible(true);
		spaceBufferOne.setVisible(true);
		noButton.setVisible(true);
		
		enterUsernameLabel.setVisible(false);
		spaceBufferTwo.setVisible(false);
		usernameTextField.setVisible(false);
		usernameWarningLabel.setVisible(false);
		playButton.setVisible(false);
		spaceBufferThree.setVisible(false);
		backButton.setVisible(false);
	}
	
	public void setExistingUserPane() {
		newUserBool = false;
		//reset text field to empty
		usernameTextField.setText("");
		//resets warning label
		usernameWarningLabel.setText("");
		
		newUserQuestionLabel.setVisible(false);
		yesButton.setVisible(false);
		spaceBufferOne.setVisible(false);
		noButton.setVisible(false);
		
		enterUsernameLabel.setVisible(true);
		spaceBufferTwo.setVisible(true);
		usernameTextField.setVisible(true);
		usernameWarningLabel.setVisible(true);
		playButton.setVisible(true);
		spaceBufferThree.setVisible(true);
		backButton.setVisible(true);
	}
	
	public void setNewUserPane() {
		newUserBool = true;
		//sets text field to empty
		usernameTextField.setText("");
		//resets warning label
		usernameWarningLabel.setText("");
		
		newUserQuestionLabel.setVisible(false);
		yesButton.setVisible(false);
		spaceBufferOne.setVisible(false);
		noButton.setVisible(false);
		
		enterUsernameLabel.setVisible(true);
		spaceBufferTwo.setVisible(true);
		usernameTextField.setVisible(true);
		usernameWarningLabel.setVisible(true);
		playButton.setVisible(true);
		spaceBufferThree.setVisible(true);
		backButton.setVisible(true);
	}
	
	public void userNameEntered(boolean isFound) {
		//new user
		if (newUserBool) {
			if (isFound) {
				usernameWarningLabel.setText(usernameEntered + " is already taken!");
			}
			else {
				userBank = STARTBANK;
				
				playBool = true;
				
				System.out.println("Username:" + usernameEntered + "" + userBank);
			}
		}
		//existing user
		else {
			if (isFound) {
				userBank = input.nextDouble();
				
				playBool = true;
								
				System.out.println("Username:" + usernameEntered + "" + userBank);
			}
			else {
				usernameWarningLabel.setText(usernameEntered + " was not found!");
			}
		}
	}
	
	public boolean isUsernameFound() {
		String usernameRead;
		found = false;

		file = new File("textfile.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		
		do {
			usernameRead = input.next();
			if (usernameRead.equals(usernameEntered)) {
				found = true;
			}
		} while (!found && input.hasNext());
		return found;
	}
	
	public String getUsername() {
		return usernameEntered;
	}
	
	public double getBank() {
		return userBank;
	}
	
	public boolean isReadyToPlay () {
		return playBool;
	}
	
	public class ButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(noButton)) {
				setNewUserPane();
			}
			else if (e.getSource().equals(yesButton)) {
				setExistingUserPane();
			}
			else if (e.getSource().equals(backButton)) {
				setFirstPane();
			}
			else if (e.getSource().equals(playButton)) {
				usernameEntered = usernameTextField.getText();
				userNameEntered(isUsernameFound());
			}
			else {
				System.out.println("that didnt exactly work");
				setNewUserPane();
			}
		}
	}
}