# Gather

[![Travis](https://img.shields.io/travis/sangupta/gather.svg)]()
[![Coveralls](https://img.shields.io/coveralls/sangupta/gather.svg)]()
[![license](https://img.shields.io/github/license/sangupta/gather.svg)]()

`Gather` is a library to fire SQL like queries on Java collections.

The library is tested on the following JDK versions:

* Oracle JDK 8
* Oracle JDK 7
* Open JDK 7

## Usage Examples

```java
List<Employee> employees = this.employeeService.getAllEmployeesFromDatabase();

// prepare a query
Gather query = Gather.where("age").greaterThan(50).and("status").is("active");

// fire query
List<Employee> filtered = query.find(employees);

// reuse the query for another collection
List<Manager> managers = this.employeeService.getAllManagersFromDatabase();

List<Manager> filteredManagers = query.find(managers);

// null check operations
query = Gather.where("name").isNull();
query = Gather.where("name").isNotNull();

// not operator
query = Gather.where("name").not().is("sandeep");

// find instances that have a property
// one of the two ways can be employed
gather = Gather.where("name").existsProperty();
gather = Gather.hasProperty("name");

// query if an attribute which is a collection or an array contains a given value
// converting between various primitive value types is supported
gather = Gather.where("someFloatArray").has(new Double(123));

// run over collections as well
gather = Gather.where("collection").has(objectInstance);

// find a single instance
query.findOne(employees);

// find limited instances
query.find(employees, 5);

// find 5 instances, but skip the first 10 instances
query.find(employees, 5, 10);

// count the number of results rather than accumulating them
// this is much faster and memory efficient
int numResults = query.count(employees);
```

## Composed Objects and Keys

`Gather` supports composed objects and keys in the `where` clause based on them. For example: the
following query will search all objects in the collection/array called `children` which have an `age`
attribute and its value is less than `40`.

```java
Gather query = Gather.where("children.age").lessThan(10);

query.find(employees);

public class Employee {

	public List<Child> children;

}

public class Child {

	public int age;
	
}
```

## Features

* Gathering operations
  * `find` - find all matching objects, with options to skip elements and/or limit number of matches
  * `findOne` - find the first matching object
  * `count` - count the total number of matching objects
  * `aggregate` - run a custom aggregator on a collection/array for a given field
* Supported operations
  * `is` - equals match
  * `isIgnoreCase` - equals match ignoring case on strings
  * `isNull` - check if a property is null
  * `isNotNull` - check if a property is not null
  * `like` - wildcard match on strings
  * `regex` - Java regular expression match on strings
  * `has` - check if value is contained in a collection or an array
  * `hasAll` - check if all values are contained in a collection or an array
  * `hasAny` - check if any of the value are present in a collection or an array
  * `not` - negate the match expression
  * `existsProperty` - check if property exists on object
  * `notExistsProperty` - check if property does not exists on object
  * `lessThan` - if a value is less than property value
  * `lessThanOrEquals` - if a value is equal or less than property value
  * `greaterThan` - if a value is greater than property value
  * `greaterThanOrEquals` - if a value is equal or greater than property value
* Boolean operations supported
  * `and` - Boolean AND between two clauses
  * `or` - Boolean OR between two clauses
* Aggregation operations
  * `averageAsLong` - find average value of a field which has no decimal part
  * `averageAsDouble` - find average value of a field which has a decimal part
  * `minAsLong` - find minimum value of a field which has no decimal part
  * `minAsDouble` - find minimum value of a field which has a decimal part
  * `maxAsLong` - find maximum value of a field which has no decimal part
  * `maxAsDouble` - find maximum value of a field which has a decimal part
  * `sumAsLong` - find total sum of value of a field which has no decimal part
  * `sumAsDouble` - find total sum of value of a field which has a decimal part
  * `unique` - find the number of unique objects from the result set
  * `count` - count objects in a collection/array which have a given field
  
## Clause chaining and Evaluation

When more than one clause is added to the query and Boolean operations like `AND` or `OR` are used
to connect them, the evaluation happens from left to right. The first clause is evaluated first and
then the result is boolean `AND`/`OR` with the result of next clause depending on connecting operation
type.

For example:

```java
Gather.where("name").like("sandeep*").and("age").lessThan(50).or("status").is("active");
```

The evaluation happens in the order:

```java
boolean first = evaluate("name like 'sandeep*'");
boolean second = evaluate("age < 50");
boolean third = evaluate("status == 'active'");
return first & second | third;
```

## Versioning

For transparency and insight into our release cycle, and for striving to maintain 
backward compatibility, `gather` will be maintained under the Semantic 
Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

```
<major>.<minor>.<patch>
```

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

## License

```
 gather: SQL queries for Java collections
 Copyright (c) 2017, Sandeep Gupta
 
 https://sangupta.com/projects/gather
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```
