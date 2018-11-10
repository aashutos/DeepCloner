/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.collections;

import java.util.Collections;
import java.util.List;

import com.ntak.deepcloner.CombinatoryCloneRule;

/**
 *  Clone Rule for creating deep duplicates of a List of the same implementation.
 * 
 *  @author Aashutos Kakshepati
 */
public class ListCloneRule<T> extends CombinatoryCloneRule<List<T>> {

	@Override
	public boolean isInstanceOf(Object object) {		
		return object instanceof List;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> clone(Object object) {
		try {
			List<T> srcList = (List<T>) object;
			List<T> cloneList = (List<T>) object.getClass().newInstance();
			
			for (T item : srcList) {
				T cloneItem = context.deepClone(item);
				cloneList.add(cloneItem);
			}
			
			return cloneList;
		} catch (Exception e) {
			List<T> errList = Collections.emptyList();
			return errList;
		}
	}
}
