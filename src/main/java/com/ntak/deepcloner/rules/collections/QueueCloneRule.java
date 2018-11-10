/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.collections;

import java.util.LinkedList;
import java.util.Queue;

import com.ntak.deepcloner.CombinatoryCloneRule;

/**
 * Clone Rule for creating deep duplicates of a Queue of the same implementation.
 * 
 *  @author Aashutos Kakshepati
 */
public class QueueCloneRule<K> extends CombinatoryCloneRule<Queue<K>> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Queue;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Queue<K> clone(Object object) {
		try {
			Queue<K> srcQueue = (Queue<K>) object;
			Queue<K> cloneQueue = (Queue<K>) object.getClass().newInstance();
			
			for (K item : srcQueue) {
				K cloneItem = context.deepClone(item);
				cloneQueue.add(cloneItem);
			}
			
			return cloneQueue;
		} catch (Exception e) {
			return new LinkedList<>();
		}
	}
}
