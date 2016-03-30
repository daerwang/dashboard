package com.oceanbank.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Ignore
public class UserTest {

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
		while (i < 5) {
			String password = "Tatiana@1";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);

			System.out.println(hashedPassword);
			i++;
		}
	}
	
}
