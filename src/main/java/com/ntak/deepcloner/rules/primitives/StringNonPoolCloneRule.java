/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 * This Clone Rule duplicates a String Object where the resulting 
 * object will not be found in the String pool.
 * 
 *  @author Aashutos Kakshepati
 */
public class StringNonPoolCloneRule extends PrimitiveCloneRule<String> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof String;
	}

	@Override
	public String clone(Object object) {
		return new String (object.toString());
	}
}
