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
 * Unit tests for {@link GatherExecutor}.
 * 
 * @author sangupta
 *
 */
public class TestGatherExecutor {

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
}
