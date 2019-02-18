/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *  Utility class containing useful objects and methods for dealing with common Deep Cloner library related tasks.
 * 
 *  @author Aashutos Kakshepati
 */
public class DeepClonerUtils {
	private static final String LTN = "<";
	private static final String GTR = ">";
	
	public static Set<Class<?>> getDirectHierarchy(Class<?> klass) {
		Set<Class<?>> dirHier = new HashSet<>();
		if (klass.getSuperclass() != null)
			dirHier.add(klass.getSuperclass());
		dirHier.addAll(Arrays.asList(klass.getInterfaces()));
		return dirHier;
	}

	public static Class<?> getCloneRuleErasureInformation(Class<?> klass) {
		if (klass == null || klass.getGenericSuperclass() == null)
			return null;
		
		String type = klass.getGenericSuperclass().getTypeName();			
		
		// Returns type name from within the outer angular brackets
		type = type.substring(type.indexOf(LTN)+1,type.lastIndexOf(GTR));
		
		try {
			// If the sub-type is generic as well then we just pull the actual type value and omit the generic typing.
			if (type.indexOf(LTN) != -1)
				type = type.substring(0,type.indexOf(LTN));
			return Class.forName(type);
		} catch (ClassNotFoundException e) {
		}	
		return null;
	}
	
}
