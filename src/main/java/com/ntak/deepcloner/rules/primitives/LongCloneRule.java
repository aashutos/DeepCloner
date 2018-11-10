/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 *  Clone Rule used to clone Long objects.
 * 
 *  @author Aashutos Kakshepati
 */
public class LongCloneRule extends PrimitiveCloneRule<Long> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Long;
	}

	@Override
	public Long clone(Object object) {
		Long srcLong = (Long) object;
		return new Long(srcLong.toString());
	}
}
