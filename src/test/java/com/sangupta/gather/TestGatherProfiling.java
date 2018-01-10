package com.sangupta.gather;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sangupta.gather.TestGather.Worker;

public class TestGatherProfiling {

	static final int MAX_TIMES = 5;

	static final int MAX_SIZE = 1000 * 10; //00;

	static final List<Worker> workers = new ArrayList<>((int) (MAX_SIZE * 1.1));

	static final Gather nameQuery = Gather.where("name").like("san*");

	static final Gather ageQuery = Gather.where("age").greaterThan(50);

	static {
		String[] names = new String[] { "sandeep", "sangupta", "abhishek", "sushant" };
		Random random = new Random();
		final int namesLength = names.length;

		for(int index = 0; index < MAX_SIZE; index++) {
			int nameIndex = random.nextInt(namesLength);
			int age = random.nextInt(100);
			workers.add(new Worker(names[nameIndex], age, age % 2 == 0, random.nextInt(1000000)));
		}
	}

	/**
	 * To run the test with profiling: use the VM arguments:
	 *
	 * <code>-agentlib:hprof=cpu=samples</code> for sample profiling
	 * <code>-agentlib:hprof=cpu=times</code> for complete profiling
	 *
	 * @param args
	 */
	public static void main23(String[] args) {
		long start = System.nanoTime();
		for(int index = 0; index < MAX_TIMES; index++) {
			GatherExecutor.handleNumericComparison(123, 123, GatherNumericComparison.GREATER_THAN);
		}
		long end = System.nanoTime();
		System.out.println("Nano: "+ ((end - start) / MAX_TIMES));

		start = System.nanoTime();
		for(int index = 0; index < MAX_TIMES; index++) {
			GatherExecutor.handleNumericComparison(123, 123, GatherNumericComparison.GREATER_THAN);
		}
		end = System.nanoTime();
		System.out.println("Nano: "+ ((end - start) / MAX_TIMES));
	}

	public static void main(String[] args) {
		for(int index = 0; index < MAX_TIMES; index++) {
			ageQuery.find(workers).size();
		}
		System.out.println("Done");
	}

	private static void randomTests() {
		long start, end;

		start = System.nanoTime();
		for(int index = 0; index < MAX_TIMES; index++) {
			nameQuery.find(workers).size();
			ageQuery.find(workers).size();
		}
		end = System.nanoTime();
		System.out.println("Nano: "+ ((end - start) / MAX_TIMES));

		char xx;
		String s = "hello world";
		start = System.nanoTime();
		for(int first = 0; first < MAX_SIZE; first++) {
			xx = s.charAt(4);
		}
		end = System.nanoTime();
		System.out.println("Nano: "+ (end - start));

		char[] x = s.toCharArray();
		start = System.nanoTime();
		for(int first = 0; first < MAX_SIZE; first++) {
			xx = x[4];
		}
		end = System.nanoTime();
		System.out.println("Nano: "+ (end - start));

	}
}
