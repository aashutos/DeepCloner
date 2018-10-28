/**
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner;

import static com.ntak.deepcloner.exceptions.ExceptionMessages.ERR_CLONE_NOT_SUP;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.ntak.deepcloner.exceptions.UnsupportedCloneTypeException;

/**
 * Class encapsulating functionality to deep clone an object of a specified type.
 * 
 * @author Aashutos Kakshepati
 *
 */
public class DeepCloner {

	private final Queue<CloneRule<?>> cloneRules;
	private final ReadWriteLock lock;
	
	public DeepCloner() {
		super();
		cloneRules = new LinkedList<>();
		lock = new ReentrantReadWriteLock(true);
	}
	
	@SuppressWarnings("unchecked")
	public <K> K deepClone(K o) {
		if (o == null) {
			throw new UnsupportedCloneTypeException(String.format(ERR_CLONE_NOT_SUP, "null", cloneRules.toString()));
		}
		
		lock.readLock().lock();
		for (CloneRule<?> rule : cloneRules) {
			if (rule.isInstanceOf(o)) {
				return (K) rule.clone(o);
			}
		}
		lock.readLock().unlock();
	
		throw new UnsupportedCloneTypeException(String.format(ERR_CLONE_NOT_SUP, o.getClass().getSimpleName(), cloneRules.toString()));
	}
	
	/**
	 * Decorator method which adds cloning rules to the context for additional cloning functionality.
	 * @param rule - Adds cloning functionality for a specific type
	 * @return self for further additions
	 */
	public DeepCloner addCloneRule(CloneRule<?> rule) {
		rule.setRuleContext(this);

		lock.writeLock().lock();
		cloneRules.add(rule);
		lock.writeLock().unlock();
		
		return this;
	}
	
}
