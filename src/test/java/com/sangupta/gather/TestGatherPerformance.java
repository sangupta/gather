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
import java.util.List;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.sangupta.gather.TestGather.Worker;

/**
 * Check simple performance for the library.
 *
 * @author sangupta
 *
 */
@BenchmarkMode(Mode.Throughput)
public class TestGatherPerformance {

	static final List<Worker> workers = new ArrayList<>();

	static final Gather nameQuery = Gather.where("name").like("san*");

	static final Gather ageQuery = Gather.where("age").greaterThan(50);

	static {
		String[] names = new String[] { "sandeep", "sangupta", "abhishek", "sushant" };
		Random random = new Random();

		for(int index = 0; index < 1000*1000; index++) {
			int nameIndex = random.nextInt(4);
			int age = random.nextInt(100);
			workers.add(new Worker(names[nameIndex], age, age % 2 == 0, random.nextInt(1000000)));
		}
	}

	@Benchmark
	public void testLikePerformance() {
		int count = nameQuery.find(workers).size();
	}

	@Benchmark
	public void testNumericPerformance() {
		int count = ageQuery.find(workers).size();
	}

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder().include(TestGatherPerformance.class.getSimpleName())
											 .warmupIterations(5)
											 .measurementIterations(20)
											 .threads(1)
										     .forks(1)
										     .mode(Mode.Throughput)
										     .build();

		new Runner(options).run();
	}

}
