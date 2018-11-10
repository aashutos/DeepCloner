/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

/**
 *  @author Aashutos Kakshepati
 */
public class TestHelper {

	public static void isPerfectCollectionClone(Collection<?> src, Collection<?> clone) {
		assertEquals("The sizes do not match for the Collection.", src.size(),clone.size());
		
		Iterator<?> srcIt = src.iterator();
		Iterator<?> cloneIt = clone.iterator();
		
		while (srcIt.hasNext()) {
			Object currentSrc = srcIt.next();
			Object currentClone = cloneIt.next();
			assertTrue("Elements were not equal in collection. In Source, but not in Clone.", clone.contains(currentSrc));	
			assertTrue("Elements were not equal in collection. In Clone, but not in Source.", src.contains(currentClone));		

		}
		assertNotSame("The two collections are the same :(.", src, clone);
		assertSame("Collections are of different implementations.", src.getClass(), clone.getClass());
	}
	
	
	public static void isPerfectPrimitiveClone(Object src, Object clone) {
		assertEquals("The values are not equal.", src, clone);
		assertNotSame("The values are the same.", src, clone);
	}
}
