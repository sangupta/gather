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
import java.util.UUID;

public class GatherTest {
	
	public static final Random RANDOM = new Random();
	
	public static void main(String[] args) {
		List<Employee> list = new ArrayList<>();
		for(int i = 0; i < 1000; i++) {
			Employee e = new Employee();
			list.add(e);
		}
		
		System.out.println("Total modified objects: " + Employee.changed);
		
		// start actual testing
		Gather query = Gather.where("name").is("sandeep").and("age").greaterThan(80);
		
		query = Gather.where("name1").existsProperty();
		query = Gather.where("array").has(99);
		
		// query = query.and("age").lessThan(40);
		// query = query.and("age").between(40, 60);
		// query = query.and("age").lessThan(40).or().greaterThan(60);
		// query = Gather.where("name").in("sandeep");
		
		List<Employee> results = query.find(list);
		if(results == null) {
			System.out.println("Results instance is NULL - SOME BUG");
			return;
		}
		
		System.out.println("Found results: " + results.size());
	}

	public static class Employee {
		
		public String name;
		
		public int age;
		
		public Gender sex;
		
		static int changed = 0;
		
		public int[] array = new int[10];
		
		public Employee() {
			this.name = UUID.randomUUID().toString();
			this.age = RANDOM.nextInt(50) + 20;
			this.sex = this.age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
			
			if(this.age % 23 == 0) {
//				System.out.println("Changing name....");
				
				if(changed < 4) {
					this.age = 99;
					this.array[RANDOM.nextInt(10)] = 99;
				}
				
				this.name = "sandeep" + changed;
				changed++;
			}
		}
		
	}
	
	public static enum Gender {
		
		MALE,
		
		FEMALE;
		
	}
	
}
