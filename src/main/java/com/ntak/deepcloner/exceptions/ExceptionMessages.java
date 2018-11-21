/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.exceptions;

/**
 *  Utility class containing exception messages.
 *  
 *  @author Aashutos Kakshepati
 */
public class ExceptionMessages {
	
	public static final String ERR_CLONE_NOT_SUP = "The type being converted is not supported. The type being converted: %s; Types supported in context: %s.";
	public static final String ERR_OP_NOT_SUP = "The operation being performed is not supported by Deep Cloner.";
	public static final String ERR_RULE_NOT_SUP = "The rule being requested is not supported by the Clone Rule factory.";
	public static final String EXC_FACTORY_DOES_NOT_EXIST = "The factory specified does not exist. Factory Parameter passed in: %s.";
}
