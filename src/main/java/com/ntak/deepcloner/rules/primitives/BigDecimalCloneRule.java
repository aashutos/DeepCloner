/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import java.math.BigDecimal;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 *  Clone Rule used to clone BigDecimal objects.
 * 
 *  @author Aashutos Kakshepati
 */
public class BigDecimalCloneRule extends PrimitiveCloneRule<BigDecimal> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof BigDecimal;
	}

	@Override
	public BigDecimal clone(Object object) {
		BigDecimal srcVal = (BigDecimal) object;
		return new BigDecimal(srcVal.toPlainString());
	}
}
