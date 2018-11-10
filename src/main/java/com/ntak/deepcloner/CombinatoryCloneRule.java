/**
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner;

/**
 * Interface defining the methodology of cloning of complex objects. 
 * For complex objects a DeepCloner can be passed in to aid with daughter object cloning in a recursive sort of manner. 
 * 
 * @author Aashutos Kakshepati
 *
 */
public abstract class CombinatoryCloneRule<K> implements CloneRule<K> {

	protected DeepCloner context;
	
	@Override
	public final void setRuleContext(DeepCloner context) {
		this.context = context;
	}
}
