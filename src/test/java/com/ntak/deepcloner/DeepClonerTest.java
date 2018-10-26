package com.ntak.deepcloner;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ntak.deepcloner.exceptions.UnsupportedCloneTypeException;

public class DeepClonerTest {

	private DeepCloner cloner;
	
	@Before
	public void setUp() {
		cloner = new DeepCloner();
	}
	
    @Test(expected=UnsupportedCloneTypeException.class)
    public void testDeepClonerThrowsExceptionWhenUnsupportedTypePassedIn()
    {
    	cloner.deepClone("UNSUPPORTED_TYPE_STRING");
    }
    
    @Test(expected=UnsupportedCloneTypeException.class)
    public void testDeepClonerThrowsExceptionWhenNullPassedIn()
    {
    	cloner.deepClone(null);
    }
    
    // Fail then successful single thread
    
    // Successful multithreaded
    
    // Composite successful
    
}
