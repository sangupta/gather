package com.sangupta.gather;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestGather {
	
	@Test
	public void testGather() {
		final List<Worker> workers = getWorkers();
		
		Gather query = Gather.where("name").is("sandeep");
		
		Assert.assertEquals(0, query.count(null));
		Assert.assertEquals(0, query.count(new ArrayList<>()));
		Assert.assertEquals(0, query.count(workers));

		query = Gather.where("name").is("sandeep gupta");
		Assert.assertEquals(0, query.count(workers));

	
		query = Gather.where("name").isIgnoreCase("sandeep gupta");
		Assert.assertEquals(1, query.count(workers));
		
		query = Gather.where("name").like("*gupta");
		Assert.assertEquals(0, query.count(workers));
		
		query = Gather.where("name").like("S*Gupta");
		Assert.assertEquals(2, query.count(workers));
		
		query = query.and("active").is(true);
		Assert.assertEquals(1, query.count(workers));
		
		query = Gather.where("name").like("S*Gupta").and("active").is(false);
		Assert.assertEquals(1, query.count(workers));
		
		query = Gather.where("salary").greaterThan(40l);
		Assert.assertEquals(3, query.count(workers));
		
		query = Gather.where("salary").greaterThan(50l);
		Assert.assertEquals(1, query.count(workers));
		
		query = Gather.where("salary").greaterThanOrEquals(50l);
		Assert.assertEquals(3, query.count(workers));
		
		query = Gather.where("salary").greaterThanOrEquals(50l).and("active").is(true);
		Assert.assertEquals(1, query.count(workers));
		
		query = Gather.where("salary").greaterThanOrEquals(50l).and("active").is(false);
		Assert.assertEquals(2, query.count(workers));
		
		query = Gather.where("salary").greaterThan(10l);
		Assert.assertEquals(4, query.count(workers));
		
		query = Gather.where("salary").greaterThan(100l);
		Assert.assertEquals(0, query.count(workers));
		
		query = Gather.where("salary").lessThan(50l);
		Assert.assertEquals(1, query.count(workers));
		
		query = Gather.where("salary").lessThanOrEquals(50l);
		Assert.assertEquals(3, query.count(workers));
	}
	
	private List<Worker> getWorkers() {
		List<Worker> workers = new ArrayList<>();
		
		workers.add(new Worker("Sandeep Gupta", 36, true, 40l));
		workers.add(new Worker("Abhishek Gupta", 32, true, 50l));
		workers.add(new Worker("Amit Modi", 36, false, 50l));
		workers.add(new Worker("Sushant Gupta", 36, false, 70l));
		
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
