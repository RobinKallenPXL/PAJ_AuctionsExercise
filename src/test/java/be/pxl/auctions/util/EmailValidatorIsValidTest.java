package be.pxl.auctions.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmailValidatorIsValidTest {

	@Test
	public void returnsTrueWhenValidEmail() {
		// TODO implement test
		assertTrue(EmailValidator.isValid("robin.kallen@student.pxl.be"));
	}

	@Test
	public void returnsFalseWhenAtSignMissing() {
		// TODO implement test
		assertFalse(EmailValidator.isValid("lol not an email"));
		/*
		assertFalse(EmailValidator.isValid("Lol not @ctually an email.com"));
		assertFalse(EmailValidator.isValid("not@an@email.com")); 
		*/
	}

}
