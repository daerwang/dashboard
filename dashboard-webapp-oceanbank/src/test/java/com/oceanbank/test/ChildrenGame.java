package com.oceanbank.test;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class ChildrenGame.
 * 
 * @author Marinell Medina
 * @since 04.08.2016
 */
public class ChildrenGame {
	
	/** The winner of the game. */
	private static String winner;
	
	/** The losers of the game. */
	private static List<String> losers = new ArrayList<String>();
	
	/**
	 * The start game which initializes the class.
	 * 
	 * @param children the list that will be part of the game.
	 * @param number the number which will count out a child when assigned
	 * @throws Exception the exception when no or 1 child is passed
	 */
	public static void startGame(List<String> children, Integer number) throws Exception{
		
		// validate if no or 1 child is passed
		if(children == null){
			throw new Exception("The list of children is empty.");
		}
		if(children != null && children.size() == 1){
			throw new Exception("The children needs to be more than one.");
		}

		// initialize variables
		int listSize = children.size();
		boolean turn = true;
		int child = 0;
		
		// iterate around the children and stop when only 1 child is left
		while(turn){
			
			// assign the number to each child starting from first child
			for(int i = 0; i < number; i++){
				int count = i + 1;
				boolean nextChild = true;
				
				String value = children.get(child);
				
				// when the number is reached, remove the child in the list and add to losers list
				if(count == number){
					children.remove(child);
					losers.add(value);
					listSize = children.size();
					nextChild = false;
					
					// when only 1 child remains, then stop iteration and declare as winner
					if(listSize == 1){
						setWinner(children.get(0));
						turn = false;
						break;
					}
				}
				
				// determine the index of child who takes the next turn
				if(nextChild){
					if(child < listSize - 1){
						child++;
					}else{
						child = 0;
					}
				}
				
				
			}
		} 
	}

	public static String getWinner() {
		return winner;
	}

	public static void setWinner(String winner) {
		ChildrenGame.winner = winner;
	}

	public static List<String> getLosers() {
		return losers;
	}

	public static void setLosers(List<String> losers) {
		ChildrenGame.losers = losers;
	}

}
