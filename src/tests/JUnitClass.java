package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JUnitClass {
	@Test
	public void jUnitMethod() {
        System.out.println("This is the testcase in this class");
        String str1="This is the testcase in this class";
        assertEquals("This is the testcase in this class", str1);
	}
	
	public static void main(String[] args) {
		
	}
}
