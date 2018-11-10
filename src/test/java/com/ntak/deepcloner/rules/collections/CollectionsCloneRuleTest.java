/*
 *  (c) copyright 2018 92AK. All Rights Reserved.
 */
package com.ntak.deepcloner.rules.collections;

import static com.ntak.deepcloner.test.TestHelper.isPerfectCollectionClone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ntak.deepcloner.DeepCloner;
import com.ntak.deepcloner.rules.primitives.BigDecimalCloneRule;
import com.ntak.deepcloner.rules.primitives.BooleanCloneRule;
import com.ntak.deepcloner.rules.primitives.DoubleCloneRule;
import com.ntak.deepcloner.rules.primitives.IntegerCloneRule;
import com.ntak.deepcloner.rules.primitives.StringNonPoolCloneRule;

/**
 *  @author Aashutos Kakshepati
 */
public class CollectionsCloneRuleTest {

	protected DeepCloner context;
	
	private ImmutableArrayListCloneRule<Object> immlistRule = new ImmutableArrayListCloneRule<>();
	private ListCloneRule<Object> listRule = new ListCloneRule<>();
	private MapCloneRule<Object, Object> mapRule = new MapCloneRule<>();
	private QueueCloneRule<Object> queueRule = new QueueCloneRule<>();
	private SetCloneRule<Object> setRule = new SetCloneRule<>();
	
	private StringNonPoolCloneRule stringRule = new StringNonPoolCloneRule();
	private IntegerCloneRule intRule = new IntegerCloneRule();
	private DoubleCloneRule doubleRule = new DoubleCloneRule();
	private BooleanCloneRule boolRule = new BooleanCloneRule();
	private BigDecimalCloneRule bigDRule = new BigDecimalCloneRule();
	
	@Mock
	private Queue<Object> mockQ;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		context = new DeepCloner();
		
		// Order matters! There are order guarantees on the clone rule collection.
		context.addCloneRule(immlistRule)
			   .addCloneRule(listRule)
			   .addCloneRule(mapRule)
			   .addCloneRule(queueRule)
			   .addCloneRule(setRule)
			   .addCloneRule(stringRule)
			   .addCloneRule(intRule)
			   .addCloneRule(doubleRule)
			   .addCloneRule(boolRule)
			   .addCloneRule(bigDRule);
	}

	@Test
	public void testImmutableArrayListClone() {
		List<Integer> integerList = Arrays.asList(1,2,3,4,5);
		List<Integer> cloneList = context.deepClone(integerList);
		// Need to deal with immutable ArrayList implementation -> Issue occurs		
		isPerfectCollectionClone(integerList, cloneList);
	}
 
    
    @Test
    public void nullImmutableListCloneReturnsInvalidList() {
    	List<Object> cloneList = context.deepClone(Arrays.asList((Object)null));
    	assertNotNull("Collection is null.", cloneList);
    	assertEquals("Collection not empty.",0,cloneList.size());
    }
	
    // Composite successful
    @Test
    public void testListClone() {
    	int size = 1000;
    	List<Integer> srcList = new LinkedList<>();
    	for (int i = 0; i < size; i++) {
    		srcList.add(i);
    	}
    	
    	List<Integer> cloneList = context.deepClone(srcList);
    	
    	isPerfectCollectionClone(srcList, cloneList);
    }
    
    @Test
    public void nullListCloneReturnsEmptyList() {
    	List<String> cloneList = context.deepClone(Collections.unmodifiableList(Arrays.asList("")));
    	assertNotNull("Collection is null.", cloneList);
    	assertEquals("Collection not empty.",0,cloneList.size());
    }

    @Test
    public void testMapClone() {
    	Map<String,Boolean> srcMap = new HashMap<>();
    	srcMap.put("KEY", true);
    	srcMap.put("KEY_2", false);
    	srcMap.put("KEY_3", false);
    	
    	Map<String,Boolean> cloneMap = context.deepClone(srcMap);
    	isPerfectCollectionClone(srcMap.entrySet(), cloneMap.entrySet());
    }
    
    @Test
    public void nullMapCloneReturnsEmptyMap() {
    	Map<String,String> cloneMap = context.deepClone(Collections.unmodifiableMap(new HashMap<String,String>()));
    	assertNotNull("Collection is null.", cloneMap);
    	assertEquals("Collection not empty.",0,cloneMap.size());
    }
    
    @Test
    public void testQueueClone() {
    	Queue<Double> srcQ = new ArrayDeque<>();
    	srcQ.add(0.1);
    	srcQ.add(0.3);
    	srcQ.add(0.6);
    	srcQ.add(0.9);
    	srcQ.add(1000.1);
    	srcQ.add(55.0);
    	
    	Queue<Double> cloneQ = context.deepClone(srcQ);
    	isPerfectCollectionClone(srcQ, cloneQ);
    }
    
    @Test
    public void nullQueueCloneReturnsEmptyQueue() {
    	// Cannot call constructor on Mock instance of Queue (Internal constructor)
    	Queue<Object> cloneQ = context.deepClone(mockQ);
    	assertNotNull("Collection is null.", cloneQ);
    	assertEquals("Collection not empty.",0,cloneQ.size());
    }
 
    @Test
    public void testSetClone() {
    	Set<BigDecimal> srcSet = new HashSet<>();
    	srcSet.add(BigDecimal.ONE);
    	srcSet.add(BigDecimal.TEN);
    	srcSet.add(BigDecimal.ZERO);
    	
    	Set<BigDecimal> cloneSet = context.deepClone(srcSet);
    	isPerfectCollectionClone(srcSet, cloneSet);
    }
    
    @Test
    public void nullSetCloneReturnsEmptySet() {
    	Set<Object> cloneSet = context.deepClone(Collections.unmodifiableSet(Collections.emptySet()));
    	assertNotNull("Collection is null.", cloneSet);
    	assertEquals("Collection not empty.",0,cloneSet.size());
    }	
}
