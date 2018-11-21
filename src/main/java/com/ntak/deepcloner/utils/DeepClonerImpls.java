/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.utils;

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

import com.ntak.deepcloner.DeepCloner;
import com.ntak.deepcloner.factories.CloneRuleFactory;
import com.ntak.deepcloner.factories.StandardCloneRuleFactory;

/**
 *  Some default implementations are available for use here.
 * 
 *  @author Aashutos Kakshepati
 */
public class DeepClonerImpls {

	static {
		List<Class<?>> types = Arrays.asList(
				   Arrays.asList("").getClass(),
				   List.class, 
				   Map.class, 
				   Queue.class, 
				   Set.class, 
				   Properties.class, 																							   
				   BigDecimal.class, 
				   BigInteger.class, 
				   Boolean.class, 
				   Byte.class, 
				   Character.class, 
				   Double.class, 
				   Float.class, 
				   Integer.class, 
				   Long.class, 
				   Short.class,
				   String.class
		  );
		
		Map<String,List<String>> params = new HashMap<>();
		
		for (Class<?> type : types) {
			params.put(type.getName(), null);
		}
		params.put(String.class.getName(), new LinkedList<String>());
		INIT_PARAMS = params;
		INIT_TYPES = types;
	}
	
	///// CLONE FACTORY /////
	public static final CloneRuleFactory DEFAULT_CLONE_FACTORY = new StandardCloneRuleFactory();
	public static final Map<String, List<String>> INIT_PARAMS;
	public static final List<Class<?>> INIT_TYPES;	
	
	///// CLONER /////
	
	/**
	 * This implementation of DeepCloner does not clone String values, but reuses values found in the String Pool.
	 */
	public static final DeepCloner IMMUTABLE_PASS_STRING_CLONER = new DeepCloner(DEFAULT_CLONE_FACTORY)
																			   .addFactoryCloneRule(
																					   INIT_TYPES,
																					  INIT_PARAMS)
																			   .genImmutableDeepCloner();
}
