/*
 *  (c) copyright 2019 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import com.ntak.deepcloner.PrimitiveCloneRule;
import com.ntak.deepcloner.exceptions.UnsupportedCloneTypeException;
import static com.ntak.deepcloner.exceptions.ExceptionMessages.EXC_CLONE_RULE_NESTED_EXCEPTION;

/**
 *  @author Aashutos Kakshepati
 */
public class CloneableCloneRule extends PrimitiveCloneRule<Cloneable> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Cloneable;
	}

	@Override
	public Cloneable clone(Object object) {
		try {			
			return (Cloneable) object.getClass().getDeclaredMethod("clone").invoke(object);
		} catch (Exception e) {
			throw new UnsupportedCloneTypeException(String.format(EXC_CLONE_RULE_NESTED_EXCEPTION, this.getClass().getName(), e.getClass().getName(), e.getMessage()));
		}
	}

}
