package ClassPractice;

import java.util.Scanner;

public class BlackJack {
	
	public static void main(String[] args) throws InterruptedException {
		
		//create the game within this class
		//introduce player to the game and create user variable
		int answer;
		boolean running = true;
		boolean playerTurn = false;
		//User scanner
		Scanner user = new Scanner(System.in);
		
		System.out.println("Hello there! Care for a quick game of black jack? Enter 1 for yes and 0 for no.");
		answer = user.nextInt();
		
		System.out.println();
		
		if (answer == 1)
		{	
			//keep game running in a while loop. If user enters 0 end game.
			while (running) {
			
				System.out.println("Excellent! Let's begin!");
			
				//create player hand and show them their hand
				Hand playerHand = new Hand();
			
				System.out.println("\nHere is your hand: ");
				System.out.println(playerHand.toString());
			
				//create dealer hand and show only the top card
				Hand dealerHand = new Hand();
				System.out.println("\nThe card facing up on the dealer's hand is: ");
				System.out.println(dealerHand.dealerCard());
			
				//ask the player if they would like to hit or stick
				System.out.println("\nHit or stick? 1 for yes and 0 for no. ");
				answer = user.nextInt();
				
				if (answer == 1)
				{
					while (playerTurn) {
						playerHand.hitMe();
				
						System.out.println("\nYour new hand is: ");
						System.out.println(playerHand.toString());
				
						if (playerHand.getTotalValue() > 21) 
						{
							System.out.println("\nOh no! You BUSTED! The dealer hand was: ");
							System.out.println(dealerHand.toString());
					
							System.out.println("\nWould you like to play again? 1 for yes and 0 for no.");
							answer = user.nextInt();
							
							playerTurn = false;
							
							if (answer == 0)
								running = false;
						}
				
						else 
						{
							System.out.println("\nWill you hit again or stick? 1 for yes and 0 for no. ");
							answer = user.nextInt();
						
							//starts players turn again
							if (answer == 0)
								playerTurn = false;
						}
				
					}
				}
				
				System.out.println("\nThe dealer hand has a value of: ");
				System.out.println(dealerHand.getTotalValue());
			
				//continue to give dealer cards until total value is 17 or greater
				while (dealerHand.getTotalValue() <= 17)
				{
					dealerHand.hitMe();
					System.out.println("\nThe dealer hand has a value of: ");
					System.out.println(dealerHand.getTotalValue());
				
					//pause to show dealer hand
					Thread.sleep(2000);
				}
			
				if ((playerHand.getTotalValue() >= dealerHand.getTotalValue() && playerHand.getTotalValue() <= 21) 
						|| dealerHand.getTotalValue() > 21 && playerHand.getTotalValue() <= 21) 
				{
					System.out.println("\nCongrats you win! PLay again? 1 for yes, 0 for no. ");
					answer = user.nextInt();
					
					if (answer == 0)
						running = false;
				}
			
				else 
				{
					System.out.println("\nAhh no, you lost. Play again? 1 for yes, 0 for no. ");
					answer = user.nextInt();
					
					if (answer == 0)
						running = false;
				}
			
			
			}
		}
			//end game
		else
			System.out.println("Thank you for playing!");
		
	}

}
