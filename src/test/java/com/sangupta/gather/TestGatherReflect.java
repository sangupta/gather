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

public class TestGatherReflect {

	
	@Test
	public void testGetField() {
		Assert.assertNull(GatherReflect.getField(null, "intField"));
		Assert.assertNull(GatherReflect.getField(null, null));
		Assert.assertNull(GatherReflect.getField(null, ""));
		
		Assert.assertNull(GatherReflect.getField(new Object(), "intField"));
		Assert.assertNull(GatherReflect.getField(new Object(), null));
		Assert.assertNull(GatherReflect.getField(new Object(), ""));

		Assert.assertNotNull(GatherReflect.getField(new TestClassA(), "intField"));
		Assert.assertNull(GatherReflect.getField(new TestClassA(), "longField"));
		
		Assert.assertNotNull(GatherReflect.getField(new TestClassB(), "intField"));
		Assert.assertNotNull(GatherReflect.getField(new TestClassB(), "longField"));
	}

	@Test
	public void testGetAllFields() {
		Assert.assertNull(GatherReflect.getAllFields(null));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPopulateAllFields() {
		GatherReflect.populateAllFields(null, null);
		GatherReflect.populateAllFields(Object.class, null);
	}
	
	private static class TestClassA {
		
		private int intField;
		
	}
	
	private static class TestClassB extends TestClassA {
		
		private int longField;
		
	}
}
