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
		Assert.assertFalse(GatherUtils.contains((byte[]) null, null));
		Assert.assertFalse(GatherUtils.contains((byte[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new byte[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new byte[] {}, new Object()));
	}
	
	@Test
	public void testContainsShortArray() {
		Assert.assertFalse(GatherUtils.contains((short[]) null, null));
		Assert.assertFalse(GatherUtils.contains((short[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new short[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new short[] {}, new Object()));
	}
	
	@Test
	public void testContainsIntegerArray() {
		Assert.assertFalse(GatherUtils.contains((int[]) null, null));
		Assert.assertFalse(GatherUtils.contains((int[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new int[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new int[] {}, new Object()));
	}
	
	@Test
	public void testContainsLongArray() {
		Assert.assertFalse(GatherUtils.contains((long[]) null, null));
		Assert.assertFalse(GatherUtils.contains((long[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new long[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new long[] {}, new Object()));
	}
	
	@Test
	public void testContainsFloatArray() {
		Assert.assertFalse(GatherUtils.contains((float[]) null, null));
		Assert.assertFalse(GatherUtils.contains((float[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new float[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new float[] {}, new Object()));
	}
	
	@Test
	public void testContainsDoubleArray() {
		Assert.assertFalse(GatherUtils.contains((double[]) null, null));
		Assert.assertFalse(GatherUtils.contains((double[]) null, new Object()));
		Assert.assertFalse(GatherUtils.contains(new double[] {}, null));
		Assert.assertFalse(GatherUtils.contains(new double[] {}, new Object()));
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
		
		Assert.assertTrue(GatherUtils.regexMatch("hello world", "^[^\\d].*"));
		Assert.assertFalse(GatherUtils.regexMatch("1hello world", "^[^\\d].*"));
	}
}