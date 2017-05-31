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
		
		query = Gather.where("name").like("sandeep?");
		 
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
		
		public Employee() {
			this.name = UUID.randomUUID().toString();
			this.age = RANDOM.nextInt(50) + 20;
			this.sex = this.age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
			
			if(this.age % 23 == 0) {
//				System.out.println("Changing name....");
				
				if(changed < 4) {
					this.age = 99;
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
