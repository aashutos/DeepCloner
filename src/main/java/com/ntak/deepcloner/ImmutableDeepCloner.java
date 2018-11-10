/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner;

import static com.ntak.deepcloner.exceptions.ExceptionMessages.*;

import java.util.Queue;

/**
 *  A non modifiable version of Deep Cloner for read only usage.
 * 
 *  @author Aashutos Kakshepati
 */
public class ImmutableDeepCloner extends DeepCloner {

	public ImmutableDeepCloner(Queue<CloneRule<?>> rules) {
		super();
		this.cloneRules.addAll(rules);
	}
	
	/**
	 * Immutable Deep Cloner does not support the addition of new Clone Rules.
	 * 
	 */
	public DeepCloner addCloneRule(CloneRule<?> rule) {
		throw new UnsupportedOperationException(ERR_OP_NOT_SUP);
	}
}
