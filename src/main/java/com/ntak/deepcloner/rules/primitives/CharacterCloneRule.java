/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import com.ntak.deepcloner.PrimitiveCloneRule;

/**
 *  Clone Rule used to clone Character objects.
 * 
 *  @author Aashutos Kakshepati
 */
public class CharacterCloneRule extends PrimitiveCloneRule<Character> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Character;
	}

	@Override
	public Character clone(Object object) {
		Character srcChar = (Character) object;
		return new Character(srcChar);
	}
}
