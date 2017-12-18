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
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Gather is the main query class that a callee deals with. Once the query is
 * built up they can fire it on a collection of objects. For example,
 * <code>Gather query = Gather.where("name").is("sangupta").and("age").lessThan(40);</code>
 * is a valid query to be fired on a collection of objects.
 * 
 * @author sangupta
 *
 */
public class Gather {
	
	final List<GatherCriteria> criteria = new ArrayList<>();
	
	/**
	 * The field name over which the query clause will fire
	 */
	private String key;
	
	/**
	 * The default sibling join method for clauses
	 */
	private GatherSiblingJoin siblingJoin = GatherSiblingJoin.OR;
	
	private boolean inverse = false;
	
	// ***************************************
	// STATIC METHODS FOLLOW
	// ***************************************
	
	public static Gather where(String name) {
		return new Gather(name);
	}
	
	public static Gather hasProperty(String name) {
		Gather instance = new Gather(name);
		instance.existsProperty();
		return instance;
	}
	
	// ***************************************
	// AGGREGATION METHODS FOLLOW
	// ***************************************
	
	public static <T> void aggregate(Collection<T> collection, String key, GatherAggregator aggregator) {
		GatherExecutor.aggregate(collection, key, aggregator);
	}
	
	public static <T> void aggregate(Object[] array, String key, GatherAggregator aggregator) {
		GatherExecutor.aggregate(array, key, aggregator);
	}
	
	public static <T> Set<Object> unique(Collection<T> collection, String key) {
		GatherAggregator.UniqueAggregator aggregator = new GatherAggregator.UniqueAggregator();
		GatherExecutor.aggregate(collection, key, aggregator);
		
		return aggregator.set;
	}
	
	public static <T> Set<Object> unique(Object[] collection, String key) {
		GatherAggregator.UniqueAggregator aggregator = new GatherAggregator.UniqueAggregator();
		GatherExecutor.aggregate(collection, key, aggregator);
		
		return aggregator.set;
	}
	
