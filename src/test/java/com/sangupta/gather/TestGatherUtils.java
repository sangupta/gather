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
	}

}
