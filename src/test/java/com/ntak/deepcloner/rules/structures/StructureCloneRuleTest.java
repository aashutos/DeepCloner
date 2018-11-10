/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.structures;

import static com.ntak.deepcloner.test.TestHelper.isPerfectCollectionClone;
import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.ntak.deepcloner.DeepCloner;
import com.ntak.deepcloner.rules.primitives.StringNonPoolCloneRule;

/**
 *  @author Aashutos Kakshepati
 */
public class StructureCloneRuleTest {

	protected DeepCloner context;
	
	private PropertiesCloneRule propRule = new PropertiesCloneRule();
	
	private StringNonPoolCloneRule strRule = new StringNonPoolCloneRule();
	
	private Properties props = new Properties();
	
	@Before
	public void setUp() throws Exception {
		context = new DeepCloner();
		
		context.addCloneRule(propRule)
			   .addCloneRule(strRule);
	}

	@Test
	public void testPropertiesClone() {
		props.setProperty("KEY_1", "VAL_1");
		props.setProperty("KEY_2", "VAL_2");
		props.setProperty("KEY_3", "VAL_3");
		
		Properties clone = context.deepClone(props);
		
		isPerfectCollectionClone(props.entrySet(), clone.entrySet());
	}
	

	@Test
	public void testPropertiesFailureToClone() {
		context = new DeepCloner();
		
		context.addCloneRule(propRule);
		
		props.setProperty("KEY_1", "VAL_1");
		props.setProperty("KEY_2", "VAL_2");
		props.setProperty("KEY_3", "VAL_3");
		
		Properties clone = context.deepClone(props);
		
		assertEquals("Clone Properties is not empty.", 0, clone.entrySet().size());
	}
	
}
