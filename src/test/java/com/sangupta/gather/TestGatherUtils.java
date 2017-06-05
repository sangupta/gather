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
	public void testCharArray() {
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
	public void testBooleanArray() {
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
}
