/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.utils;

import static com.ntak.deepcloner.utils.DeepClonerUtils.getCloneRuleErasureInformation;
import static com.ntak.deepcloner.utils.DeepClonerUtils.getDirectHierarchy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.ntak.deepcloner.rules.collections.ListCloneRule;
import com.ntak.deepcloner.rules.collections.MapCloneRule;
import com.ntak.deepcloner.rules.primitives.IntegerCloneRule;

public class DeepClonerUtilsTest {

	@Test
	public void testSimpleClassExtraction() {
		Class<?> klass = getCloneRuleErasureInformation(IntegerCloneRule.class);
		assertEquals("The extracted class type was not Integer.", Integer.class, klass);
	}

	@Test
	public void testSingleGenericClassExtraction() {
		Class<?> klass = getCloneRuleErasureInformation(ListCloneRule.class);
		assertEquals("The extracted class type was not List.", List.class, klass);
	}

	@Test
	public void testMultipleGenericClassExtraction() {
		Class<?> klass = getCloneRuleErasureInformation(MapCloneRule.class);
		assertEquals("The extracted class type was not List.", Map.class, klass);
	}

	@Test
	public void testNullClassExtraction() {
		Class<?> klass = getCloneRuleErasureInformation(null);
		assertNull("There should not have been a class type generated.", klass);
	}

	@Test
	public void testNonSpecificErasureClassExtraction() {
		Class<?> klass = getCloneRuleErasureInformation(LinkedList.class);
		assertNull("There should not have been a class type generated.", klass);
	}
	
	@Test
	public void testDirectHierarchy() {
		Set<Class<?>> result = getDirectHierarchy(LinkedList.class);
		assertTrue("Result set does not contain the List Class.", result.contains(List.class));
		assertTrue("Result set does not contain the List Class.", result.contains(Deque.class));
		assertTrue("Result set does not contain the List Class.", result.contains(Serializable.class));
		assertTrue("Result set does not contain the List Class.", result.contains(Cloneable.class));
		assertTrue("Result set does not contain the List Class.", result.contains(AbstractSequentialList.class));
	}
}
