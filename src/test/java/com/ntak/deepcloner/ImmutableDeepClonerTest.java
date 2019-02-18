/*
 *  (c) copyright 2019 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.ntak.deepcloner.rules.primitives.IntegerCloneRule;

import static com.ntak.deepcloner.test.TestHelper.isPerfectPrimitiveClone;;

/**
 *  @author Aashutos Kakshepati
 */
public class ImmutableDeepClonerTest {

	private static final Map<String, List<String>> EMPTY_MAP = Collections.emptyMap();
	private static final List<String> EMPTY_LIST = Collections.emptyList();
	public DeepCloner context;
	
	@Before
	public void setUp() {
		context = (new DeepCloner())
					.addFactoryCloneRule(Integer.class, EMPTY_LIST)
					.genImmutableDeepCloner();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testCannotAddCloneRule() {
		context.addCloneRule(new IntegerCloneRule());
	}	

	@SuppressWarnings("unchecked")
	@Test(expected=UnsupportedOperationException.class)
	public void testCannotAddCloneRuleByFactory() {
		context.addFactoryCloneRule(Character.class, EMPTY_LIST);
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testCannotAddCloneRulesByFactory() {
		context.addFactoryCloneRule(Arrays.<Class<?>>asList(Character.class, Double.class), EMPTY_MAP);
	}
	
	@Test
	public void testCloneInteger() {
		Integer input = 5;
		Integer output = context.deepClone(input);
		isPerfectPrimitiveClone(input, output);
	}
}
