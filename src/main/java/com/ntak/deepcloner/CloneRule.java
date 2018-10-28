/**
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner;

/**
 * Interface defining the methodology of cloning of a single object. 
 * For complex objects a DeepCloner can be passed in to aid with daughter object cloning in a recursive sort of manner. 
 * 
 * @author Aashutos Kakshepati
 *
 */
public interface CloneRule<K> {

	public void setRuleContext(DeepCloner context); // For any nested evaluation of cloning process
	public boolean isInstanceOf(Object object);
	public K clone(Object object);
	
}
