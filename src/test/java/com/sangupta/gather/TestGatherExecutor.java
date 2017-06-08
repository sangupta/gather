/**
 *
 * gather: SQL queries for Java collections
 * Copyright (c) 2017, Sandeep Gupta
 *
 * https://sangupta.com/projects/gather
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.sangupta.gather;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.gather.GatherAggregator.CountingAggregator;

/**
 * Unit tests for {@link GatherExecutor}.
 * 
 * @author sangupta
 *
 */
public class TestGatherExecutor {
	
	@Test
	public void testAggregate() {
		Assert.assertNull(GatherExecutor.aggregate((Collection<?>) null, null, null));
		Assert.assertNull(GatherExecutor.aggregate((Object[]) null, null, null));
		
		Assert.assertNull(GatherExecutor.aggregate(new ArrayList<>(), null, null));
		Assert.assertNull(GatherExecutor.aggregate(new Object[] {}, null, null));
		
		Assert.assertNull(GatherExecutor.aggregate(new ArrayList<>(), "age", null));
		Assert.assertNull(GatherExecutor.aggregate(new Object[] {}, "age", null));
		
		Assert.assertNull(GatherExecutor.aggregate(new ArrayList<>(), "age", new CountingAggregator()));
		Assert.assertNull(GatherExecutor.aggregate(new Object[] {}, "age", new CountingAggregator()));
	}

	@Test
	public void testHandleEquals() {
		Assert.assertFalse(GatherExecutor.handleEquals(null, null));
		Assert.assertFalse(GatherExecutor.handleEquals(new Object(), null));
		Assert.assertFalse(GatherExecutor.handleEquals(null, new Object()));
		
		Object instance = new Object();
		Assert.assertFalse(GatherExecutor.handleEquals(instance, new Object()));
		Assert.assertTrue(GatherExecutor.handleEquals(instance, instance));
		
		Assert.assertTrue(GatherExecutor.handleEquals("hello", "hello"));
		Assert.assertTrue(GatherExecutor.handleEquals("hello", new String("hello")));
		Assert.assertTrue(GatherExecutor.handleEquals("hello", "hello".intern()));
		
		Assert.assertTrue(GatherExecutor.handleEquals(123l, new Long(123)));
		Assert.assertTrue(GatherExecutor.handleEquals(123, new Integer(123)));
	}
	
	@Test
	public void testHandleEqualsIgnoreCase() {
		Assert.assertFalse(GatherExecutor.handleEqualsIgnoreCase(null, null));
		Assert.assertFalse(GatherExecutor.handleEqualsIgnoreCase(new Object(), null));
		Assert.assertFalse(GatherExecutor.handleEqualsIgnoreCase(null, new Object()));
		
		Object instance = new Object();
		Assert.assertFalse(GatherExecutor.handleEqualsIgnoreCase(instance, new Object()));
		Assert.assertTrue(GatherExecutor.handleEqualsIgnoreCase(instance, instance));
		
		Assert.assertTrue(GatherExecutor.handleEqualsIgnoreCase("hello", "HELLO"));
		Assert.assertTrue(GatherExecutor.handleEqualsIgnoreCase("hello", new String("HELLO")));
		Assert.assertTrue(GatherExecutor.handleEqualsIgnoreCase("hello", "heLLo".intern()));
		
		Assert.assertTrue(GatherExecutor.handleEqualsIgnoreCase(123l, new Long(123)));
		Assert.assertTrue(GatherExecutor.handleEqualsIgnoreCase(123, new Integer(123)));
	}
	
	@Test
	public void testHandleNull() {
		Assert.assertTrue(GatherExecutor.handleNull(null, new Object()));
		Assert.assertFalse(GatherExecutor.handleNull(new Object(), new Object()));

		Assert.assertTrue(GatherExecutor.handleNull(null, null));
		Assert.assertFalse(GatherExecutor.handleNull(new Object(), null));
	}
	
	@Test
	public void testHandleValueIn() {
		Assert.assertFalse(GatherExecutor.handleValueIn(null, null));
		Assert.assertFalse(GatherExecutor.handleValueIn(new Object(), null));
		
		List<String> list = new ArrayList<>();
		Assert.assertFalse(GatherExecutor.handleValueIn(list, null));
		Assert.assertFalse(GatherExecutor.handleValueIn(list, "hello"));
		Assert.assertFalse(GatherExecutor.handleValueIn(null, list));
		Assert.assertFalse(GatherExecutor.handleValueIn("hello", list));
		
		list.add("hello");
		list.add("world");
		list.add("life");
		list.add("is");
		list.add("sunny");
		Assert.assertFalse(GatherExecutor.handleValueIn("hello world", list));
		Assert.assertTrue(GatherExecutor.handleValueIn("hello", list));
		Assert.assertTrue(GatherExecutor.handleValueIn("world", list));
		Assert.assertTrue(GatherExecutor.handleValueIn("life", list));
		Assert.assertTrue(GatherExecutor.handleValueIn("is", list));
		Assert.assertTrue(GatherExecutor.handleValueIn("sunny", list));
		
		Integer[] array = new Integer[] { 2, 3, 5, 7, 9, 11, 13, 17, 19 };
		Assert.assertFalse(GatherExecutor.handleValueIn(0, array));
		Assert.assertFalse(GatherExecutor.handleValueIn(1, array));
		Assert.assertFalse(GatherExecutor.handleValueIn(4, array));
		
		Assert.assertTrue(GatherExecutor.handleValueIn(2, array));
		Assert.assertTrue(GatherExecutor.handleValueIn(13, array));
		Assert.assertTrue(GatherExecutor.handleValueIn(19, array));
	}
	
