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
	
	public static <T> Set<Object> unique(Collection<T> collection, String key) {
		GatherAggregator.UniqueAggregator aggregator = new GatherAggregator.UniqueAggregator();
		GatherExecutor.aggregate(collection, key, aggregator);
		
		return aggregator.set;
	}
	
	public static <T> Number count(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.CountingAggregator());
	}
	
	public static <T> Number sumAsLong(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongSumAggregator());
	}
	
	public static <T> Number sumAsDouble(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.DoubleSumAggregator());
	}
	
	public static <T> Number minAsLong(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongMinAggregator());
	}
	
	public static <T> Number minAsDouble(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.DoubleMinAggregator());
	}
	
	public static <T> Number maxAsLong(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongMaxAggregator());
	}
	
	public static <T> Number maxAsDouble(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.DoubleMaxAggregator());
	}
	
	public static <T> Number averageAsLong(Collection<T> collection, String key) {
		return GatherExecutor.aggregate(collection, key, new GatherAggregator.LongAverageAggregator());
	}
	
	public static <T> Number averageAsDouble(Collection<T> collection, String key) {
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
		
		this.siblingJoin = GatherSiblingJoin.AND;
		return fluent();
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
		
		this.siblingJoin = GatherSiblingJoin.OR;
		return fluent();
	}

	/**
	 * Check if the attribute value is equivalent to the given value.
	 * 
	 * @param value
	 * @return
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
	 * @return
	 */
	public Gather isNull() {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.IsNull, null, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	/**
	 * Check if the attribute value is <code>null</code>.
	 * 
	 * @return
	 */
	public Gather isNotNull() {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.IsNull, null, this.siblingJoin, true));
		return fluent();
	}
	
	/**
	 * Check if the attribute value equals to the given value ignoring case.
	 * 
	 * @param value
	 * @return
	 */
	public Gather isIgnoreCase(String value) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.EqualsIgnoreCase, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	/**
	 * Check if the attribute value matches the given value as a wildcard pattern.
	 * 
	 * @param pattern
	 * @return
	 */
	public Gather like(String pattern) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.WildcardMatch, pattern, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	/**
	 * Check if the attribute value matches the given value as a regular-expression match.
	 * 
	 * @param pattern
	 * @return
	 */
	public Gather regex(String pattern) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.RegexMatch, pattern, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	/**
	 * Check if the attribute value matches the given value as a regular-expression match.
	 * 
	 * @param pattern
	 * @return
	 */
	public Gather regex(Pattern pattern) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.RegexMatch, pattern, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather greaterThan(Object value) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.GreaterThan, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather greaterThanOrEquals(Object value) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.GreaterThanOrEquals, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather lessThan(Object value) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.LessThan, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public Gather lessThanOrEquals(Object value) {
		this.criteria.add(new GatherCriteria(this.key, GatherOperation.LessThanOrEquals, value, this.siblingJoin, this.inverse));
		return fluent();
	}
	
	public <T> int count(Collection<T> collection) {
		return GatherExecutor.count(collection, this);
	}
	
	/**
	 * Execute the query over the given collection of objects.
	 * 
	 * @param collection
	 * @param classOfT
	 * @return
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
