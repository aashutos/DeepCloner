/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 *  Clone Rule used to clone Short objects.
 * 
 *  @author Aashutos Kakshepati
 */
public class ShortCloneRule extends PrimitiveCloneRule<Short> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Short;
	}

	@Override
	public Short clone(Object object) {
		Short src = (Short) object;
		return new Short(src);
	}
}