	@Test
	public void testHandleCollectionHasValue() {
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(null, null));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new Object(), null));
		
		List<String> list = new ArrayList<>();
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(list, null));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(list, "hello"));
		
		list.add("hello");
		list.add("world");
		list.add("life");
		list.add("is");
		list.add("sunny");
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(list, "hello world"));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(list, "hello"));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(list, "world"));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(list, "life"));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(list, "is"));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(list, "sunny"));
		
		// object array
		
		Integer[] array = new Integer[] { 2, 3, 5, 7, 9, 11, 13, 17, 19 };
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(array, 0));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(array, 1));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(array, 4));
		
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(array, 2));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(array, 13));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(array, 19));
		
		// char array
		
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new char[] { 'a', 'b', 'c' }, 'a'));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new char[] { 'a', 'b', 'c' }, 'b'));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new char[] { 'a', 'b', 'c' }, 'c'));
		
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new char[] { 'a', 'b', 'c' }, 'A'));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new char[] { 'a', 'b', 'c' }, 'B'));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new char[] { 'a', 'b', 'c' }, 'C'));
		
		// boolean array
		
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new boolean[] { false, false }, false));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new boolean[] { false, false }, true));
		
		// byte array
		
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new byte[] { 12, 14 }, 12));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new byte[] { 12, 14 }, 14));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new byte[] { 12, 14  }, 11));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new byte[] { 12, 14 }, 13));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new byte[] { 12, 14 }, 15));
		
		// short array
		
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new short[] { 12, 14 }, 12));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new short[] { 12, 14 }, 14));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new short[] { 12, 14  }, 11));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new short[] { 12, 14 }, 13));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new short[] { 12, 14 }, 15));
		
		// int array
		
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new int[] { 12, 14 }, 12));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new int[] { 12, 14 }, 14));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new int[] { 12, 14  }, 11));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new int[] { 12, 14 }, 13));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new int[] { 12, 14 }, 15));
		
		// long array
		
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new long[] { 12, 14 }, 12));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new long[] { 12, 14 }, 14));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new long[] { 12, 14  }, 11));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new long[] { 12, 14 }, 13));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new long[] { 12, 14 }, 15));
		
		// float array
		
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new float[] { 12, 14 }, 12));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new float[] { 12, 14 }, 14));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new float[] { 12, 14  }, 11));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new float[] { 12, 14 }, 13));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new float[] { 12, 14 }, 15));
		
		// double array
		
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new double[] { 12, 14 }, 12));
		Assert.assertTrue(GatherExecutor.handleCollectionHasValue(new double[] { 12, 14 }, 14));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new double[] { 12, 14  }, 11));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new double[] { 12, 14 }, 13));
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new double[] { 12, 14 }, 15));
		
		// unknown
		
		Assert.assertFalse(GatherExecutor.handleCollectionHasValue(new double[] { 12, 14 }, true));
	}
	
	@Test
	public void testHandleWildcardMatch() {
		Assert.assertFalse(GatherExecutor.handleWildcardMatch(null, null));
		Assert.assertFalse(GatherExecutor.handleWildcardMatch(null, "*"));
		Assert.assertFalse(GatherExecutor.handleWildcardMatch("abc", null));
		
		Assert.assertFalse(GatherExecutor.handleWildcardMatch("one more", ""));
		
		Assert.assertTrue(GatherExecutor.handleWildcardMatch("abc.wav", "*"));
		Assert.assertTrue(GatherExecutor.handleWildcardMatch("abc.wav", "*.w?v"));
		Assert.assertTrue(GatherExecutor.handleWildcardMatch("abc.wav", "*b?.wav"));
		Assert.assertTrue(GatherExecutor.handleWildcardMatch("abc.wav", "*.wav"));
		Assert.assertTrue(GatherExecutor.handleWildcardMatch("abc.wav", "*abc.wav"));
		Assert.assertTrue(GatherExecutor.handleWildcardMatch("abc.wav", "???.wav"));
		Assert.assertTrue(GatherExecutor.handleWildcardMatch("abc.wav", "???.???"));
		Assert.assertTrue(GatherExecutor.handleWildcardMatch("abc.wav", "???.???"));

		Assert.assertFalse(GatherExecutor.handleWildcardMatch("abc.wav", "*.html"));
		Assert.assertFalse(GatherExecutor.handleWildcardMatch("abc.wav", "?.wav"));
		Assert.assertFalse(GatherExecutor.handleWildcardMatch("abc.wav", "??.wav"));
		Assert.assertFalse(GatherExecutor.handleWildcardMatch("abc.wav", "abc.wi?"));

		Assert.assertTrue(GatherExecutor.handleWildcardMatch("http://sangupta.com/tech/page10/index.html", "*tech/page*"));
	}
	
	@Test
	public void testHandleCollectionHasAllOrAnyValues() {
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(null, null, true));
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(null, null, false));
		
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new int[] {}, null, true));
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new int[] {}, null, false));
		
		// char array
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new char[] { 'a', 'b', 'c' }, new char[] { 'a', 'b' }, true));
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new char[] { 'a', 'b', 'c' }, new char[] { 'a', 'd' }, false));
		
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new char[] { 'a', 'b', 'c' }, new char[] { 'a', 'd' }, true));
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new char[] { 'a', 'b', 'c' }, new char[] { 'd', 'e' }, false));
		
		// boolean array
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new boolean[] { true }, new boolean[] { true, true }, true));
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new boolean[] { true }, new boolean[] { true, false }, false));
		
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new boolean[] { true }, new boolean[] { false, false, true }, true));
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new boolean[] { true }, new boolean[] { false, false }, false));
		
		// byte array
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new byte[] { 2, 4, 8, 16 }, new byte[] { 4, 8, 16 }, true));
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new byte[] { 2, 4, 8, 16 }, new byte[] { 8, 16, 32 }, false));
		
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new byte[] { 2, 4, 8, 16 }, new byte[] { 4, 8, 16, 32 }, true));
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new byte[] { 2, 4, 8, 16 }, new byte[] { 32, 64, 1 }, false));
		
		// short array
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new short[] { 2, 4, 8, 16 }, new short[] { 4, 8, 16 }, true));
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new short[] { 2, 4, 8, 16 }, new short[] { 8, 16, 32 }, false));
		
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new short[] { 2, 4, 8, 16 }, new short[] { 4, 8, 16, 32 }, true));
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new short[] { 2, 4, 8, 16 }, new short[] { 32, 64, 1 }, false));
		
		// int array
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new int[] { 2, 4, 8, 16 }, new int[] { 4, 8, 16 }, true));
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new int[] { 2, 4, 8, 16 }, new int[] { 8, 16, 32 }, false));
		
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new int[] { 2, 4, 8, 16 }, new int[] { 4, 8, 16, 32 }, true));
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new int[] { 2, 4, 8, 16 }, new int[] { 32, 64, 1 }, false));
			
		// long array
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new long[] { 2, 4, 8, 16 }, new long[] { 4, 8, 16 }, true));
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new long[] { 2, 4, 8, 16 }, new long[] { 8, 16, 32 }, false));
		
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new long[] { 2, 4, 8, 16 }, new long[] { 4, 8, 16, 32 }, true));
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new long[] { 2, 4, 8, 16 }, new long[] { 32, 64, 1 }, false));	
		
		
		// float array
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new float[] { 2, 4, 8, 16 }, new float[] { 4, 8, 16 }, true));
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new float[] { 2, 4, 8, 16 }, new float[] { 8, 16, 32 }, false));
		
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new float[] { 2, 4, 8, 16 }, new float[] { 4, 8, 16, 32 }, true));
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new float[] { 2, 4, 8, 16 }, new float[] { 32, 64, 1 }, false));	
		
		// double array
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new double[] { 2, 4, 8, 16 }, new double[] { 4, 8, 16 }, true));
		Assert.assertTrue(GatherExecutor.handleCollectionHasAllOrAnyValues(new double[] { 2, 4, 8, 16 }, new double[] { 8, 16, 32 }, false));
		
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new double[] { 2, 4, 8, 16 }, new double[] { 4, 8, 16, 32 }, true));
		Assert.assertFalse(GatherExecutor.handleCollectionHasAllOrAnyValues(new double[] { 2, 4, 8, 16 }, new double[] { 32, 64, 1 }, false));	
	}
	
	@Test
	public void testHandleNumericComparison() {
		Assert.assertFalse(GatherExecutor.handleNumericComparison(null, 123, GatherNumericComparison.LESS_THAN));
		Assert.assertFalse(GatherExecutor.handleNumericComparison(123, null, GatherNumericComparison.LESS_THAN));
		
		Assert.assertFalse(GatherExecutor.handleNumericComparison(123, new Object(), GatherNumericComparison.LESS_THAN));
		Assert.assertTrue(GatherExecutor.handleNumericComparison('a', 'b', GatherNumericComparison.LESS_THAN));
		Assert.assertFalse(GatherExecutor.handleNumericComparison(new Object(), new Object(), GatherNumericComparison.LESS_THAN));
	}
	
}
