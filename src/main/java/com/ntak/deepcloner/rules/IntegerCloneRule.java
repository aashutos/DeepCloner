/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules;

import com.ntak.deepcloner.CloneRule;
import com.ntak.deepcloner.DeepCloner;

/**
 * 	Clone Rule used to clone Integer objects.
 * 
 *  @author Aashutos Kakshepati
 */
public class IntegerCloneRule implements CloneRule<Integer> {
	
	@Override
	public void setRuleContext(DeepCloner context) {
	}

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
