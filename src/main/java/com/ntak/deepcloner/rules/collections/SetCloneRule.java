/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.collections;

import java.util.Collections;
import java.util.Set;

import com.ntak.deepcloner.CombinatoryCloneRule;

/**
 * Clone Rule for creating deep duplicates of a Set of the same implementation.
 * 
 *  @author Aashutos Kakshepati
 */
public class SetCloneRule<K> extends CombinatoryCloneRule<Set<K>> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Set;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<K> clone(Object object) {
		try {
			Set<K> srcSet = (Set<K>) object;
			Set<K> cloneSet = (Set<K>) object.getClass().newInstance();
			
			for (K item : srcSet) {
				K cloneItem = context.deepClone(item);
				cloneSet.add(cloneItem);
			}
			
			return cloneSet;
		} catch (Exception e) {
			Set<K> errSet = Collections.emptySet();
			return errSet;
		}
	}
}
