package com.ntak.deepcloner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.ntak.deepcloner.exceptions.UnsupportedCloneTypeException;
import com.ntak.deepcloner.factories.CloneRuleFactory;
import com.ntak.deepcloner.rules.primitives.IntegerCloneRule;

import static com.ntak.deepcloner.exceptions.ExceptionMessages.*;

public class DeepClonerTest {

	private DeepCloner cloner;
	private IntegerCloneRule intCloneRule = new IntegerCloneRule();
	@Mock
	private CloneRuleFactory mockFactory;
	@Mock
	private CloneRule<?> mockRule;
	
	@Before
	public void setUp() throws CloneNotSupportedException {
		MockitoAnnotations.initMocks(this);
		cloner = new DeepCloner();
		
    	doAnswer(new Answer<List<CloneRule<?>>>() {

			@Override
			public List<CloneRule<?>> answer(InvocationOnMock invocation) throws Throwable {				
				mockRule.setRuleContext((DeepCloner) invocation.getArgument(0));
				return new LinkedList<CloneRule<?>>(Arrays.asList(mockRule));
			}
    		
    	}).when(mockFactory).createRuleSet(ArgumentMatchers.any(DeepCloner.class), ArgumentMatchers.any(List.class), ArgumentMatchers.any(Map.class));
 
    	doAnswer(new Answer<CloneRule<?>>() {

			@Override
			public CloneRule<?> answer(InvocationOnMock invocation) throws Throwable {				
				return mockRule;
			}
    		
    	}).when(mockFactory).createRule(ArgumentMatchers.eq(Integer.class), ArgumentMatchers.any(List.class));
 
    	
	}
	
    @Test(expected=UnsupportedCloneTypeException.class)
    public void testDeepClonerThrowsExceptionWhenUnsupportedTypePassedIn()
    {
    	cloner.deepClone("UNSUPPORTED_TYPE_STRING");
    }
    
    @Test(expected=UnsupportedCloneTypeException.class)
    public void testDeepClonerThrowsExceptionWhenNullPassedIn()
    {
    	cloner.deepClone(null);
    }
    
    @Test
    public void testDeepClonerFailBeforeRuleLoadAndSuccessAfterLoadingSingleThreaded() {
    	Integer srcInt = new Integer(15);
    	
    	try {
    	cloner.deepClone(srcInt);
    	} catch (UnsupportedCloneTypeException e) {
    		assertTrue("Unexpected UnsupportedCloneTypeException message was found.", e.getMessage().matches(ERR_CLONE_NOT_SUP.replaceAll("%s", ".*")));
    	} catch (Exception e) {
    		fail("Unexpected exception occurred.");
    	}
    	
    	cloner.addCloneRule(intCloneRule);
    	
    	Integer clonedInteger = cloner.deepClone(srcInt);    	
    	assertEquals("The numbers are not equal in value.", srcInt, clonedInteger);
    	assertNotSame("The numbers are the same objects.", srcInt, clonedInteger);
    }
    
    // Successful multithreaded
    @Test
    public void testDeepClonerMultithreaded() throws InterruptedException {
    	long startTime= new Date().getTime();
    	
    	int noThreads = 1000;
    	int noTasks = 50000;
    	CountDownLatch latch = new CountDownLatch(noTasks);
    	BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    	ThreadPoolExecutor threadPool = new ThreadPoolExecutor(noThreads, noThreads, 20000, TimeUnit.MILLISECONDS, workQueue);
    	
    	LinkedList<Future<Integer>> responses = new LinkedList<>();
    	
    	cloner.addCloneRule(intCloneRule);
    	Integer srcInt = new Integer(15);
		
    	System.out.println(String.format("Initial parameters :: # Threads in pool = %s; # Tasks = %s", noThreads, noTasks));
    	
    	try {
				for (int i = 0; i < noTasks; i++) {
					CallCloner<Integer> task = new CallCloner<Integer>(srcInt, cloner, latch);
					responses.add(threadPool.submit(task));
				}
				
				latch.await(10000, TimeUnit.MILLISECONDS);
				long endTime= new Date().getTime();
				
				System.out.println(String.format("Execution Time: %s ms.", (endTime - startTime)));
				
				int count = 0;
				for (Future<Integer> resp : responses) {
					try {
						assertEquals("Values are not equal.", srcInt, resp.get());
						assertNotSame("Values are the same.", srcInt, resp.get());
						count++;
					} catch (ExecutionException e) {
						fail(String.format("Unexpected exception: %s\nException message: %s\n", e.getClass().getSimpleName(), e.getMessage()));
					}
				}
				assertEquals("All tasks did not execute successfully.", noTasks,count);
    	} finally {
    		threadPool.shutdown();
    	}
    }

