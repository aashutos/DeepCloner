/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 *  Clone rule, which returns the same String as observed in the String Pool.
 * 
 *  @author Aashutos Kakshepati
 */
public class StringPoolCloneRule extends PrimitiveCloneRule<String> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof String;
	}

	@Override
	public String clone(Object object) {
		return object.toString();
	}
}
