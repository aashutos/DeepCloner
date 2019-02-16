/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.factories;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;

import com.ntak.deepcloner.CloneRule;
import com.ntak.deepcloner.DeepCloner;
import com.ntak.deepcloner.rules.collections.ImmutableArrayListCloneRule;
import com.ntak.deepcloner.rules.collections.ListCloneRule;
import com.ntak.deepcloner.rules.collections.MapCloneRule;
import com.ntak.deepcloner.rules.collections.QueueCloneRule;
import com.ntak.deepcloner.rules.collections.SetCloneRule;
import com.ntak.deepcloner.rules.primitives.BigDecimalCloneRule;
import com.ntak.deepcloner.rules.primitives.BigIntegerCloneRule;
import com.ntak.deepcloner.rules.primitives.BooleanCloneRule;
import com.ntak.deepcloner.rules.primitives.ByteCloneRule;
import com.ntak.deepcloner.rules.primitives.CharacterCloneRule;
import com.ntak.deepcloner.rules.primitives.CloneableCloneRule;
import com.ntak.deepcloner.rules.primitives.DoubleCloneRule;
import com.ntak.deepcloner.rules.primitives.FloatCloneRule;
import com.ntak.deepcloner.rules.primitives.IntegerCloneRule;
import com.ntak.deepcloner.rules.primitives.LongCloneRule;
import com.ntak.deepcloner.rules.primitives.ShortCloneRule;
import com.ntak.deepcloner.rules.primitives.StringNonPoolCloneRule;
import com.ntak.deepcloner.rules.primitives.StringPoolCloneRule;
import com.ntak.deepcloner.rules.structures.PropertiesCloneRule;

import static com.ntak.deepcloner.exceptions.ExceptionMessages.*;

/**
 *  Implementation of the CloneRuleFactory to deal with generating Clone Rules for Deep Cloner using the standard set of available clone rules.
 * 
 *  @author Aashutos Kakshepati
 */
public class StandardCloneRuleFactory implements CloneRuleFactory {

	private static final String ARRAY_LIST_CLASS = "java.util.Arrays$ArrayList";
	public static final String NON_POOL_CLONE = "NON_POOL_CLONE";

	@Override
	public List<CloneRule<?>> createRuleSet(DeepCloner context, List<Class<?>> types, Map<String, List<String>> factoryParams) throws CloneNotSupportedException {
		List<CloneRule<?>> ruleCache = new LinkedList<>();
		
		for (Class<?> type : types) {
			List<String> params = factoryParams.getOrDefault(type.getName(), new LinkedList<String>());
			CloneRule<?> newRule = createRule(type, params);
			newRule.setRuleContext(context);
			ruleCache.add(newRule);
		}
		
		return ruleCache;
	}

	@Override
	public CloneRule<?> createRule(Class<?> type, List<String> factoryParams) throws CloneNotSupportedException {
		String typeName = type.getName();
		
		// PRIMITIVES
		if (Integer.class.getName().equals(typeName)) {
			return new IntegerCloneRule();
		}
		if (BigDecimal.class.getName().equals(typeName)) {
			return new BigDecimalCloneRule();
		}
		if (BigInteger.class.getName().equals(typeName)) {
			return new BigIntegerCloneRule();
		}
		if (Boolean.class.getName().equals(typeName)) {
			return new BooleanCloneRule();
		}
		if (Byte.class.getName().equals(typeName)) {
			return new ByteCloneRule();
		}
		if (Character.class.getName().equals(typeName)) {
			return new CharacterCloneRule();
		}
		if (Double.class.getName().equals(typeName)) {
			return new DoubleCloneRule();
		}
		if (Float.class.getName().equals(typeName)) {
			return new FloatCloneRule();
		}
		if (Long.class.getName().equals(typeName)) {
			return new LongCloneRule();
		}
		if (Short.class.getName().equals(typeName)) {
			return new ShortCloneRule();
		}
		if (Cloneable.class.getName().equals(typeName)) {
			return new CloneableCloneRule();
		}
		if (String.class.getName().equals(typeName)) {
			if (factoryParams.contains(NON_POOL_CLONE))
				return new StringNonPoolCloneRule();
			else 
				return new StringPoolCloneRule();
		}
				
		// COLLECTIONS
		if (ARRAY_LIST_CLASS.equals(typeName)) {
			return new ImmutableArrayListCloneRule<Object>();
		}
		if (List.class.getName().equals(typeName)) {
			return new ListCloneRule<Object>();
		}		
		if (Map.class.getName().equals(typeName)) {
			return new MapCloneRule<Object,Object>();
		}	
		if (Queue.class.getName().equals(typeName)) {
			return new QueueCloneRule<Object>();
		}	
		if (Set.class.getName().equals(typeName)) {
			return new SetCloneRule<Object>();
		}	
		
		// STRUCTURES
		if (Properties.class.getName().equals(typeName)) {
			return new PropertiesCloneRule();
		}	
		
		// Unknown Class
		throw new CloneNotSupportedException(ERR_RULE_NOT_SUP);
		
	}

}
