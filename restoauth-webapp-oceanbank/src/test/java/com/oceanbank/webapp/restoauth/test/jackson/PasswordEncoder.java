/**
 * 
 */
package com.oceanbank.webapp.restoauth.test.jackson;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Programmers
 *
 */
public class PasswordEncoder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new  BCryptPasswordEncoder(11);
		String name = "ocean@123";
		System.out.println(encoder.encode(name));
		System.out.println(encoder.encode(name));
		System.out.println(encoder.encode(name));
		System.out.println(encoder.encode(name));
	}

}
