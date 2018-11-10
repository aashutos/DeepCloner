/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.collections;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import com.ntak.deepcloner.CombinatoryCloneRule;

/**
 * Clone Rule for creating deep duplicates of a Map of the same implementation.
 * 
 *  @author Aashutos Kakshepati
 */
public class MapCloneRule<K,V> extends CombinatoryCloneRule<Map<K, V>> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Map;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<K, V> clone(Object object) {
		try {
			Map<K,V> srcMap = (Map<K,V>) object;
			Map<K,V> cloneMap = (Map<K,V>) object.getClass().newInstance();
			
			for (Entry<K,V> item : srcMap.entrySet()) {
				K cloneKey = context.deepClone(item.getKey());
				V cloneValue = context.deepClone(item.getValue());
				cloneMap.put(cloneKey, cloneValue);
			}

			return cloneMap;
		} catch (Exception e) {
			Map<K,V> errMap = Collections.emptyMap();
			return errMap;
		}
	}
}
