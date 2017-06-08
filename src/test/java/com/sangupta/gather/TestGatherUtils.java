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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link GatherUtils} class.
 * 
 * @author sangupta
 *
 */
public class TestGatherUtils {
	
	@Test
	public void testContainsCharArray() {
		Assert.assertFalse(GatherUtils.contains((char[]) null, null));
		Assert.assertFalse(GatherUtils.contains((char[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new char[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new char[] {}, new Object()));
		
		Assert.assertTrue(GatherUtils.contains(new char[] { 'a', 'b', 'c' }, 'a'));
		Assert.assertTrue(GatherUtils.contains(new char[] { 'a', 'b', 'c' }, 'b'));
		Assert.assertTrue(GatherUtils.contains(new char[] { 'a', 'b', 'c' }, 'c'));
		Assert.assertFalse(GatherUtils.contains(new char[] { 'a', 'b', 'c' }, 'x'));
		Assert.assertFalse(GatherUtils.contains(new char[] { 'a', 'b', 'c' }, 'y'));
		Assert.assertFalse(GatherUtils.contains(new char[] { 'a', 'b', 'c' }, 'z'));
		Assert.assertTrue(GatherUtils.contains(new char[] { 'a', 'b', 'c' }, new Character('a')));
		Assert.assertFalse(GatherUtils.contains(new char[] { 'a', 'b', 'c' }, new Character('x')));
	}
	
	@Test
	public void testContainsBooleanArray() {
		Assert.assertFalse(GatherUtils.contains((boolean[]) null, null));
		Assert.assertFalse(GatherUtils.contains((boolean[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new boolean[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new boolean[] {}, new Object()));
		
		Assert.assertFalse(GatherUtils.contains(new boolean[] {}, true));
		Assert.assertFalse(GatherUtils.contains(new boolean[] {}, false));
		Assert.assertTrue(GatherUtils.contains(new boolean[] { true }, true));
		Assert.assertFalse(GatherUtils.contains(new boolean[] { true }, false));
		Assert.assertTrue(GatherUtils.contains(new boolean[] { false }, false));
		Assert.assertFalse(GatherUtils.contains(new boolean[] { false }, true));
	}
	
	@Test
	public void testContainsByteArray() {
		Assert.assertFalse(GatherUtils.contains((byte[]) null, (Object) null));
		
		Assert.assertFalse(GatherUtils.contains((byte[]) null, null));
		Assert.assertFalse(GatherUtils.contains((byte[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new byte[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new byte[] {}, new Object()));
		
		Assert.assertFalse(GatherUtils.contains(new byte[] { 2, 4, 8, 16, 32 }, 1));
		Assert.assertFalse(GatherUtils.contains(new byte[] { 2, 4, 8, 16, 32 }, 3));
		Assert.assertFalse(GatherUtils.contains(new byte[] { 2, 4, 8, 16, 32 }, 9));
		Assert.assertFalse(GatherUtils.contains(new byte[] { 2, 4, 8, 16, 32 }, 27));
		
		Assert.assertTrue(GatherUtils.contains(new byte[] { 2, 4, 8, 16, 32 }, 2));
		Assert.assertTrue(GatherUtils.contains(new byte[] { 2, 4, 8, 16, 32 }, 4));
		Assert.assertTrue(GatherUtils.contains(new byte[] { 2, 4, 8, 16, 32 }, 8));
		Assert.assertTrue(GatherUtils.contains(new byte[] { 2, 4, 8, 16, 32 }, 16));
		Assert.assertTrue(GatherUtils.contains(new byte[] { 2, 4, 8, 16, 32 }, 32));
	}
	
	@Test
	public void testContainsShortArray() {
		Assert.assertFalse(GatherUtils.contains((short[]) null, (Object) null));
		
		Assert.assertFalse(GatherUtils.contains((short[]) null, null));
		Assert.assertFalse(GatherUtils.contains((short[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new short[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new short[] {}, new Object()));
		
		Assert.assertFalse(GatherUtils.contains(new short[] { 2, 4, 8, 16, 32 }, 1));
		Assert.assertFalse(GatherUtils.contains(new short[] { 2, 4, 8, 16, 32 }, 3));
		Assert.assertFalse(GatherUtils.contains(new short[] { 2, 4, 8, 16, 32 }, 9));
		Assert.assertFalse(GatherUtils.contains(new short[] { 2, 4, 8, 16, 32 }, 27));
		
		Assert.assertTrue(GatherUtils.contains(new short[] { 2, 4, 8, 16, 32 }, 2));
		Assert.assertTrue(GatherUtils.contains(new short[] { 2, 4, 8, 16, 32 }, 4));
		Assert.assertTrue(GatherUtils.contains(new short[] { 2, 4, 8, 16, 32 }, 8));
		Assert.assertTrue(GatherUtils.contains(new short[] { 2, 4, 8, 16, 32 }, 16));
		Assert.assertTrue(GatherUtils.contains(new short[] { 2, 4, 8, 16, 32 }, 32));
	}
	
	@Test
	public void testContainsIntegerArray() {
		Assert.assertFalse(GatherUtils.contains((int[]) null, (Object) null));
		
		Assert.assertFalse(GatherUtils.contains((int[]) null, null));
		Assert.assertFalse(GatherUtils.contains((int[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new int[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new int[] {}, new Object()));
		
		Assert.assertFalse(GatherUtils.contains(new int[] { 2, 4, 8, 16, 32 }, 1));
		Assert.assertFalse(GatherUtils.contains(new int[] { 2, 4, 8, 16, 32 }, 3));
		Assert.assertFalse(GatherUtils.contains(new int[] { 2, 4, 8, 16, 32 }, 9));
		Assert.assertFalse(GatherUtils.contains(new int[] { 2, 4, 8, 16, 32 }, 27));
		
		Assert.assertTrue(GatherUtils.contains(new int[] { 2, 4, 8, 16, 32 }, 2));
		Assert.assertTrue(GatherUtils.contains(new int[] { 2, 4, 8, 16, 32 }, 4));
		Assert.assertTrue(GatherUtils.contains(new int[] { 2, 4, 8, 16, 32 }, 8));
		Assert.assertTrue(GatherUtils.contains(new int[] { 2, 4, 8, 16, 32 }, 16));
		Assert.assertTrue(GatherUtils.contains(new int[] { 2, 4, 8, 16, 32 }, 32));
	}
	
	@Test
	public void testContainsLongArray() {
		Assert.assertFalse(GatherUtils.contains((long[]) null, (Object) null));
		
		Assert.assertFalse(GatherUtils.contains((long[]) null, null));
		Assert.assertFalse(GatherUtils.contains((long[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new long[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new long[] {}, new Object()));
		
		Assert.assertFalse(GatherUtils.contains(new long[] { 2, 4, 8, 16, 32 }, 1));
		Assert.assertFalse(GatherUtils.contains(new long[] { 2, 4, 8, 16, 32 }, 3));
		Assert.assertFalse(GatherUtils.contains(new long[] { 2, 4, 8, 16, 32 }, 9));
		Assert.assertFalse(GatherUtils.contains(new long[] { 2, 4, 8, 16, 32 }, 27));
		
		Assert.assertTrue(GatherUtils.contains(new long[] { 2, 4, 8, 16, 32 }, 2));
		Assert.assertTrue(GatherUtils.contains(new long[] { 2, 4, 8, 16, 32 }, 4));
		Assert.assertTrue(GatherUtils.contains(new long[] { 2, 4, 8, 16, 32 }, 8));
		Assert.assertTrue(GatherUtils.contains(new long[] { 2, 4, 8, 16, 32 }, 16));
		Assert.assertTrue(GatherUtils.contains(new long[] { 2, 4, 8, 16, 32 }, 32));
	}
	
	@Test
	public void testContainsFloatArray() {
		Assert.assertFalse(GatherUtils.contains((float[]) null, (Object) null));
		
		Assert.assertFalse(GatherUtils.contains((float[]) null, null));
		Assert.assertFalse(GatherUtils.contains((float[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new float[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new float[] {}, new Object()));
		
		Assert.assertFalse(GatherUtils.contains(new float[] { 2, 4, 8, 16, 32 }, 1));
		Assert.assertFalse(GatherUtils.contains(new float[] { 2, 4, 8, 16, 32 }, 3));
		Assert.assertFalse(GatherUtils.contains(new float[] { 2, 4, 8, 16, 32 }, 9));
		Assert.assertFalse(GatherUtils.contains(new float[] { 2, 4, 8, 16, 32 }, 27));
		
		Assert.assertTrue(GatherUtils.contains(new float[] { 2, 4, 8, 16, 32 }, 2));
		Assert.assertTrue(GatherUtils.contains(new float[] { 2, 4, 8, 16, 32 }, 4));
		Assert.assertTrue(GatherUtils.contains(new float[] { 2, 4, 8, 16, 32 }, 8));
		Assert.assertTrue(GatherUtils.contains(new float[] { 2, 4, 8, 16, 32 }, 16));
		Assert.assertTrue(GatherUtils.contains(new float[] { 2, 4, 8, 16, 32 }, 32));
	}
	
	@Test
	public void testContainsDoubleArray() {
		Assert.assertFalse(GatherUtils.contains((double[]) null, (Object) null));
		
		Assert.assertFalse(GatherUtils.contains((double[]) null, null));
		Assert.assertFalse(GatherUtils.contains((double[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new double[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new double[] {}, new Object()));
		
		Assert.assertFalse(GatherUtils.contains(new double[] { 2, 4, 8, 16, 32 }, 1));
		Assert.assertFalse(GatherUtils.contains(new double[] { 2, 4, 8, 16, 32 }, 3));
		Assert.assertFalse(GatherUtils.contains(new double[] { 2, 4, 8, 16, 32 }, 9));
		Assert.assertFalse(GatherUtils.contains(new double[] { 2, 4, 8, 16, 32 }, 27));
		
		Assert.assertTrue(GatherUtils.contains(new double[] { 2, 4, 8, 16, 32 }, 2));
		Assert.assertTrue(GatherUtils.contains(new double[] { 2, 4, 8, 16, 32 }, 4));
		Assert.assertTrue(GatherUtils.contains(new double[] { 2, 4, 8, 16, 32 }, 8));
		Assert.assertTrue(GatherUtils.contains(new double[] { 2, 4, 8, 16, 32 }, 16));
		Assert.assertTrue(GatherUtils.contains(new double[] { 2, 4, 8, 16, 32 }, 32));
	}
	
	@Test
	public void testContainsObjectArray() {
		Assert.assertFalse(GatherUtils.contains((Object[]) null, null));
		Assert.assertFalse(GatherUtils.contains((Object[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new Object[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new Object[] {}, new Object()));
		
		Assert.assertTrue(GatherUtils.contains(new Object[] { 'a', 'b', 'c' }, 'a'));
		Assert.assertTrue(GatherUtils.contains(new Object[] { 'a', 'b', 'c' }, 'b'));
		Assert.assertTrue(GatherUtils.contains(new Object[] { 'a', 'b', 'c' }, 'c'));
		Assert.assertFalse(GatherUtils.contains(new Object[] { 'a', 'b', 'c' }, 'x'));
		Assert.assertFalse(GatherUtils.contains(new Object[] { 'a', 'b', 'c' }, 'y'));
		Assert.assertFalse(GatherUtils.contains(new Object[] { 'a', 'b', 'c' }, 'z'));
		Assert.assertTrue(GatherUtils.contains(new Object[] { 'a', 'b', 'c' }, new Character('a')));
		Assert.assertFalse(GatherUtils.contains(new Object[] { 'a', 'b', 'c' }, new Character('x')));
	}

	@Test
	public void testWildcardMatch() {
		Assert.assertTrue(GatherUtils.wildcardMatch("abc.wav", "*"));
		Assert.assertTrue(GatherUtils.wildcardMatch("abc.wav", "*.w?v"));
		Assert.assertTrue(GatherUtils.wildcardMatch("abc.wav", "*b?.wav"));
		Assert.assertTrue(GatherUtils.wildcardMatch("abc.wav", "*.wav"));
		Assert.assertTrue(GatherUtils.wildcardMatch("abc.wav", "*abc.wav"));
		Assert.assertTrue(GatherUtils.wildcardMatch("abc.wav", "???.wav"));
		Assert.assertTrue(GatherUtils.wildcardMatch("abc.wav", "???.???"));
		Assert.assertTrue(GatherUtils.wildcardMatch("abc.wav", "???.???"));

		Assert.assertFalse(GatherUtils.wildcardMatch("abc.wav", "*.html"));
		Assert.assertFalse(GatherUtils.wildcardMatch("abc.wav", "?.wav"));
		Assert.assertFalse(GatherUtils.wildcardMatch("abc.wav", "??.wav"));
		Assert.assertFalse(GatherUtils.wildcardMatch("abc.wav", "abc.wi?"));

		Assert.assertTrue(GatherUtils.wildcardMatch("http://sangupta.com/tech/page10/index.html", "*tech/page*"));
	}
	
	@Test
	public void testIsNumberType() {
		Assert.assertFalse(GatherUtils.isNumberType(null));
		Assert.assertFalse(GatherUtils.isNumberType(new Object()));
		
		Assert.assertTrue(GatherUtils.isNumberType((byte) 123));
		Assert.assertTrue(GatherUtils.isNumberType(123));
		Assert.assertTrue(GatherUtils.isNumberType(123l));
		Assert.assertTrue(GatherUtils.isNumberType(123f));
		Assert.assertTrue(GatherUtils.isNumberType(123d));
		Assert.assertTrue(GatherUtils.isNumberType(new AtomicLong(123)));
		Assert.assertTrue(GatherUtils.isNumberType(new AtomicInteger(123)));
	}
	
	@Test
	public void testAsNumber() {
		Assert.assertNull(GatherUtils.asNumber(null));
		Assert.assertNull(GatherUtils.asNumber(new Object()));
		
		Assert.assertNotNull(GatherUtils.asNumber((byte) 123));
		Assert.assertNotNull(GatherUtils.asNumber(123));
		Assert.assertNotNull(GatherUtils.asNumber(123l));
		Assert.assertNotNull(GatherUtils.asNumber(123f));
		Assert.assertNotNull(GatherUtils.asNumber(123d));
		Assert.assertNotNull(GatherUtils.asNumber(new AtomicLong(123)));
		Assert.assertNotNull(GatherUtils.asNumber(new AtomicInteger(123)));
	}

	@Test
	public void testRegexMatch() {
		Assert.assertFalse(GatherUtils.regexMatch(null, (String) null));
		Assert.assertFalse(GatherUtils.regexMatch("hello world", (String) null));
		
		Assert.assertFalse(GatherUtils.regexMatch(null, (Pattern) null));
		Assert.assertFalse(GatherUtils.regexMatch("hello world", (Pattern) null));
		Assert.assertFalse(GatherUtils.regexMatch(null, "\\d"));
		Assert.assertFalse(GatherUtils.regexMatch(null, Pattern.compile("\\d")));
		
		Assert.assertTrue(GatherUtils.regexMatch("hello world", "^[^\\d].*"));
		Assert.assertFalse(GatherUtils.regexMatch("1hello world", "^[^\\d].*"));
	}
	
	@Test
	public void testContainsAllOrAnyBooleanArray() {
		// basic checks
		Assert.assertFalse(GatherUtils.containsAllOrAny((boolean[]) null, new boolean[] { false }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny((boolean[]) null, new boolean[] { false }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { }, new boolean[] { false }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { }, new boolean[] { false }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { true }, null, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { true }, null, false));
				
		// all clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new boolean[] { false, false }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new boolean[] { false, true }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new Boolean[] { false, false }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new Boolean[] { false, true }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new boolean[] { false, false }, Arrays.asList(new Boolean[] { false, false }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { false, false }, Arrays.asList(new Boolean[] { false, true }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { false, false }, Arrays.asList(new Object[] { new Object() }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { false, false }, Arrays.asList(new Object[] { new Object() }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new Object(), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new Object(), true));
		
		// any clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new boolean[] { false, false }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new boolean[] { false, true }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new Boolean[] { false, false }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new Boolean[] { false, true }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new boolean[] { false, false }, Arrays.asList(new Boolean[] { false, false }), false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new boolean[] { false, false }, Arrays.asList(new Boolean[] { false, true }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { false, false }, Arrays.asList(new Object[] { new Object() }), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { false, false }, Arrays.asList(new Object[] { new Object() }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new Object(), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new boolean[] { false, false }, new Object(), false));
	}
	
	@Test
	public void testContainsAllOrAnyCharacterArray() {
		// basic checks
		Assert.assertFalse(GatherUtils.containsAllOrAny((char[]) null, new char[] { 'a' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny((char[]) null, new char[] { 'a' }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { }, new char[] { 'a' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { }, new char[] { 'a' }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, null, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, null, false));
				
		// all clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new char[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new char[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new Character[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new Character[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, Arrays.asList(new Character[] { 'a', 'b', 'c' }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, Arrays.asList(new Character[] { 'b', 'd' }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new Object(), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new Object(), true));
		
		// any clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new char[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new char[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new Character[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new Character[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, Arrays.asList(new Character[] { 'a', 'b', 'c' }), false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, Arrays.asList(new Character[] { 'b', 'd' }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new Object(), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new char[] { 'a', 'b', 'c' }, new Object(), false));
	}
	
	@Test
	public void testContainsAllOrAnyByteArray() {
		// basic checks
		Assert.assertFalse(GatherUtils.containsAllOrAny((byte[]) null, new byte[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny((byte[]) null, new byte[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { }, new byte[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { }, new byte[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, null, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, null, false));
				
		// all clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new byte[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new byte[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new Byte[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new Byte[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, Arrays.asList(new Byte[] { 'a', 'b', 'c' }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, Arrays.asList(new Byte[] { 'b', 'd' }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new Object(), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new Object(), true));
		
		// any clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new byte[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new byte[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new Byte[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new Byte[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, Arrays.asList(new Byte[] { 'a', 'b', 'c' }), false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, Arrays.asList(new Byte[] { 'b', 'd' }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new Object(), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new byte[] { 'a', 'b', 'c' }, new Object(), false));
	}
	
	@Test
	public void testContainsAllOrAnyShortArray() {
		// basic checks
		Assert.assertFalse(GatherUtils.containsAllOrAny((short[]) null, new short[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny((short[]) null, new short[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { }, new short[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { }, new short[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, null, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, null, false));
				
		// all clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new short[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new short[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new Short[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new Short[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'a', 'b', 'c' }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'b', 'd' }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new Object(), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new Object(), true));
		
		// any clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new short[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new short[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new Short[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new Short[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'a', 'b', 'c' }), false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'b', 'd' }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new Object(), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new short[] { 'a', 'b', 'c' }, new Object(), false));
	}
	
	@Test
	public void testContainsAllOrAnyIntArray() {
		// basic checks
		Assert.assertFalse(GatherUtils.containsAllOrAny((int[]) null, new int[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny((int[]) null, new int[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { }, new int[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { }, new int[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, null, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, null, false));
				
		// all clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new int[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new int[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new Short[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new Short[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'a', 'b', 'c' }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'b', 'd' }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new Object(), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new Object(), true));
		
		// any clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new int[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new int[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new Short[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new Short[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'a', 'b', 'c' }), false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'b', 'd' }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new Object(), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new int[] { 'a', 'b', 'c' }, new Object(), false));
	}
	
	@Test
	public void testContainsAllOrAnyLongArray() {
		// basic checks
		Assert.assertFalse(GatherUtils.containsAllOrAny((long[]) null, new long[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny((long[]) null, new long[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { }, new long[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { }, new long[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, null, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, null, false));
				
		// all clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new long[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new long[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new Short[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new Short[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'a', 'b', 'c' }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'b', 'd' }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new Object(), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new Object(), true));
		
		// any clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new long[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new long[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new Short[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new Short[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'a', 'b', 'c' }), false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'b', 'd' }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new Object(), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new long[] { 'a', 'b', 'c' }, new Object(), false));
	}
	
	@Test
	public void testContainsAllOrAnyFloatArray() {
		// basic checks
		Assert.assertFalse(GatherUtils.containsAllOrAny((float[]) null, new float[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny((float[]) null, new float[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { }, new float[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { }, new float[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, null, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, null, false));
				
		// all clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new float[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new float[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new Short[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new Short[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'a', 'b', 'c' }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'b', 'd' }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new Object(), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new Object(), true));
		
		// any clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new float[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new float[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new Short[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new Short[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'a', 'b', 'c' }), false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'b', 'd' }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new Object(), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new float[] { 'a', 'b', 'c' }, new Object(), false));
	}
	
	@Test
	public void testContainsAllOrAnyDoubleArray() {
		// basic checks
		Assert.assertFalse(GatherUtils.containsAllOrAny((double[]) null, new double[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny((double[]) null, new double[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { }, new double[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { }, new double[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, null, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, null, false));
				
		// all clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new double[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new double[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new Short[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new Short[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'a', 'b', 'c' }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'b', 'd' }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new Object(), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new Object(), true));
		
		// any clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new double[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new double[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new Short[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new Short[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'a', 'b', 'c' }), false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, Arrays.asList(new Short[] { 'b', 'd' }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new Object(), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new double[] { 'a', 'b', 'c' }, new Object(), false));
	}
	
	@Test
	public void testContainsAllOrAnyObjectArray() {
		// basic checks
		Assert.assertFalse(GatherUtils.containsAllOrAny((Object[]) null, new double[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny((Object[]) null, new double[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { }, new double[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { }, new double[] { 12 }, false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, null, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, null, false));
				
		// all clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { 'a', 'b', 'c' }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { 'b', 'd' }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object(), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object(), true));
		
		// any clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { 'a', 'b', 'c' }), false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { 'b', 'd' }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, Arrays.asList(new Object[] { new Object() }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object(), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(new Object[] { 'a', 'b', 'c' }, new Object(), false));
	}
	
	@Test
	public void testContainsAllOrAnyObjectCollection() {
		// basic checks
		Assert.assertFalse(GatherUtils.containsAllOrAny((List<Object>) null, new double[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny((List<Object>) null, new double[] { 12 }, false));
		
		List<Character> list = new ArrayList<>();
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, new double[] { 12 }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, new double[] { 12 }, false));
		
		list.add('a');
		list.add('b');
		list.add('c');
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, null, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, null, false));
				
		// all clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(list, new Object[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, new Object[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(list, new Object[] { 'a', 'b', 'c' }, true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, new Object[] { 'b', 'd' }, true));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(list, Arrays.asList(new Object[] { 'a', 'b', 'c' }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, Arrays.asList(new Object[] { 'b', 'd' }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, Arrays.asList(new Object[] { new Object() }), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, Arrays.asList(new Object[] { new Object() }), true));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, new Object(), true));
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, new Object(), true));
		
		// any clause
		Assert.assertTrue(GatherUtils.containsAllOrAny(list, new Object[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(list, new Object[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(list, new Object[] { 'a', 'b', 'c' }, false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(list, new Object[] { 'b', 'd' }, false));
		
		Assert.assertTrue(GatherUtils.containsAllOrAny(list, Arrays.asList(new Object[] { 'a', 'b', 'c' }), false));
		Assert.assertTrue(GatherUtils.containsAllOrAny(list, Arrays.asList(new Object[] { 'b', 'd' }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, Arrays.asList(new Object[] { new Object() }), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, Arrays.asList(new Object[] { new Object() }), false));
		
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, new Object(), false));
		Assert.assertFalse(GatherUtils.containsAllOrAny(list, new Object(), false));
	}
}
