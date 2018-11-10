/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import java.math.BigInteger;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 *  Clone Rule used to clone BigInteger objects.
 * 
 *  @author Aashutos Kakshepati
 */
public class BigIntegerCloneRule extends PrimitiveCloneRule<BigInteger> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof BigInteger;
	}

	@Override
	public BigInteger clone(Object object) {
		BigInteger srcVal = (BigInteger) object;
		return new BigInteger(srcVal.toString());
	}
}
