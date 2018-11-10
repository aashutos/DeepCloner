/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 *  Clone Rule used to clone Float objects.
 * 
 *  @author Aashutos Kakshepati
 */
public class FloatCloneRule extends PrimitiveCloneRule<Float> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Float;
	}

	@Override
	public Float clone(Object object) {
		Float srcFloat = (Float) object;
		return new Float(srcFloat);
	}
}
