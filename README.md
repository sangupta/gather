# Gather

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

## Features

* Supported operations
  * `is` - equals match
  * `isIgnoreCase` - equals match ignoring case
  * `like` - wildcard match
  
## TODO

* has - for collection check
* hasAny
* hasAll
* composed object handling
* type conversion

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