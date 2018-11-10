/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ntak.deepcloner.rules.collections.CollectionsCloneRuleTest;
import com.ntak.deepcloner.rules.primitives.PrimitiveCloneRuleTest;
import com.ntak.deepcloner.rules.structures.StructureCloneRuleTest;
import com.ntak.deepcloner.utils.DeepClonerImplsSuite;

/**
 *  @author Aashutos Kakshepati
 */
@RunWith(Suite.class)
@SuiteClasses({ 
				DeepClonerTest.class,
				CollectionsCloneRuleTest.class,
				PrimitiveCloneRuleTest.class,
				StructureCloneRuleTest.class,
				DeepClonerImplsSuite.class
})
public class RegressionTestSuite {

}
