package com.sangupta.gather;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.gather.GatherAggregator.UniqueAggregator;

public class TestGatherAggregator {
	
	@Test
	public void testUniqueAggregator() {
		UniqueAggregator aggregator = new UniqueAggregator();
		
		Object instance = new Object();
		Assert.assertEquals(0, aggregator.getResult(0));
		
		aggregator.aggregate(0, instance);
		Assert.assertEquals(1, aggregator.getResult(0));
		
		aggregator.aggregate(0, instance);
		aggregator.aggregate(0, instance);
		aggregator.aggregate(0, instance);
		Assert.assertEquals(1, aggregator.getResult(0));
		
		aggregator.aggregate(0, new Object());
		Assert.assertEquals(2, aggregator.getResult(0));
		
		aggregator.aggregate(0, new Object());
		aggregator.aggregate(0, new Object());
		aggregator.aggregate(0, new Object());
		Assert.assertEquals(5, aggregator.getResult(0));
	}

}
