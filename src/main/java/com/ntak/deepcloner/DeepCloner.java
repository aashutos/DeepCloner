/**
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner;

import static com.ntak.deepcloner.exceptions.ExceptionMessages.ERR_CLONE_NOT_SUP;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.ntak.deepcloner.exceptions.UnsupportedCloneTypeException;
import com.ntak.deepcloner.factories.CloneRuleFactory;
import com.ntak.deepcloner.factories.StandardCloneRuleFactory;

import static com.ntak.deepcloner.utils.DeepClonerUtils.getCloneRuleErasureInformation;
/**
 * Class encapsulating functionality to deep clone an object of a specified type.
 * 
 * @author Aashutos Kakshepati
 *
 */
public class DeepCloner {

	protected final Queue<CloneRule<?>> cloneRules;
	protected final ReadWriteLock lock;
	protected final CloneRuleFactory factory;
	
	public DeepCloner() {
		this(new StandardCloneRuleFactory());		
	}
	
	public DeepCloner(CloneRuleFactory factory) {
		super();
		cloneRules = new LinkedList<>();
		lock = new ReentrantReadWriteLock(true);
		this.factory = factory;
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
	
	@SuppressWarnings("unchecked")
	public <K> K deepClone(K o, Class<?> hintKlass) {
		if (o == null) {
			throw new UnsupportedCloneTypeException(String.format(ERR_CLONE_NOT_SUP, "null", cloneRules.toString()));
		}
		
		lock.readLock().lock();
		for (CloneRule<?> rule : cloneRules) {
			if (getCloneRuleErasureInformation(rule.getClass()).equals(hintKlass))
				if (rule.isInstanceOf(o)) {
					return (K) rule.clone(o);
				}
		}
		lock.readLock().unlock();
	
		throw new UnsupportedCloneTypeException(String.format(ERR_CLONE_NOT_SUP, o.getClass().getSimpleName(), cloneRules.toString()));
	}
	
	/**
	 * Decorator method which adds cloning rules to the context for additional cloning functionality. There are order guarantees on the clone rule collection as it uses a Queue implementation as the underlying Collection.
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
	
	public DeepCloner addFactoryCloneRule(List<Class<?>> klasses, Map<String,List<String>> paramMap) {
		try {
			List<CloneRule<?>> rules = factory.createRuleSet(this, klasses, paramMap);
			if (rules != null) {
				lock.writeLock().lock();
				cloneRules.addAll(rules);
				lock.writeLock().unlock();
			}
		} catch (CloneNotSupportedException e) {
			// ignore failure - Warning should 
		}

		return this;
	}
	
	public DeepCloner addFactoryCloneRule(Class<?> klass, List<String> param) {
		try {
			CloneRule<?> rule = factory.createRule(klass, param);
			rule.setRuleContext(this);
			
			if (rule != null) {
				lock.writeLock().lock();
				cloneRules.add(rule);
				lock.writeLock().unlock();
			}
		} catch (CloneNotSupportedException e) {
			// ignore failure - Warning should 
		}

		return this;
	}
	
	public DeepCloner genImmutableDeepCloner() {
		return new ImmutableDeepCloner(this.cloneRules);
	}
}
