/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 *  Clone Rule used to clone Double objects.
 * 
 *  @author Aashutos Kakshepati
 */
public class DoubleCloneRule extends PrimitiveCloneRule<Double> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Double;
	}

	@Override
	public Double clone(Object object) {
		Double srcDouble = (Double) object;
		return new Double(srcDouble);
	}
}
