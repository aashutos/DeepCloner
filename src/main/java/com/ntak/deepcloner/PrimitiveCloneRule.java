/**
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner;

/**
 * Interface defining the methodology of cloning of simple objects (no nested cloning required). 
 * 
 * @author Aashutos Kakshepati
 *
 */
public abstract class PrimitiveCloneRule<K> implements CloneRule<K> {

	@Override
	public final void setRuleContext(DeepCloner context) {
	}
}
