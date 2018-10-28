/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules;

import java.util.Collections;
import java.util.List;

import com.ntak.deepcloner.CloneRule;
import com.ntak.deepcloner.DeepCloner;

/**
 *  @author Aashutos Kakshepati
 */
public class ListCloneRule<T> implements CloneRule<List<T>> {

	DeepCloner context;
	
	@Override
	public void setRuleContext(DeepCloner context) {
		this.context = context;
	}

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
		} catch (InstantiationException | IllegalAccessException e) {
			return Collections.EMPTY_LIST;
		}
	}

}
