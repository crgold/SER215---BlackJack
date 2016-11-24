package ClassPractice;

public class HandTester {
	
	public static void main(String[] args) {
		
		//create a hand object which holds 2 card objects
		Hand myHand = new Hand();
		
		System.out.println(myHand.toString());
		
		myHand.hitMe();
		
		System.out.println();
		
		System.out.println(myHand.toString());
		
	}

	
}
