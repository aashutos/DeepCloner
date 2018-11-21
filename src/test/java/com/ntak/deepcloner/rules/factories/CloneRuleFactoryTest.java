/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.factories;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.ntak.deepcloner.CloneRule;
import com.ntak.deepcloner.DeepCloner;
import com.ntak.deepcloner.factories.AmalgamatedCloneRuleFactory;
import com.ntak.deepcloner.factories.CloneRuleFactory;
import com.ntak.deepcloner.factories.StandardCloneRuleFactory;
import com.ntak.deepcloner.rules.collections.ListCloneRule;
import com.ntak.deepcloner.rules.collections.MapCloneRule;
import com.ntak.deepcloner.rules.collections.QueueCloneRule;
import com.ntak.deepcloner.rules.collections.SetCloneRule;
import com.ntak.deepcloner.rules.primitives.BigDecimalCloneRule;
import com.ntak.deepcloner.rules.primitives.BigIntegerCloneRule;
import com.ntak.deepcloner.rules.primitives.BooleanCloneRule;
import com.ntak.deepcloner.rules.primitives.ByteCloneRule;
import com.ntak.deepcloner.rules.primitives.CharacterCloneRule;
import com.ntak.deepcloner.rules.primitives.DoubleCloneRule;
import com.ntak.deepcloner.rules.primitives.FloatCloneRule;
import com.ntak.deepcloner.rules.primitives.IntegerCloneRule;
import com.ntak.deepcloner.rules.primitives.LongCloneRule;
import com.ntak.deepcloner.rules.primitives.ShortCloneRule;
import com.ntak.deepcloner.rules.primitives.StringNonPoolCloneRule;
import com.ntak.deepcloner.rules.primitives.StringPoolCloneRule;
import com.ntak.deepcloner.rules.structures.PropertiesCloneRule;

/**
 *  @author Aashutos Kakshepati
 */
public class CloneRuleFactoryTest {

	private CloneRuleFactory amgFactory;
	private DeepCloner amgCloner;
	
	private CloneRuleFactory standardFactory;
	private DeepCloner standardCloner;
	
	@Mock(name="intFactory")
	private CloneRuleFactory mockFactory;	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		doAnswer(new Answer<CloneRule<?>>() {

			@Override
			public CloneRule<?> answer(InvocationOnMock invocation) throws Throwable {
				Class<?> type = invocation.getArgument(0);
				if (type.equals(Integer.class))
					return new IntegerCloneRule();
				return null;
			}
			
		}).when(mockFactory).createRule(ArgumentMatchers.eq(Integer.class), ArgumentMatchers.any(List.class));

		doAnswer(new Answer<CloneRule<?>>() {

			@Override
			public CloneRule<?> answer(InvocationOnMock invocation) throws Throwable {
				Class<?> type = invocation.getArgument(0);
				if (type.equals(Boolean.class))
					return new BooleanCloneRule();
				return null;
			}
			
		}).when(mockFactory).createRule(ArgumentMatchers.eq(Boolean.class), ArgumentMatchers.any(List.class));

		
		standardCloner = new DeepCloner();
		standardFactory = new StandardCloneRuleFactory();

