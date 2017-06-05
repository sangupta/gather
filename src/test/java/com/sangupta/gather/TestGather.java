package com.sangupta.gather;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Test;

public class TestGather {
	
	@Test
	public void testGather() {
		Gather query = Gather.where("name").is("sandeep");
		
		Assert.assertEquals(0, query.count(null));
		Assert.assertEquals(0, query.count(new ArrayList<>()));
		Assert.assertEquals(0, query.count(getWorkers()));

		query = Gather.where("name").is("sandeep gupta");
		Assert.assertEquals(0, query.count(getWorkers()));

	
		query = Gather.where("name").isIgnoreCase("sandeep gupta");
		Assert.assertEquals(1, query.count(getWorkers()));
		
		query = Gather.where("name").like("*gupta");
		Assert.assertEquals(0, query.count(getWorkers()));
		
		query = Gather.where("name").like("S*Gupta");
		Assert.assertEquals(2, query.count(getWorkers()));
		
		query = query.and("active").is(true);
		Assert.assertEquals(1, query.count(getWorkers()));
		
		query = Gather.where("name").like("S*Gupta").and("active").is(false);
		Assert.assertEquals(1, query.count(getWorkers()));
		
//		query = Gather.where("salary").greaterThan(40);
//		Assert.assertEquals(3, query.count(getWorkers()));
//		
//		query = Gather.where("salary").greaterThan(10);
//		Assert.assertEquals(4, query.count(getWorkers()));
//		
//		query = Gather.where("salary").greaterThan(100);
//		Assert.assertEquals(0, query.count(getWorkers()));
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
		
		private AtomicLong salary;
		
		public Worker(String name, int age, boolean active, long salary) {
			this.name = name;
			this.age = age;
			this.active = active;
			this.salary = new AtomicLong(salary);
		}
		
	}

}