	public static <T> Number count(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.CountingAggregator());
	}
	
	public static <T> Number count(Object[] collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.CountingAggregator());
	}
	
	public static <T> Number sumAsLong(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongSumAggregator());
	}
	
	public static <T> Number sumAsLong(Object[] collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongSumAggregator());
	}
	
	public static <T> Number sumAsDouble(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.DoubleSumAggregator());
	}
	
	public static <T> Number sumAsDouble(Object[] collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.DoubleSumAggregator());
	}
	
	public static <T> Number minAsLong(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongMinAggregator());
	}
	
	public static <T> Number minAsLong(Object[] collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongMinAggregator());
	}
	
	public static <T> Number minAsDouble(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.DoubleMinAggregator());
	}
	
	public static <T> Number minAsDouble(Object[] collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.DoubleMinAggregator());
	}
	
	public static <T> Number maxAsLong(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongMaxAggregator());
	}
	
	public static <T> Number maxAsLong(Object[] collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongMaxAggregator());
	}
	
	public static <T> Number maxAsDouble(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.DoubleMaxAggregator());
	}
	
	public static <T> Number maxAsDouble(Object[] collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.DoubleMaxAggregator());
	}
	
	public static <T> Number averageAsLong(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongAverageAggregator());
	}
	
	public static <T> Number averageAsLong(Object[] collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongAverageAggregator());
	}
	
	public static <T> Number averageAsDouble(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.DoubleAverageAggregator());
	}
	
	public static <T> Number averageAsDouble(Object[] collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.DoubleAverageAggregator());
	}
	
	// ***************************************
	// PUBLIC INSTANCE METHODS FOLLOW
	// ***************************************
	
	private Gather(String key) {
		this.key = key;
	}

	public Gather and(String key) {
		if(this.key != null) {
			throw new IllegalArgumentException("Add a comparison condition to previous key first");
		}
		
		this.key = key;
		this.siblingJoin = GatherSiblingJoin.AND;
		this.inverse = false;
		return this;
	}
	
	public Gather not() {
		if(this.key == null) {
			throw new IllegalArgumentException("Define a key first");
		}
		
		this.inverse = true;
		return this;
	}

	public Gather or(String key) {
		if(this.key != null) {
			throw new IllegalArgumentException("Add a comparison condition to previous key first");
		}
		
		this.key = key;
		this.siblingJoin = GatherSiblingJoin.OR;
		this.inverse = false;
		return this;
	}

	/**
	 * Check if the attribute value is equivalent to the given value.
	 * 
	 * @param value
	 *            the object value to compare against
	 * 
	 * @return this {@link Gather} instance
	 */
	public Gather is(Object value) {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.Equals, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather existsProperty() {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.HasProperty, null, this.siblingJoin, false));
		return fluent();
	}
	
	public Gather notExistsProperty() {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.HasProperty, null, this.siblingJoin, true));
		return fluent();
	}
	
	public Gather has(Object value) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.CollectionHasValue, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather hasAll(Collection<?> value) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.CollectionHasAllValues, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather hasAll(Object[] value) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.CollectionHasAllValues, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather hasAny(Collection<?> value) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.CollectionHasAnyValue, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather hasAny(Object[] value) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.CollectionHasAnyValue, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	/**
	 * Check if the attribute value is <code>null</code>.
	 * 
	 * @return this {@link Gather} instance
	 */
	public Gather isNull() {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.IsNull, null, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	/**
	 * Check if the attribute value is <code>null</code>.
	 * 
	 * @return this {@link Gather} instance
	 */
	public Gather isNotNull() {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.IsNull, null, this.siblingJoin, true));
		return fluent();
	}
	
	public Gather in(Collection<?> collection) {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.In, collection, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather in(Object[] array) {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.In, array, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	/**
	 * Check if the attribute value equals to the given value ignoring case.
	 * 
	 * @param value
	 *            {@link String} value to compare against
	 * 
	 * @return this {@link Gather} instance
	 */
	public Gather isIgnoreCase(String value) {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.EqualsIgnoreCase, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	/**
	 * Check if the attribute value matches the given value as a wildcard
	 * pattern.
	 * 
	 * @param pattern
	 *            <code>wildcard</code> pattern to compare against
	 * 
	 * @return this {@link Gather} instance
	 */
	public Gather like(String pattern) {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.WildcardMatch, pattern, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	/**
	 * Check if the attribute value matches the given value as a
	 * regular-expression match.
	 * 
	 * @param pattern
	 *            <code>regex</code> pattern to compare against
	 * 
	 * @return this {@link Gather} instance
	 */
	public Gather regex(String pattern) {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.RegexMatch, pattern, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	/**
	 * Check if the attribute value matches the given value as a
	 * regular-expression match.
	 * 
	 * @param pattern
	 *            regex {@link Pattern} to compare against
	 * 
	 * @return this {@link Gather} instance
	 */
	public Gather regex(Pattern pattern) {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.RegexMatch, pattern, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather greaterThan(Object value) {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.GreaterThan, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather greaterThanOrEquals(Object value) {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.GreaterThanOrEquals, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather lessThan(Object value) {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.LessThan, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather lessThanOrEquals(Object value) {
		if(this.key == null) {
			throw new IllegalArgumentException("Operation needs a key to work upon");
		}
		
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.LessThanOrEquals, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	/**
	 * Count the number of objects that match the given criteria
	 * in the given collection.
	 * 
	 * @param collection
	 * @return
	 */
	public <T> int count(Collection<T> collection) {
		if(collection == null) {
			return 0;
		}
		
		if(collection.isEmpty()) {
			return 0;
		}
		
		return GatherExecutor.count(collection, this);
	}
	
	public <T> int count(Object[] array) {
		if(array == null) {
			return 0;
		}
		
		if(array.length == 0) {
			return 0;
		}
		
		return GatherExecutor.count(array, this);
	}
	
	/**
	 * Execute the query over the given collection of objects.
	 * 
	 * @param collection
	 *            the collection to run {@link Gather} query against
	 * 
	 * @return the results, if any, found after running the query
	 */
	public <T> List<T> find(Collection<T> collection) {
		return GatherExecutor.getResults(collection, this, 0, 0);
	}
	
	public <T> List<T> find(Collection<T> collection, int numResults) {
		return GatherExecutor.getResults(collection, this, numResults, 0);
	}
	
	public <T> List<T> find(Collection<T> collection, int numResults, int skipCount) {
		return GatherExecutor.getResults(collection, this, numResults, skipCount);
	}
	
	public <T> T findOne(Collection<T> collection) {
		return findOne(collection, 0);
	}
	
	public <T> T findOne(Collection<T> collection, int skipCount) {
		List<T> results = GatherExecutor.getResults(collection, this, 1, skipCount);
		if(results == null || results.isEmpty()) {
			return null;
		}
		
		return results.get(0);
	}
	
	// ***************************************
	// INTERNAL INSTANCE METHODS FOLLOW
	// ***************************************
	
	Gather fluent() {
		this.key = null;
		this.inverse = false;
		return this;
	}
	
}