		amgCloner = new DeepCloner();
		amgFactory = new AmalgamatedCloneRuleFactory(Arrays.asList(mockFactory));
	}

	@Test
	public void testCreateCloneRulesAsExpected() throws CloneNotSupportedException {
		List<Class<?>> types = Arrays.asList(Integer.class,
		  									 BigDecimal.class,
		  									 BigInteger.class,
		  									 Boolean.class,
		  									 Byte.class,
		  									 Character.class,
		  									 Double.class,
		  									 Float.class,
		  									 Long.class,
		  									 Short.class,
		  									 String.class,
		  									 List.class,
		  									 Map.class,
		  									 Queue.class,
		  									 Set.class,
		  									 Properties.class
		);
		List<CloneRule<?>> ruleSet = standardFactory.createRuleSet(standardCloner, 
				  types, 
				  new HashMap<String, List<String>>()
	   );
		
	   assertEquals("Not all rules were created...",types.size(), ruleSet.size());
	   
	   assertSame("Did not create Integer Rule", IntegerCloneRule.class, ruleSet.get(0).getClass());
	   assertSame("Did not create BigDecimal Rule", BigDecimalCloneRule.class, ruleSet.get(1).getClass());
	   assertSame("Did not create BigInteger Rule", BigIntegerCloneRule.class, ruleSet.get(2).getClass());
	   assertSame("Did not create Boolean Rule", BooleanCloneRule.class, ruleSet.get(3).getClass());
	   assertSame("Did not create Byte Rule", ByteCloneRule.class, ruleSet.get(4).getClass());
	   assertSame("Did not create Character Rule", CharacterCloneRule.class, ruleSet.get(5).getClass());
	   assertSame("Did not create Double Rule", DoubleCloneRule.class, ruleSet.get(6).getClass());
	   assertSame("Did not create Float Rule", FloatCloneRule.class, ruleSet.get(7).getClass());
	   assertSame("Did not create Long Rule", LongCloneRule.class, ruleSet.get(8).getClass());
	   assertSame("Did not create Short Rule", ShortCloneRule.class, ruleSet.get(9).getClass());
	   assertSame("Did not create String Rule", StringPoolCloneRule.class, ruleSet.get(10).getClass());
	   assertSame("Did not create List Rule", ListCloneRule.class, ruleSet.get(11).getClass());
	   assertSame("Did not create Map Rule", MapCloneRule.class, ruleSet.get(12).getClass());
	   assertSame("Did not create Queue Rule", QueueCloneRule.class, ruleSet.get(13).getClass());
	   assertSame("Did not create Set Rule", SetCloneRule.class, ruleSet.get(14).getClass());
	   assertSame("Did not create Properties Rule", PropertiesCloneRule.class, ruleSet.get(15).getClass());   
	}

	@Test(expected=CloneNotSupportedException.class)
	public void testCouldNotCreateCloneRule() throws CloneNotSupportedException {
		List<Class<?>> types = Arrays.asList(Assert.class, Mockito.class);
		standardFactory.createRuleSet(standardCloner, 
				  types, 
				  new HashMap<String, List<String>>()
	   );
	}
	

	@Test
	public void testStringNonPoolCloneRule() throws CloneNotSupportedException {
		List<Class<?>> types = new LinkedList<>();
		types.add(String.class);
		Map<String,List<String>> params = new HashMap<String, List<String>>();
		
		params.put(String.class.getName(), Arrays.asList(StandardCloneRuleFactory.NON_POOL_CLONE));
		List<CloneRule<?>> ruleSet = standardFactory.createRuleSet(standardCloner, 
				  types, 
				  params
	    );
		
		assertEquals("Not all rules were created...",types.size(), ruleSet.size());   
		assertSame("Did not create String Non Pool Rule", StringNonPoolCloneRule.class, ruleSet.get(0).getClass());   
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// Known types
	@Test
	public void testValidCloneRuleGenerationOnAmalgamatingFactory() throws CloneNotSupportedException {
		List<Class<?>> types = new LinkedList<>();
		types.add(Integer.class);
		
		List<CloneRule<?>> ruleSet = amgFactory.createRuleSet(amgCloner, types, new HashMap<String, List<String>>());
		
		verify(mockFactory,times(1)).createRule(ArgumentMatchers.eq(Integer.class), ArgumentMatchers.any(List.class));
		
		assertEquals("Not all rules were created...",types.size(), ruleSet.size());   
		assertSame("Did not create Integer Rule", IntegerCloneRule.class, ruleSet.get(0).getClass());  
	}
	
	// Unknown type
	@Test(expected=CloneNotSupportedException.class)
	public void testInvalidCloneRuleGenerationOnAmalgamatingFactory() throws CloneNotSupportedException {		
		List<Class<?>> types = new LinkedList<>();
		types.add(Number.class);
		
		amgFactory.createRuleSet(amgCloner, types, new HashMap<String, List<String>>());
	}
	
	// Add new factory
	@Test
	public void testAddCloneRuleGenerationOnAmalgamatingFactory() throws CloneNotSupportedException {
		List<Class<?>> types = new LinkedList<>();
		types.add(Integer.class);
		
		amgFactory = new AmalgamatedCloneRuleFactory(new LinkedList<CloneRuleFactory>());
		
		try {
			amgFactory.createRuleSet(amgCloner, types, new HashMap<String, List<String>>());
			fail("Byte clone rule should not have been processed.");
		} catch (CloneNotSupportedException e) {
			
		}
		((AmalgamatedCloneRuleFactory)amgFactory).addCloneRuleFactory(mockFactory);
		List<CloneRule<?>> ruleSet = amgFactory.createRuleSet(amgCloner, types, new HashMap<String, List<String>>());
		assertEquals("Not all rules were created...",types.size(), ruleSet.size());   
		assertSame("Did not create Integer Rule", IntegerCloneRule.class, ruleSet.get(0).getClass());

	}
	
	// Add new factory with specific name
	@Test
	public void testAddNamedCloneRuleGenerationOnAmalgamatingFactory() throws CloneNotSupportedException {
		List<Class<?>> types = new LinkedList<>();
		amgFactory = new AmalgamatedCloneRuleFactory(new LinkedList<CloneRuleFactory>());
		types.add(Integer.class);
		
		try {
			amgFactory.createRuleSet(amgCloner, types, new HashMap<String, List<String>>());
			fail("Byte clone rule should not have been processed.");
		} catch (CloneNotSupportedException e) {
			
		}
		((AmalgamatedCloneRuleFactory)amgFactory).addCloneRuleFactory("INTEGER_FACTORY",mockFactory);
		List<CloneRule<?>> ruleSet = amgFactory.createRuleSet(amgCloner, types, new HashMap<String, List<String>>());
		assertEquals("Not all rules were created...",types.size(), ruleSet.size());   
		assertSame("Did not create Integer Rule", IntegerCloneRule.class, ruleSet.get(0).getClass());

	}	
	
	// Preference factory success
	@Test
	public void testValidPrefFactoryRuleGenerationOnAmalgamatingFactory() throws CloneNotSupportedException {
		amgFactory = new AmalgamatedCloneRuleFactory(Arrays.asList(standardFactory));
		((AmalgamatedCloneRuleFactory)amgFactory).addCloneRuleFactory("MOCK_FACTORY", mockFactory);
		
		List<Class<?>> types = new LinkedList<>();
		types.add(Integer.class);
		types.add(Boolean.class);
		Map<String, List<String>> params = new HashMap<>();
		params.put("java.lang.Integer", Arrays.asList("FACTORY_IMPL_REQ:MOCK_FACTORY"));
		params.put("java.lang.Boolean", Arrays.asList("FACTORY_IMPL_REQ:MOCK_FACTORY"));
		
		List<CloneRule<?>> ruleSet = amgFactory.createRuleSet(amgCloner, types, params);
		
		verify(mockFactory,times(1)).createRule(ArgumentMatchers.eq(Integer.class), ArgumentMatchers.any(List.class));
		verify(mockFactory,times(1)).createRule(ArgumentMatchers.eq(Boolean.class), ArgumentMatchers.any(List.class));
		
		assertEquals("Not all rules were created...",types.size(), ruleSet.size());   
		assertSame("Did not create Integer Rule", IntegerCloneRule.class, ruleSet.get(0).getClass());  
		assertSame("Did not create Boolean Rule", BooleanCloneRule.class, ruleSet.get(1).getClass());
	}
	
	// Preference factory failure
	@Test(expected=CloneNotSupportedException.class)
	public void testInvalidPrefFactoryRuleGenerationOnAmalgamatingFactory() throws CloneNotSupportedException {
		amgFactory = new AmalgamatedCloneRuleFactory(Arrays.asList(standardFactory));
		((AmalgamatedCloneRuleFactory)amgFactory).addCloneRuleFactory("MOCK_FACTORY", mockFactory);
		
		List<Class<?>> types = new LinkedList<>();
		types.add(Number.class);
		Map<String, List<String>> params = new HashMap<>();
		params.put("java.lang.Number", Arrays.asList("FACTORY_IMPL_REQ:MOCK_FACTORY"));

		amgFactory.createRuleSet(amgCloner, types, params);
	}
	
	// Preference factory failure
	@Test(expected=CloneNotSupportedException.class)
	public void testNonExistantFactoryPrefOnAmalgamatingFactory() throws CloneNotSupportedException {
		amgFactory = new AmalgamatedCloneRuleFactory(Arrays.asList(standardFactory));
		((AmalgamatedCloneRuleFactory)amgFactory).addCloneRuleFactory("MOCK_FACTORY", mockFactory);
		
		List<Class<?>> types = new LinkedList<>();
		types.add(Number.class);
		Map<String, List<String>> params = new HashMap<>();
		params.put("java.lang.Number", Arrays.asList("FACTORY_IMPL_REQ:WHAT_MOCK_FACTORY_???"));

		amgFactory.createRuleSet(amgCloner, types, params);
	}
}
