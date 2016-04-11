package com.oceanbank.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


//@Ignore
public class UserTest {
	
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
