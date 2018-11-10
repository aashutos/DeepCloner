/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.utils;

import static org.junit.Assert.*;
import static com.ntak.deepcloner.test.TestHelper.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mockito.MockitoAnnotations;

import com.ntak.deepcloner.DeepCloner;
import com.ntak.deepcloner.DeepClonerTest;
import com.ntak.deepcloner.rules.collections.CollectionsCloneRuleTest;
import com.ntak.deepcloner.rules.primitives.PrimitiveCloneRuleTest;
import com.ntak.deepcloner.rules.structures.StructureCloneRuleTest;

/**
 *  @author Aashutos Kakshepati
 */
@RunWith(Suite.class)
@SuiteClasses({ 
				DeepClonerImplsSuite.StandardDeepClonerPrimitiveTest.class,
				DeepClonerImplsSuite.StandardDeepClonerCollectionsTest.class,
				DeepClonerImplsSuite.StandardDeepClonerStructureTest.class,
				DeepClonerImplsSuite.PassStrDeepClonerPrimitiveTest.class,
				DeepClonerImplsSuite.PassStrDeepClonerCollectionsTest.class,
				DeepClonerImplsSuite.PassStrDeepClonerStructureTest.class
})
public class DeepClonerImplsSuite {
	
	///// STANDARD DEEP CLONER TESTS /////
	
	public static class StandardDeepClonerPrimitiveTest extends PrimitiveCloneRuleTest {
		@Before
		@Override
		public void setUp() {
			context = DeepClonerImpls.IMMUTABLE_STANDARD_CLONER;
		}
		
		@Test
		public void cloneString() {			
			String string = "Same String!";
			String cloneString = context.deepClone(string);
			
			isPerfectPrimitiveClone(string, cloneString);
		}
	}

	public static class StandardDeepClonerCollectionsTest extends CollectionsCloneRuleTest {
		@Before
		@Override
		public void setUp() {
			MockitoAnnotations.initMocks(this);
			
			context = DeepClonerImpls.IMMUTABLE_STANDARD_CLONER;
		}
	}
	
	public static class StandardDeepClonerStructureTest extends StructureCloneRuleTest {
		@Before
		@Override
		public void setUp() {
			context = DeepClonerImpls.IMMUTABLE_STANDARD_CLONER;
		}
	}
	
	///// STRING PASS THROUGH DEEP CLONER TESTS /////
	
	public static class PassStrDeepClonerPrimitiveTest extends PrimitiveCloneRuleTest {
		@Before
		@Override
		public void setUp() {
			context = DeepClonerImpls.IMMUTABLE_PASS_STRING_CLONER;
		}
		
		@Test
		public void cloneNonPoolString() {
			String string = "Same String!";
			String cloneString = context.deepClone(string);
			
			assertEquals("The values are not equal.", string, cloneString);
			assertSame("The values are not the same.", string, cloneString);
		}
	}

	public static class PassStrDeepClonerCollectionsTest extends CollectionsCloneRuleTest {
		@Before
		@Override
		public void setUp() {
			MockitoAnnotations.initMocks(this);
			
			context = DeepClonerImpls.IMMUTABLE_PASS_STRING_CLONER;
		}
	}

	public static class PassStrDeepClonerStructureTest extends StructureCloneRuleTest {
		@Before
		@Override
		public void setUp() {
			context = DeepClonerImpls.IMMUTABLE_PASS_STRING_CLONER;
		}
	}
}
