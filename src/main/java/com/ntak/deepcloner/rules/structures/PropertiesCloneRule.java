/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.structures;

import java.util.Properties;
import java.util.Map.Entry;

import com.ntak.deepcloner.CombinatoryCloneRule;

/**
 *  Clone Rule used to clone Properties objects. It will attempt to Deep Clone each entry within the properties object.
 * 
 *  @author Aashutos Kakshepati
 */
public class PropertiesCloneRule extends CombinatoryCloneRule<Properties> {

	@Override
	public boolean isInstanceOf(Object object) {
		return object instanceof Properties;
	}

	@Override
	public Properties clone(Object object) {
		Properties srcProperties = (Properties) object;
		
		try {
			Properties cloneProperties = srcProperties.getClass().newInstance();

			for (Entry<Object,Object> entry : srcProperties.entrySet()) {
				String key = entry.getKey().toString();
				String value = srcProperties.getProperty(key,"");
				
				cloneProperties.setProperty(context.deepClone(key), context.deepClone(value));
			}
			
			return cloneProperties;
		} catch (Exception e) {
			Properties emptyProperties = new Properties();
			return emptyProperties;
		}
	}
}
