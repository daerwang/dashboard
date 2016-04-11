package com.oceanbank.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ChildrenGameTest {
	
	@Test
	public void testGameWinner() throws Exception{
		List<String> children = new ArrayList<String>();
		children.add("1");
		children.add("2");
		children.add("3");
		children.add("4");

		ChildrenGame.startGame(children, 10);
		
		assertEquals("Game Winner", "4", ChildrenGame.getWinner());
	}
	
	@Test
	public void testGameLosers() throws Exception{
		List<String> children = new ArrayList<String>();
		children.add("1");
		children.add("2");
		children.add("3");
		children.add("4");

		ChildrenGame.startGame(children, 10);
		
		List<String> expected = Arrays.asList("2", "3", "1");
		
		assertEquals("Game Losers", expected, ChildrenGame.getLosers());
		
	}
	
	@Test(expected = Exception.class)
	public void testExceptionIsThrownWhenNoChild() throws Exception {
		List<String> children = new ArrayList<String>();
		ChildrenGame.startGame(children, 10);
	}
	
	@Test(expected = Exception.class)
	public void testExceptionIsThrownWhenOneChild() throws Exception {
		List<String> children = new ArrayList<String>();
		children.add("Nell");
		ChildrenGame.startGame(children, 10);
	}
}
