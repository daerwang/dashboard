package com.oceanbank.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Ignore
public class UserTest {
	
	@Test
	public void test_exam4(){
		//System.out.println(Math.min(a, b));
		
		int array[]= {0, 1, 3, -2, 0, 1, 0, -3, 2, 3};
	    
	    int length = array.length;
		int currentDepth = 0;
		int maxDepth = -1; 

		int first, second, third; 
		int i; // index for first
		int j; // index for second
		int k; // index for third
		
		// find the triplet pattern by iterating the array
		for (i=0; i<length-2; i++) {
		    
			j = i + 1;

		    if (array[i] > array[j]) {
		        
		        first = array[i]; // set the P
		        
		        // find the index for j
		        while (j + 1 < length && array[j] > array[j + 1]) {
		            j++;
		        }
		        second = array[j]; // set the Q

		        k = j+1;
		        
		        // find the index for k
		        while (k + 1 < length && array[k] < array[k + 1]) {
		            k++;
		        }
		        
		        // stop iteration
		        if (k >= length) {
		            break;
		        }
		        third = array[k]; // set the R

		        currentDepth = (int)Math.min(first - second, third - second);
		        
		        if (currentDepth > maxDepth) {
		            maxDepth = currentDepth;
		        }
		        
		        i = k-1; // go to next P
		    }
		}
		
		System.out.println("it is " + maxDepth);
	}

	@Test
	public void test_exam2(){
		Integer[] array = {-1, 3, -4, 5, 1, -6, 2, 1};
		int eqIndex = 1;
		final int size = array.length;
		boolean isTurn = true;
		
		while(isTurn){
			int totalLeft = 0;
			int totalRight = 0;
			
			for(int i = 0; i < size; i++){
				if(i < eqIndex){
					totalLeft = totalLeft + array[i];
				}
				if(i > eqIndex){
					totalRight = totalRight + array[i];
				}
			}
			
			if(totalLeft == totalRight){
				System.out.println("Answer is index " + eqIndex);
				isTurn = false;
			}
			if(eqIndex == size - 1){
				isTurn = false;
			}
			eqIndex++;
		}
		
	}
	
	@Test
	public void test_exam() throws Exception{
		
		List<String> children = new ArrayList<String>();
		children.add("Nell");
		children.add("Jerry");
		children.add("William");
		children.add("Malone");


		ChildrenGame.startGame(children, 10);
		System.out.println("Losers are " + ChildrenGame.getLosers());
		System.out.println("Winner is " + ChildrenGame.getWinner());
		
	}
	
	@Test
	public void test_content() throws JsonProcessingException, IOException{
		String msg = "{\"message\":\"User Account is locked!\",\"cause\":\"class org.springframework.security.authentication.LockedException\",\"name\":\"org.springframework.security.authentication.LockedException\"}";
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode mainNode = mapper.readTree(msg);
		if(mainNode.has("name")){
			String name = mainNode.path("name").asText();
			String[] arr = name.split("\\.");
			name = arr[arr.length - 1];
			if(name.trim().contains("LockedExceptiosn")){
				System.out.println("correct!");
			}
		}
		
		
	}

	@Test
	public void test_regex(){
		String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{7,20})";
		
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher("Tatiana@1");
		Boolean isCorrect =  matcher.matches();
		
		System.out.println("it is - " + isCorrect);
	}
	
	@Test
	public void test_encoder(){
		int i = 0;
		String password = "Tatiana@1";
		while (i < 5) {
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
			// $2a$10$b7BcoO1motLDQxKiuEIvzekO4ATgTnuFXPZEvmKZd2tFEiElhozFS
			
			System.out.println(hashedPassword);
			i++;
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Boolean isCorrect = passwordEncoder.matches("Tatiana@2", "$2a$10$b7BcoO1motLDQxKiuEIvzekO4ATgTnuFXPZEvmKZd2tFEiElhozFS");
		
		System.out.println("final is " + isCorrect);
	}
	
}
