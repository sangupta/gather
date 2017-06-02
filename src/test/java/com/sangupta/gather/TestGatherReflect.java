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
