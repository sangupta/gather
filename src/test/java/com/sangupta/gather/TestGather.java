package com.sangupta.gather;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestGather {
	
	@Test
	public void testGatherAggregation() {
		final List<Worker> workers = getWorkers();
		final Worker[] arrayOfWorkers = workers.toArray(new Worker[] {});
		
		// on list first
		Assert.assertEquals(150l, Gather.sumAsLong(workers, "age"));
		Assert.assertEquals(150d, Gather.sumAsDouble(workers, "age"));
		
		Assert.assertEquals(32l, Gather.minAsLong(workers, "age"));
		Assert.assertEquals(32d, Gather.minAsDouble(workers, "age"));
		
		Assert.assertEquals(46l, Gather.maxAsLong(workers, "age"));
		Assert.assertEquals(46d, Gather.maxAsDouble(workers, "age"));

		Assert.assertEquals(37l, Gather.averageAsLong(workers, "age"));
		Assert.assertEquals(37.5d, Gather.averageAsDouble(workers, "age"));
		
		Assert.assertEquals(4, Gather.count(workers, "age"));

		// on array next
		Assert.assertEquals(150l, Gather.sumAsLong(arrayOfWorkers, "age"));
		Assert.assertEquals(150d, Gather.sumAsDouble(arrayOfWorkers, "age"));
		
		Assert.assertEquals(32l, Gather.minAsLong(arrayOfWorkers, "age"));
		Assert.assertEquals(32d, Gather.minAsDouble(arrayOfWorkers, "age"));
		
		Assert.assertEquals(46l, Gather.maxAsLong(arrayOfWorkers, "age"));
		Assert.assertEquals(46d, Gather.maxAsDouble(arrayOfWorkers, "age"));

		Assert.assertEquals(37l, Gather.averageAsLong(arrayOfWorkers, "age"));
		Assert.assertEquals(37.5d, Gather.averageAsDouble(arrayOfWorkers, "age"));
		
		Assert.assertEquals(4, Gather.count(arrayOfWorkers, "age"));
	}
	
	@Test
	public void testGather() {
		final List<Worker> workers = getWorkers();
		
		Gather query = Gather.where("name").is("sandeep");
		
		Assert.assertEquals(0, query.count(null));
		Assert.assertEquals(0, query.count(new ArrayList<>()));
		Assert.assertEquals(0, query.count(workers));
		
		testGatherQuery(0, Gather.where("noAttribute").existsProperty());
		testGatherQuery(4, Gather.where("noAttribute").notExistsProperty());
		
		testGatherQuery(0, Gather.where("name").is("sandeep gupta"));
		testGatherQuery(1, Gather.where("name").isIgnoreCase("sandeep gupta"));
		
		testGatherQuery(0, Gather.where("name").like("*gupta"));
		testGatherQuery(2, Gather.where("name").like("S*Gupta"));
		testGatherQuery(2, Gather.where("name").not().like("S*Gupta"));
		
		testGatherQuery(1, query.and("active").is(true));
		testGatherQuery(1, Gather.where("name").like("S*Gupta").and("active").is(false));

		testGatherQuery(3, Gather.where("salary").greaterThan(40l));
		testGatherQuery(1, Gather.where("salary").greaterThan(50l));
		testGatherQuery(3, Gather.where("salary").greaterThanOrEquals(50l));
		testGatherQuery(1, Gather.where("salary").greaterThanOrEquals(50l).and("active").is(true));
		testGatherQuery(2, Gather.where("salary").greaterThanOrEquals(50l).and("active").is(false));

		testGatherQuery(4, Gather.where("salary").greaterThan(10l));
		testGatherQuery(0, Gather.where("salary").greaterThan(100l));
		testGatherQuery(1, Gather.where("salary").lessThan(50l));
		testGatherQuery(3, Gather.where("salary").lessThanOrEquals(50l));
	}
	
	private boolean testGatherQuery(int expected, Gather query) {
		List<Worker> result = query.find(this.getWorkers());
		if(expected == 0) {
			if(result == null || result.size() == 0) {
				return true;
			}
			
			return false;
		}
		
		if(expected != result.size()) {
			return false;
		}
		
		return expected == query.count(this.getWorkers());
	}
	
	private List<Worker> getWorkers() {
		List<Worker> workers = new ArrayList<>();
		
		workers.add(new Worker("Sandeep Gupta", 36, true, 40l));
		workers.add(new Worker("Abhishek Gupta", 32, true, 50l));
		workers.add(new Worker("Amit Modi", 36, false, 50l));
		workers.add(new Worker("Sushant Gupta", 46, false, 70l));
		
		return workers;
	}
	
	static class Worker {
		
		private String name;
		
		private int age;
		
		private boolean active;
		
		private long salary;
		
		public Worker(String name, int age, boolean active, long salary) {
			this.name = name;
			this.age = age;
			this.active = active;
			this.salary = salary;
		}
		
	}

}
