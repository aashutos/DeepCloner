/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 * Clone Rule for duplicating a Byte object.
 * 
 *  @author Aashutos Kakshepati
 */
public class ByteCloneRule extends PrimitiveCloneRule<Byte> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Byte;
	}

	@Override
	public Byte clone(Object object) {
		Byte srcObj = (Byte)object;
		Byte cloneObj = new Byte(srcObj);
		
		return cloneObj;
	}
}
