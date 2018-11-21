/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.factories;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.ntak.deepcloner.CloneRule;
import com.ntak.deepcloner.DeepCloner;

import static com.ntak.deepcloner.exceptions.ExceptionMessages.*;

/**
 *  <p>
 *  A special Clone Rule Factory, which can iterate through implementations of factories in order to generate the required rule implementations by type.
 *  </p><p>
 *  You can pass a parameter to set a preference over a specific factory to set an implementation of a Clone Rule for a given type. The format for the factory preference parameter can be written using a key:value format e.g. "FACTORY_IMPL_REQ:com.xyz...XYZCloneFactory"
 *  </p>
 *  @author Aashutos Kakshepati
 */
public class AmalgamatedCloneRuleFactory implements CloneRuleFactory {

	private Map<String, CloneRuleFactory> factoriesCache;
	public static final String PREFIX_FACTORY_IMPL_REQ = "FACTORY_IMPL_REQ";
	public static final Pattern colonDel = Pattern.compile(Pattern.quote(":"));
	
	public AmalgamatedCloneRuleFactory(List<CloneRuleFactory> factories) {
		super();
		this.factoriesCache = new HashMap<>();
		for (CloneRuleFactory factory : factories) {
			factoriesCache.put(factory.getClass().getSimpleName(), factory);			
		}
	}
	
	@Override
	public List<CloneRule<?>> createRuleSet(DeepCloner context, List<Class<?>> types,
			Map<String, List<String>> factoryParams) throws CloneNotSupportedException {
		
		List<CloneRule<?>> ruleSet = new LinkedList<>();
		
		// For each type generate Clone Rules...
		for (Class<?> type : types) {
			List<String> params = factoryParams.getOrDefault(type.getName(), new LinkedList<String>());
			CloneRule<?> newRule = createRule(type, params);
			
			if (newRule == null) {
				throw new CloneNotSupportedException(ERR_RULE_NOT_SUP);
			}
			
			ruleSet.add(newRule);
		}
		
		return ruleSet;
	}

	@Override
	public CloneRule<?> createRule(Class<?> type, List<String> factoryParams) throws CloneNotSupportedException {
		// Check parameters for factory preference...
		CloneRule<?> newRule = null;
		for (String param : factoryParams) {
			// Factory preference exists then we use that implementation...
			if (param.startsWith(PREFIX_FACTORY_IMPL_REQ)) {
				String[] keyVal = colonDel.split(param);	
				CloneRuleFactory factory;
				if (keyVal.length == 2 && ((factory = factoriesCache.get(keyVal[1])) != null)) {
					return factory.createRule(type, factoryParams);
				} else { // Factory does not exist so rules cannot be created!
					throw new CloneNotSupportedException(String.format(EXC_FACTORY_DOES_NOT_EXIST,keyVal[1]));
				}
			} 
		}
		
		// No preference. So iterate through all factories and take first match.
		for (CloneRuleFactory factory : factoriesCache.values()) {
			try {
					newRule = factory.createRule(type, factoryParams);
					if (newRule != null)
						return newRule;
			} catch (CloneNotSupportedException e) {
					// Ignore and try next factory
			}
		}
		return newRule; // Don't bother if null as this is handled in outer method or by callee.
	}

	public AmalgamatedCloneRuleFactory addCloneRuleFactory(CloneRuleFactory factory) {
		addCloneRuleFactory(factory.getClass().getSimpleName(), factory);
		return this;
	}

	public AmalgamatedCloneRuleFactory addCloneRuleFactory(String key, CloneRuleFactory factory) {
		if (key != null && !key.trim().equals("") && factory != null)
			factoriesCache.put(key, factory);
		return this;
	}
	
}
