/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 *  Clone Rule used to clone Boolean objects.
 * 
 *  @author Aashutos Kakshepati
 */
public class BooleanCloneRule extends PrimitiveCloneRule<Boolean> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Boolean;
	}

	@Override
	public Boolean clone(Object object) {
		Boolean srcBool = (Boolean) object;
		return new Boolean(srcBool);
	}
}
