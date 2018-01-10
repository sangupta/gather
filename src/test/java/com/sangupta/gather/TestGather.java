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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class TestGather {

	public static void main(String[] args) {
		Gather query = Gather.where("name").in(new Object[] { "Amit Modi", "Sandeep Gupta" }).and("salary").lessThanOrEquals(20d);
		List<Worker> result = query.find(getWorkers());
		for(Worker worker : result) {
			System.out.println(worker);
		}
	}

	@Test
	public void testGatherLimitedResults() {
		final List<Worker> workers = getWorkers();

		Assert.assertEquals(2, Gather.where("age").is(36).find(workers).size());
		Assert.assertNotNull(Gather.where("age").is(36).findOne(workers));
		Assert.assertNotNull(Gather.where("age").is(36).findOne(workers, 1));
		Assert.assertNull(Gather.where("age").is(36).findOne(workers, 2));
		Assert.assertNull(Gather.where("age").is(36).findOne(workers, 10));

		Assert.assertNotNull(Gather.where("age").is(36).find(workers, 1));
		Assert.assertNotNull(Gather.where("age").is(36).find(workers, 1, 1));
	}

	@Test
	public void testGatherAggregation() {
		final List<Worker> workers = getWorkers();
		final Worker[] arrayOfWorkers = workers.toArray(new Worker[] {});

		// on list first
		GatherAggregator aggregator = new GatherAggregator.CountingAggregator();
		Gather.aggregate(workers, "age", aggregator);
		Assert.assertEquals(4, aggregator.getResult(0));

		Assert.assertEquals(150l, Gather.sumAsLong(workers, "age"));
		Assert.assertEquals(150d, Gather.sumAsDouble(workers, "age"));

		Assert.assertEquals(32l, Gather.minAsLong(workers, "age"));
		Assert.assertEquals(32d, Gather.minAsDouble(workers, "age"));

		Assert.assertEquals(46l, Gather.maxAsLong(workers, "age"));
		Assert.assertEquals(46d, Gather.maxAsDouble(workers, "age"));

		Assert.assertEquals(37l, Gather.averageAsLong(workers, "age"));
		Assert.assertEquals(37.5d, Gather.averageAsDouble(workers, "age"));

		Assert.assertEquals(4, Gather.count(workers, "age"));

		Assert.assertEquals(3, Gather.unique(workers, "age").size());
		Assert.assertEquals(2, Gather.unique(workers, "active").size());
		Assert.assertEquals(3, Gather.unique(workers, "salary").size());

		// on array next
		aggregator = new GatherAggregator.CountingAggregator();
		Gather.aggregate(arrayOfWorkers, "age", aggregator);
		Assert.assertEquals(4, aggregator.getResult(0));

		Assert.assertEquals(150l, Gather.sumAsLong(arrayOfWorkers, "age"));
		Assert.assertEquals(150d, Gather.sumAsDouble(arrayOfWorkers, "age"));

		Assert.assertEquals(32l, Gather.minAsLong(arrayOfWorkers, "age"));
		Assert.assertEquals(32d, Gather.minAsDouble(arrayOfWorkers, "age"));

		Assert.assertEquals(46l, Gather.maxAsLong(arrayOfWorkers, "age"));
		Assert.assertEquals(46d, Gather.maxAsDouble(arrayOfWorkers, "age"));

		Assert.assertEquals(37l, Gather.averageAsLong(arrayOfWorkers, "age"));
		Assert.assertEquals(37.5d, Gather.averageAsDouble(arrayOfWorkers, "age"));

		Assert.assertEquals(4, Gather.count(arrayOfWorkers, "age"));

		Assert.assertEquals(3, Gather.unique(arrayOfWorkers, "age").size());
		Assert.assertEquals(2, Gather.unique(arrayOfWorkers, "active").size());
		Assert.assertEquals(3, Gather.unique(arrayOfWorkers, "salary").size());
	}

	@Test
	public void testGather() {
		final List<Worker> workers = getWorkers();

		Gather query = Gather.where("name").is("sandeep");

		Assert.assertEquals(0, query.count((Object[]) null));
		Assert.assertEquals(0, query.count(new ArrayList<>()));
		Assert.assertEquals(0, query.count(workers));

		Assert.assertEquals(0, query.find(null).size());
		Assert.assertEquals(0, query.find(new ArrayList<>()).size());

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

		testGatherQuery(0, Gather.where("salary").regex("S*g"));
		testGatherQuery(0, Gather.where("salary").regex(Pattern.compile("S*g")));
		testGatherQuery(4, Gather.where("salary").not().regex("S*g"));

		testGatherQuery(0, Gather.where("name").isNull());
		testGatherQuery(4, Gather.where("name").not().isNull());
		testGatherQuery(4, Gather.where("name").isNotNull());

		testGatherQuery(4, Gather.hasProperty("name"));
		testGatherQuery(0, Gather.hasProperty("sex"));

		testGatherQuery(0, Gather.where("sex").is(Gender.Male));

		testGatherQuery(3, Gather.where("salary").in(new Object[] { 40l, 50l}));
		testGatherQuery(3, Gather.where("salary").in(Arrays.asList(new Object[] { 40l, 50l})));

		testGatherQuery(2, Gather.where("age").is(32).or("age").is(46));
	}

	@Test
	public void testGatherCollections() {
		testGatherQuery(2, Gather.where("list").has(36));
		testGatherQuery(2, Gather.where("list").has(true));
		testGatherQuery(2, Gather.where("list").has(50));
		testGatherQuery(1, Gather.where("list").has(40));

		testGatherQuery(0, Gather.where("list").hasAll(new Integer[] { 40, 60 }));
		testGatherQuery(1, Gather.where("list").hasAny(new Integer[] { 40, 60 }));

		testGatherQuery(0, Gather.where("list").hasAll(Arrays.asList(new Integer[] { 40, 60 })));
		testGatherQuery(1, Gather.where("list").hasAny(Arrays.asList(new Integer[] { 40, 60 })));
	}

	@Test
	public void testGatherArrays() {
		testGatherQuery(2, Gather.where("array").has(36));
		testGatherQuery(2, Gather.where("array").has(true));
		testGatherQuery(2, Gather.where("array").has(50));
		testGatherQuery(1, Gather.where("array").has(40));

		testGatherQuery(0, Gather.where("array").hasAll(new Integer[] { 40, 60 }));
		testGatherQuery(1, Gather.where("array").hasAny(new Integer[] { 40, 60 }));

		testGatherQuery(0, Gather.where("array").hasAll(Arrays.asList(new Integer[] { 40, 60 })));
		testGatherQuery(1, Gather.where("array").hasAny(Arrays.asList(new Integer[] { 40, 60 })));
	}

	@Test
	public void testQueryBuilderErrors() {
		try { Gather.where("name").and("age"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("name").or("age"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("name").is("sandeep").not(); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("name").is("sandeep").is("gupta"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("name").is("sandeep").isNull(); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("name").is("sandeep").isNotNull(); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("name").is("sandeep").isIgnoreCase("gupta"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("name").is("sandeep").like("gupta"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("name").is("sandeep").regex("gupta"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("name").is("sandeep").regex(Pattern.compile("gupta")); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }

		try { Gather.where("age").is("36").greaterThan(40); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("age").is("36").greaterThanOrEquals(40); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("age").is("36").lessThan(40); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("age").is("36").lessThanOrEquals(40); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }

		try { Gather.where("age").is("36").in(new Object[] { 40 }); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.where("age").is("36").in(new ArrayList<>()); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
	}

	@Test
	public void testQueryExecutionErrors() {
		try { Gather.sumAsLong(getWorkers(), "name"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.sumAsDouble(getWorkers(), "name"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }

		try { Gather.maxAsLong(getWorkers(), "name"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.maxAsDouble(getWorkers(), "name"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }

		try { Gather.minAsLong(getWorkers(), "name"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.minAsDouble(getWorkers(), "name"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }

		try { Gather.averageAsLong(getWorkers(), "name"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
		try { Gather.averageAsDouble(getWorkers(), "name"); Assert.assertTrue(false); } catch(IllegalArgumentException e) { Assert.assertTrue(true); }
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

	private static List<Worker> getWorkers() {
		List<Worker> workers = new ArrayList<>();

		workers.add(new Worker("Sandeep Gupta", 36, true, 40l));
		workers.add(new Worker("Abhishek Gupta", 32, true, 50l));
		workers.add(new Worker("Amit Modi", 36, false, 50l));
		workers.add(new Worker("Sushant Gupta", 46, false, 70l));

		return workers;
	}

	static class Worker {

		String name;

		int age;

		boolean active;

		long salary;

		List<String> list = new ArrayList<>();

		Object[] array;

		@Override
		public String toString() {
			return this.name + ", age=" + this.age + ", active=" + this.active + ", salary=" + this.salary;
		}

		public Worker(String name, int age, boolean active, long salary) {
			this.name = name;
			this.age = age;
			this.active = active;
			this.salary = salary;

			this.list.add(name);
			this.list.add(String.valueOf(age));
			this.list.add(String.valueOf(salary));
			this.list.add(String.valueOf(active));

			this.array = this.list.toArray(new Object[] {});
		}

	}

	static enum Gender {

		Male,

		Female;
	}

}
