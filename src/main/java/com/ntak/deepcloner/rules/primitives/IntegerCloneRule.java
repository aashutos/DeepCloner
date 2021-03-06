/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 * 	Clone Rule used to clone Integer objects.
 * 
 *  @author Aashutos Kakshepati
 */
public class IntegerCloneRule extends PrimitiveCloneRule<Integer> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Integer;
	}

	@Override
	public Integer clone(Object object) {
		Integer srcInt = (Integer) object;
		return new Integer(srcInt);
	}
}
