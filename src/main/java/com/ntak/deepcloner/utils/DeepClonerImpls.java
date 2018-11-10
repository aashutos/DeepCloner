/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.utils;

import com.ntak.deepcloner.DeepCloner;
import com.ntak.deepcloner.rules.collections.ImmutableArrayListCloneRule;
import com.ntak.deepcloner.rules.collections.ListCloneRule;
import com.ntak.deepcloner.rules.collections.MapCloneRule;
import com.ntak.deepcloner.rules.collections.QueueCloneRule;
import com.ntak.deepcloner.rules.collections.SetCloneRule;
import com.ntak.deepcloner.rules.primitives.BigDecimalCloneRule;
import com.ntak.deepcloner.rules.primitives.BigIntegerCloneRule;
import com.ntak.deepcloner.rules.primitives.BooleanCloneRule;
import com.ntak.deepcloner.rules.primitives.ByteCloneRule;
import com.ntak.deepcloner.rules.primitives.CharacterCloneRule;
import com.ntak.deepcloner.rules.primitives.DoubleCloneRule;
import com.ntak.deepcloner.rules.primitives.FloatCloneRule;
import com.ntak.deepcloner.rules.primitives.IntegerCloneRule;
import com.ntak.deepcloner.rules.primitives.LongCloneRule;
import com.ntak.deepcloner.rules.primitives.ShortCloneRule;
import com.ntak.deepcloner.rules.primitives.StringNonPoolCloneRule;
import com.ntak.deepcloner.rules.primitives.StringPoolCloneRule;
import com.ntak.deepcloner.rules.structures.PropertiesCloneRule;

/**
 *  Some default implementations are available for use here.
 * 
 *  @author Aashutos Kakshepati
 */
public class DeepClonerImpls {

	///// CLONE RULES /////
	
	// Clone Rules for Collections/Map types
	public static final ImmutableArrayListCloneRule<Object> R_IMM_ARRAY_LIST = new ImmutableArrayListCloneRule<>();
	public static final ListCloneRule<Object> R_LIST = new ListCloneRule<>();
	public static final MapCloneRule<Object, Object> R_MAP = new MapCloneRule<>();
	public static final QueueCloneRule<Object> R_QUEUE = new QueueCloneRule<>();
	public static final SetCloneRule<Object> R_SET = new SetCloneRule<>();
	
	// Clone Rules for Complex (Structure) types
	public static final PropertiesCloneRule R_PROPS = new PropertiesCloneRule();
	
	// Clone Rules for Primitive and core types
	public static final BigDecimalCloneRule R_BIG_D = new BigDecimalCloneRule();
	public static final BigIntegerCloneRule R_BIG_I = new BigIntegerCloneRule();
	public static final BooleanCloneRule R_BOOL = new BooleanCloneRule();
	public static final ByteCloneRule R_BYTE = new ByteCloneRule();
	public static final CharacterCloneRule R_CHAR = new CharacterCloneRule();
	public static final DoubleCloneRule R_DOUBLE = new DoubleCloneRule();
	public static final FloatCloneRule R_FLOAT = new FloatCloneRule();
	public static final IntegerCloneRule R_INT = new IntegerCloneRule();
	public static final LongCloneRule R_LONG = new LongCloneRule();
	public static final ShortCloneRule R_SHORT = new ShortCloneRule();
	public static final StringNonPoolCloneRule R_NO_POOL_STR = new StringNonPoolCloneRule();
	
	public static final StringPoolCloneRule R_POOL_STR = new StringPoolCloneRule();	
	
	///// DEEP CLONER IMPLEMENTATIONS /////
	
	public static final DeepCloner IMMUTABLE_STANDARD_CLONER = new DeepCloner().addCloneRule(R_IMM_ARRAY_LIST)
																			   .addCloneRule(R_LIST)
																			   .addCloneRule(R_MAP)
																			   .addCloneRule(R_QUEUE)
																			   .addCloneRule(R_SET)
																			   .addCloneRule(R_PROPS)
																			   .addCloneRule(R_BIG_D)
																			   .addCloneRule(R_BIG_I)
																			   .addCloneRule(R_BOOL)
																			   .addCloneRule(R_BYTE)
																			   .addCloneRule(R_CHAR)
																			   .addCloneRule(R_DOUBLE)
																			   .addCloneRule(R_FLOAT)
																			   .addCloneRule(R_INT)
																			   .addCloneRule(R_LONG)
																			   .addCloneRule(R_SHORT)
																			   .addCloneRule(R_NO_POOL_STR)
																			   .genImmutableDeepCloner();

	/**
	 * This implementation of DeepCloner does not clone String values, but reuses values found in the String Pool.
	 */
	public static final DeepCloner IMMUTABLE_PASS_STRING_CLONER = new DeepCloner().addCloneRule(R_IMM_ARRAY_LIST)
																			   .addCloneRule(R_LIST)
																			   .addCloneRule(R_MAP)
																			   .addCloneRule(R_QUEUE)
																			   .addCloneRule(R_SET)
																			   .addCloneRule(R_PROPS)
																			   .addCloneRule(R_BIG_D)
																			   .addCloneRule(R_BIG_I)
																			   .addCloneRule(R_BOOL)
																			   .addCloneRule(R_BYTE)
																			   .addCloneRule(R_CHAR)
																			   .addCloneRule(R_DOUBLE)
																			   .addCloneRule(R_FLOAT)
																			   .addCloneRule(R_INT)
																			   .addCloneRule(R_LONG)
																			   .addCloneRule(R_SHORT)
																			   .addCloneRule(R_POOL_STR)
																			   .genImmutableDeepCloner();
}