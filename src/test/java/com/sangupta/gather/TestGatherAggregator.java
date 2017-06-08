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

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.gather.GatherAggregator.CountingAggregator;
import com.sangupta.gather.GatherAggregator.DoubleAverageAggregator;
import com.sangupta.gather.GatherAggregator.DoubleMaxAggregator;
import com.sangupta.gather.GatherAggregator.DoubleMinAggregator;
import com.sangupta.gather.GatherAggregator.DoubleSumAggregator;
import com.sangupta.gather.GatherAggregator.LongAverageAggregator;
import com.sangupta.gather.GatherAggregator.LongMaxAggregator;
import com.sangupta.gather.GatherAggregator.LongMinAggregator;
import com.sangupta.gather.GatherAggregator.LongSumAggregator;
import com.sangupta.gather.GatherAggregator.UniqueAggregator;

public class TestGatherAggregator {
	
	@Test
	public void testUniqueAggregator() {
		UniqueAggregator aggregator = new UniqueAggregator();
		
		Object instance = new Object();
		Assert.assertEquals(0, aggregator.getResult(0));
		Assert.assertEquals(0, aggregator.getUniqueSet().size());
		
		aggregator.aggregate(0, instance);
		Assert.assertEquals(1, aggregator.getResult(0));
		Assert.assertEquals(1, aggregator.getUniqueSet().size());
		
		aggregator.aggregate(0, instance);
		aggregator.aggregate(0, instance);
		aggregator.aggregate(0, instance);
		Assert.assertEquals(1, aggregator.getResult(0));
		Assert.assertEquals(1, aggregator.getUniqueSet().size());
		
		aggregator.aggregate(0, new Object());
		Assert.assertEquals(2, aggregator.getResult(0));
		Assert.assertEquals(2, aggregator.getUniqueSet().size());
		
		aggregator.aggregate(0, new Object());
		aggregator.aggregate(0, null);
		aggregator.aggregate(0, new Object());
		aggregator.aggregate(0, null);
		aggregator.aggregate(0, new Object());
		aggregator.aggregate(0, null);
		Assert.assertEquals(5, aggregator.getResult(0));
		Assert.assertEquals(5, aggregator.getUniqueSet().size());
	}

	@Test
	public void testCountingAggregator() {
		CountingAggregator aggregator = new CountingAggregator();
		
		Assert.assertEquals(0, aggregator.getResult(0));
		
		aggregator.aggregate(0, null);
		Assert.assertEquals(1, aggregator.getResult(0));
		
		aggregator.aggregate(0, null);
		aggregator.aggregate(0, null);
		aggregator.aggregate(0, null);
		Assert.assertEquals(4, aggregator.getResult(0));
	}
	
	@Test
	public void testDoubleMinAggregator() {
		DoubleMinAggregator aggregator = new DoubleMinAggregator();
		
		Random random = new Random();
		double result = Double.MAX_VALUE;
		for(int index = 0; index < 100000; index++) {
			double value = random.nextDouble();
			if(result > value) {
				result = value;
			}
			
			aggregator.aggregate(index, value);
		}
		
		Assert.assertEquals(result, aggregator.getResult(0));
	}
	
	@Test
	public void testDoubleMaxAggregator() {
		DoubleMaxAggregator aggregator = new DoubleMaxAggregator();
		
		Random random = new Random();
		double result = Double.MIN_VALUE;
		for(int index = 0; index < 100000; index++) {
			double value = random.nextDouble();
			if(result < value) {
				result = value;
			}
			
			aggregator.aggregate(index, value);
		}
		
		Assert.assertEquals(result, aggregator.getResult(0));
	}
	
	@Test
	public void testDoubleSumAggregator() {
		DoubleSumAggregator aggregator = new DoubleSumAggregator();
		
		Random random = new Random();
		double result = 0;
		for(int index = 0; index < 100000; index++) {
			double value = random.nextDouble();
			result += value;
			
			aggregator.aggregate(index, value);
		}
		
		Assert.assertEquals(result, aggregator.getResult(0));
	}
	
	@Test
	public void testDoubleAverageAggregator() {
		DoubleAverageAggregator aggregator = new DoubleAverageAggregator();
		
		Random random = new Random();
		double result = 0;
		int counted = 0;
		for(int index = 0; index < 100000; index++) {
			double value = random.nextDouble();
			result += value;
			counted++;
			
			aggregator.aggregate(index, value);
		}
		
		Assert.assertEquals(result / counted, aggregator.getResult(counted));
		Assert.assertEquals(0d, new DoubleAverageAggregator().getResult(0));
	}
	
	@Test
	public void testLongMinAggregator() {
		LongMinAggregator aggregator = new LongMinAggregator();
		
		Random random = new Random();
		long result = Long.MAX_VALUE;
		for(int index = 0; index < 100000; index++) {
			long value = random.nextLong();
			if(result > value) {
				result = value;
			}
			
			aggregator.aggregate(index, value);
		}
		
		Assert.assertEquals(result, aggregator.getResult(0));
	}
	
	@Test
	public void testLongMaxAggregator() {
		LongMaxAggregator aggregator = new LongMaxAggregator();
		
		Random random = new Random();
		long result = Long.MIN_VALUE;
		for(int index = 0; index < 100000; index++) {
			long value = random.nextLong();
			if(result < value) {
				result = value;
			}
			
			aggregator.aggregate(index, value);
		}
		
		Assert.assertEquals(result, aggregator.getResult(0));
	}
	
	@Test
	public void testLongSumAggregator() {
		LongSumAggregator aggregator = new LongSumAggregator();
		
		Random random = new Random();
		long result = 0;
		for(int index = 0; index < 100000; index++) {
			long value = random.nextLong();
			result += value;
			
			aggregator.aggregate(index, value);
		}
		
		Assert.assertEquals(result, aggregator.getResult(0));
	}
	
	@Test
	public void testLongAverageAggregator() {
		LongAverageAggregator aggregator = new LongAverageAggregator();
		
		Random random = new Random();
		long result = 0;
		int counted = 0;
		for(int index = 0; index < 100000; index++) {
			long value = random.nextLong();
			result += value;
			counted++;
			
			aggregator.aggregate(index, value);
		}
		
		Assert.assertEquals(result / counted, aggregator.getResult(counted));
		Assert.assertEquals(0d, new LongAverageAggregator().getResult(0));
		
		try { new LongAverageAggregator().aggregate(1, new Object()); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
	}
}