    static class CallCloner<T> implements Callable<T> {

    	private T source;
		private DeepCloner cloner;
		private CountDownLatch latch;
    	
    	public CallCloner(T source, DeepCloner cloner, CountDownLatch latch) {
    		super();
    		this.source = source;
    		this.cloner = cloner;
    		this.latch = latch;
    	}

		@Override
		public T call() throws Exception {
			try {
				Thread.sleep(Math.round(Math.random()*100));
			} catch (InterruptedException e) {
				// Ignore timeout
			}
			T res = cloner.deepClone(source);
			latch.countDown();
			return res;
		}
    	
    };
    
    @Test(expected=UnsupportedOperationException.class)
    public void immutableDeepClonerThrowsExceptionOnAdd() {
    	DeepCloner output = new DeepCloner().genImmutableDeepCloner();
    	assertTrue(output instanceof ImmutableDeepCloner);
    	output.addCloneRule(intCloneRule);
    }
    
    @Test
    public void testAddFactoryCloneRuleSetSuccess() throws CloneNotSupportedException {
    	DeepCloner output = new DeepCloner(mockFactory);
       	List<Class<?>> klasses = new LinkedList<>();
    	klasses.add(Integer.class);
    	
    	output.addFactoryCloneRule(klasses, new HashMap<String, List<String>>());
    	
    	verify(mockFactory, times(1)).createRuleSet(ArgumentMatchers.eq(output), ArgumentMatchers.eq(klasses), ArgumentMatchers.any(Map.class));
    	verify(mockRule, times(1)).setRuleContext(ArgumentMatchers.eq(output));
    }
    
    @Test
    public void testAddFactoryCloneRuleSetFailure() throws CloneNotSupportedException {
    	doThrow(CloneNotSupportedException.class).when(mockFactory).createRuleSet(ArgumentMatchers.any(DeepCloner.class), ArgumentMatchers.any(List.class), ArgumentMatchers.any(Map.class));
    	 
    	DeepCloner output = new DeepCloner(mockFactory);
       	List<Class<?>> klasses = new LinkedList<>();
    	klasses.add(Boolean.class);
    	
    	output.addFactoryCloneRule(klasses, new HashMap<String, List<String>>());
    	
    	verify(mockFactory).createRuleSet(ArgumentMatchers.eq(output), ArgumentMatchers.eq(klasses), ArgumentMatchers.any(Map.class));
    	verify(mockRule, never()).setRuleContext(ArgumentMatchers.eq(output));
    }
    
    
    @Test
    public void testAddFactoryCloneRuleSuccess() throws CloneNotSupportedException {
    	DeepCloner output = new DeepCloner(mockFactory);
    	
    	output.addFactoryCloneRule(Integer.class, new LinkedList<String>());
    	
    	verify(mockFactory, times(1)).createRule(ArgumentMatchers.eq(Integer.class), ArgumentMatchers.any(List.class));
    	verify(mockRule, times(1)).setRuleContext(ArgumentMatchers.eq(output));
    }
    
    @Test
    public void testAddFactoryCloneRuleFailure() throws CloneNotSupportedException {
    	doThrow(CloneNotSupportedException.class).when(mockFactory).createRule(ArgumentMatchers.eq(Integer.class), ArgumentMatchers.any(List.class));
    	 
    	DeepCloner output = new DeepCloner(mockFactory);
    	
    	output.addFactoryCloneRule(Integer.class, new LinkedList<String>());
    	
    	verify(mockFactory).createRule(ArgumentMatchers.eq(Integer.class), ArgumentMatchers.any(List.class));
    	verify(mockRule, never()).setRuleContext(ArgumentMatchers.eq(output));
    }
}
