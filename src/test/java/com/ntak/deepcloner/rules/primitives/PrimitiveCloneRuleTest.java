/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.primitives;

import static org.junit.Assert.*;
import static com.ntak.deepcloner.test.TestHelper.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import com.ntak.deepcloner.DeepCloner;

/**
 *  @author Aashutos Kakshepati
 */
public class PrimitiveCloneRuleTest {
	
	protected DeepCloner context;
	
	private BigDecimalCloneRule bigDRule = new BigDecimalCloneRule();
	private BigIntegerCloneRule bigIRule = new BigIntegerCloneRule();
	private BooleanCloneRule boolRule = new BooleanCloneRule();
	private ByteCloneRule byteRule = new ByteCloneRule();
	private CharacterCloneRule charRule = new CharacterCloneRule();
	private DoubleCloneRule doubleRule = new DoubleCloneRule();
	private FloatCloneRule floatRule = new FloatCloneRule();
	private IntegerCloneRule intRule = new IntegerCloneRule();
	private LongCloneRule longRule = new LongCloneRule();
	private ShortCloneRule shortRule = new ShortCloneRule();
	private CloneableCloneRule clnbleRule = new CloneableCloneRule();
	private StringPoolCloneRule strPoolRule = new StringPoolCloneRule();	
	
	private StringNonPoolCloneRule strNPoolRule = new StringNonPoolCloneRule();
	
	@Before
	public void setUp() throws Exception {
		setUpContext();
	}

	public void setUpContext() {
		context = new DeepCloner();
		context.addCloneRule(bigDRule)
			   .addCloneRule(bigIRule)
			   .addCloneRule(boolRule)
			   .addCloneRule(byteRule)
			   .addCloneRule(charRule)
			   .addCloneRule(doubleRule)
			   .addCloneRule(floatRule)
			   .addCloneRule(intRule)
			   .addCloneRule(longRule)
			   .addCloneRule(shortRule)
			   .addCloneRule(clnbleRule);
	}

	@Test
	public void cloneBigDecimal() {
		BigDecimal bigDec = BigDecimal.ONE;
		BigDecimal cloneBigDec = context.deepClone(bigDec);
		
		isPerfectPrimitiveClone(bigDec, cloneBigDec);
	}
	
	@Test
	public void cloneBigInteger() {
		BigInteger bigInt = BigInteger.ONE;
		BigInteger cloneBigInt = context.deepClone(bigInt);
		
		isPerfectPrimitiveClone(bigInt, cloneBigInt);
	}
	
	@Test
	public void cloneBoolean() {
		Boolean bool = false;
		Boolean cloneBool = context.deepClone(bool);
		
		isPerfectPrimitiveClone(bool, cloneBool);
	}
	
	@Test
	public void cloneByte() {
		Byte byt = 'a';
		Byte cloneByt = context.deepClone(byt);
		
		isPerfectPrimitiveClone(byt, cloneByt);
	}
	
	@Test
	public void cloneCharacter() {
		Character chr = 'a';
		Character cloneChr = context.deepClone(chr);
		
		isPerfectPrimitiveClone(chr, cloneChr);
	}
	
	@Test
	public void cloneDouble() {
		Double dbl = 2.0;
		Double cloneDbl = context.deepClone(dbl);
		
		isPerfectPrimitiveClone(dbl, cloneDbl);
	}
	
	@Test
	public void cloneFloat() {
		Float flt = 2.0f;
		Float cloneFlt = context.deepClone(flt);
		
		isPerfectPrimitiveClone(flt, cloneFlt);
	}
	
	@Test
	public void cloneInt() {
		Integer intgr = 2;
		Integer cloneIntgr = context.deepClone(intgr);
		
		isPerfectPrimitiveClone(intgr, cloneIntgr);
	}
	
	@Test
	public void cloneLong() {
		Long lng = 2L;
		Long cloneLng = context.deepClone(lng);
		
		isPerfectPrimitiveClone(lng, cloneLng);
	}
	
	@Test
	public void cloneShort() {
		Short shrt = 2;
		Short cloneShort = context.deepClone(shrt);
		
		isPerfectPrimitiveClone(shrt, cloneShort);
	}
	
	private static class CloneablePOJO implements Cloneable {
		
		private String property;
		
		public CloneablePOJO(String property) {
			this.property = property;
		}
		
		@Override
		protected Object clone() {
			try {
			return new CloneablePOJO(new String(this.property));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		public boolean equals(Object other) {
			if (other instanceof CloneablePOJO) {
				if (other != null && ((CloneablePOJO) other).getProperty().equals(property)) {
					return true;
				}
			}
			return false;
		}
		
		public String getProperty() {
			return property;
		}
		
	}
	
	@Test
	public void cloneCloneable() {
		setUpContext();
		
		Cloneable clSrc = new CloneablePOJO("lalala");
		Cloneable clDup = context.deepClone(clSrc);
		isPerfectPrimitiveClone(clSrc, clDup);
		isPerfectPrimitiveClone(((CloneablePOJO)clSrc).getProperty(), ((CloneablePOJO)clDup).getProperty());
	}
	
	@Test
	public void cloneString() {
		context = new DeepCloner();
		context.addCloneRule(strNPoolRule);
		
		String string = "Same String!";
		String cloneString = context.deepClone(string);
		
		isPerfectPrimitiveClone(string, cloneString);
	}
	
	@Test
	public void cloneNonPoolString() {
		context = new DeepCloner();
		context.addCloneRule(strPoolRule);
		
		String string = "Same String!";
		String cloneString = context.deepClone(string);
		
		assertEquals("The values are not equal.", string, cloneString);
		assertSame("The values are not the same.", string, cloneString);
	}	
	
	@Test
	public void cloneWithHint() {
		int input = 123;
		int clone = context.deepClone(input, Integer.class);
		
		assertEquals("The values are not equal.", input, clone);
		assertSame("The values are not the same.", input, clone);
	}
}
