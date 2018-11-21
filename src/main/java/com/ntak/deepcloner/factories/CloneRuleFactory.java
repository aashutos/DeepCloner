/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.factories;

import java.util.List;
import java.util.Map;

import com.ntak.deepcloner.CloneRule;
import com.ntak.deepcloner.DeepCloner;

/**
 *  Interface defines a method to generate instances of a set of Clone rules.
 * 
 *  @author Aashutos Kakshepati
 */
public interface CloneRuleFactory {

	/**
	 * Create a bound set of required Clone Rules to a particular DeepCloner context.
	 * 
	 * @param context - Deep Cloner instance to bind Clone Rules to.
	 * @param types - List of types that need to cloned.
	 * @param factoryParams - Map for String parameters grouped by Class Name or other unique user-defined String identifier in order to help building the Clone Rule.
	 * @return List&lt;CloneRule&gt; - List of clone rules.
	 * @throws CloneNotSupportedException - The factory cannot generate a Clone Rule for the given type.
	 */
	List<CloneRule<?>> createRuleSet(DeepCloner context, List<Class<?>> types, Map<String,List<String>> factoryParams) throws CloneNotSupportedException;
	
	/**
	 * Generates an unbound Clone Rule for free execution.
	 * 
	 * @param type - Class of type for Clone Rule to be generated.
	 * @param factoryParams - List of parameters to aid with Clone Rule generation.
	 * @return CloneRule - Clone Rule generated.
	 * 
	 * @throws CloneNotSupportedException - The factory cannot generate a Clone Rule for the given type.
	 */
	CloneRule<?> createRule(Class<?> type, List<String> factoryParams) throws CloneNotSupportedException;
	
}
