/**
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.exceptions;

import com.ntak.deepcloner.CloneRule;

/**
 * Exception is thrown when a {@link CloneRule} does not exist for the type to be cloned.
 * 
 * @author Aashutos Kakshepati
 *
 */
public class UnsupportedCloneTypeException extends RuntimeException {

	private static final long serialVersionUID = -2697827118951158709L;

	public UnsupportedCloneTypeException(String message) {
		super(message);
	}	
}
