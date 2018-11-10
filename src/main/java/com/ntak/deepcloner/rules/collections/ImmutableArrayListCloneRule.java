/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.collections;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.ntak.deepcloner.CombinatoryCloneRule;

/**
 *  Clone Rule for creating deep immutable duplicates of an immutable Array List (From an Array).
 * 
 *  @author Aashutos Kakshepati
 */
public class ImmutableArrayListCloneRule<T> extends CombinatoryCloneRule<List<T>> {

	@Override
	public boolean isInstanceOf(Object object) {
		// Bit of a hack :(...
		return Arrays.asList(0).getClass().isInstance(object);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> clone(Object object) {
		try {
			List<T> srcList = (List<T>) object;
			List<T> cloneList = new LinkedList<>();
			
			// Bit more of a hack...
			Class<T> klass = null;
			
			for (T item : srcList) {
				T cloneItem = context.deepClone(item);
				klass = (Class<T>) cloneItem.getClass();
				cloneList.add(cloneItem);
			}
			
			// Even more hacks...
	        T[] cloneArray = (T[]) Array.newInstance(klass, cloneList.size());
			cloneArray = cloneList.toArray(cloneArray);
			
			return Arrays.asList(cloneArray);
		} catch (Exception e) {
			List<T> errList = Collections.emptyList();
			return errList;
		}
	}
}
