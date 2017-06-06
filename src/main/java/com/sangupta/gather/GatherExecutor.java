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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The query executor that takes a {@link Gather} query and fires it against a given
 * collection of objects.
 * 
 * @author sangupta
 *
 */
abstract class GatherExecutor {
	
	static <T> Number aggregate(Collection<T> collection, String key, GatherAggregator aggregator) {
		if(collection == null) {
			return null;
		}
		
		if(key == null) {
			return null;
		}
		
		if(aggregator == null) {
			return null;
		}
		
		if(collection.isEmpty()) {
			return null;
		}
		
		int found = 0;
		for(T item : collection) {
			found = aggregateOnItem(item, key, aggregator, found);
		}
		
		return aggregator.getResult(found);
	}
	
	static <T> Number aggregate(Object[] array, String key, GatherAggregator aggregator) {
		if(array == null) {
			return null;
		}
		
		if(key == null) {
			return null;
		}
		
		if(aggregator == null) {
			return null;
		}
		
		if(array.length == 0) {
			return null;
		}
		
		int found = 0;
		for(Object item : array) {
			found = aggregateOnItem(item, key, aggregator, found);
		}
		
		return aggregator.getResult(found);
	}
	
	static <T> int aggregateOnItem(T item, String key, GatherAggregator aggregator, int found) {
		Field field = GatherReflect.getField(item, key);
		if(field == null) {
			return found;
		}
		
		found++;
		Object value;
		
		try {
			// allow field to be read
			field.setAccessible(true);
			
			value = field.get(item);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("Unable to read value of field", e);
		}
		
		aggregator.aggregate(found, value);
		return found;
	}

	static <T> int count(final Collection<T> collection, final Gather gather) {
		ResultsOrCount<T> resultsOrCount = getResultsInternal(collection, gather, 0, 0, true);
		return resultsOrCount.count;
	}
	
	static <T> List<T> getResults(final Collection<T> collection, final Gather gather, final int numResults, final int skipCount) {
		ResultsOrCount<T> resultsOrCount = getResultsInternal(collection, gather, 0, 0, false);
		return resultsOrCount.list;
	}
	
	static <T> ResultsOrCount<T> getResultsInternal(final Collection<T> collection, final Gather gather, final int numResults, final int skipCount, final boolean countMode) {
		if(collection == null) {
			return null;
		}
		
		ResultsOrCount<T> resultsOrCount = new ResultsOrCount<T>();
		if(collection.isEmpty()) {
			return resultsOrCount;
		}
		
		// run filtering criteria first
		int skipped = 0;
		for(T item : collection) {
			if(matches(item, gather)) {
				// skip elements asked for
				if(skipCount > 0 && skipped < skipCount) {
					skipped++;
					continue;
				}
				
				// add the result - we need this item
				// either as count or as an actual result
				if(countMode) {
					resultsOrCount.count++;
				} else {
					resultsOrCount.add(item);
				}
				
				// break if we have accumulated enough results
				if(numResults > 0 && resultsOrCount.size() == numResults) {
					return resultsOrCount;
				}
			}
		}
		
		return resultsOrCount;
	}

	static <T> boolean matches(T item, Gather gather) {
		if(item == null) {
			return false;
		}
		
		if(gather.criteria.isEmpty()) {
			return true;
		}
		
		boolean finalResult = false;
		for(GatherCriteria criteria : gather.criteria) {
			boolean criteriaResult = matchCriteria(item, criteria);
			
			if(criteria.inverse) {
				criteriaResult = !criteriaResult;
			}
			
			switch(criteria.join) {
				case OR:
					finalResult = criteriaResult | finalResult;
					break;
					
				case AND:
					finalResult = criteriaResult & finalResult;
					break;
					
			}
		}
		
		return finalResult;
	}

	/**
	 * 
	 * @param item an always non-null object
	 * 
	 * @param criteria
	 * @param classOfT
	 * @return
	 */
	static <T> boolean matchCriteria(T item, GatherCriteria criteria) {
		Field field = GatherReflect.getField(item, criteria.key);
		
		if(criteria.operation == GatherOperation.HasProperty) {
			if(field != null) {
				return true;
			}
			
			return false;
		}
		
		if(field == null) {
			return false;
		}
		
		// allow field to be read
		field.setAccessible(true);
		
		// get the value from the object instance
		Object value;
		try {
			value = field.get(item);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("Unable to read value of field", e);
		}
		
		// now match the value against criteria values
		return valueMatchesCriteria(value, criteria.operation, criteria.value);
	}

	static boolean valueMatchesCriteria(Object fieldValue, GatherOperation operation, Object requiredValue) {
		switch(operation) {
			case Equals:
				return handleEquals(fieldValue, requiredValue);
				
			case EqualsIgnoreCase:
				return handleEqualsIgnoreCase(fieldValue, requiredValue);
				
			case GreaterThan:
				return handleGreaterThan(fieldValue, requiredValue);
			
			case GreaterThanOrEquals:
				return handleGreaterThanOrEquals(fieldValue, requiredValue);
			
			case In:
				return handleValueIn(fieldValue, requiredValue);
			
			case IsNull:
				return handleNull(fieldValue, requiredValue);
				
			case CollectionHasValue:
				return handleCollectionHasValue(fieldValue, requiredValue);
				
			case CollectionHasAllValues:
				return handleCollectionHasAllValues(fieldValue, requiredValue);
			
			case CollectionHasAnyValue:
				return handleCollectionHasAnyValue(fieldValue, requiredValue);
			
			case LessThan:
				return handleLessThan(fieldValue, requiredValue);
			
			case LessThanOrEquals:
				return handleLessThanOrEquals(fieldValue, requiredValue);
			
			case RegexMatch:
				return handleRegexMatch(fieldValue, requiredValue);
			
			case WildcardMatch:
				return handleWildcardMatch(fieldValue, requiredValue);
				
			case HasProperty:
				throw new IllegalStateException("This operation should have been taken care of before");
				
			default:
				throw new IllegalStateException("Unknown operation in criteria: " + operation);
		
		}
	}

	static boolean handleCollectionHasAnyValue(Object fieldValue, Object requiredValue) {
		return handleCollectionHasAllOrAnyValues(fieldValue, requiredValue, false);
	}

	static boolean handleCollectionHasAllValues(Object fieldValue, Object requiredValue) {
		return handleCollectionHasAllOrAnyValues(fieldValue, requiredValue, true);
	}
	
	static boolean handleCollectionHasAllOrAnyValues(Object fieldValue, Object requiredValue, boolean usingAllClause) {
		if(fieldValue == null) {
			return false;
		}
		
		if(requiredValue == null) {
			return false;
		}
		
		// check for collection
		if(fieldValue instanceof Collection) {
			Collection<?> collection = (Collection<?>) fieldValue;
			
			return GatherUtils.containsAllOrAny(collection, requiredValue, usingAllClause);
		}
		
		// check for array
		if(fieldValue.getClass().isArray()) {
			if(fieldValue instanceof Object[]) {
				return GatherUtils.containsAllOrAny((Object[]) fieldValue, requiredValue, usingAllClause); 
			}
			
			if(fieldValue instanceof char[]) {
				return GatherUtils.containsAllOrAny((char[]) fieldValue, requiredValue, usingAllClause);
			}
			
			if(fieldValue instanceof boolean[]) {
				return GatherUtils.containsAllOrAny((boolean[]) fieldValue, requiredValue, usingAllClause);
			}
			
			if(fieldValue instanceof byte[]) {
				return GatherUtils.containsAllOrAny((byte[]) fieldValue, requiredValue, usingAllClause);
			}
			
			if(fieldValue instanceof int[]) {
				return GatherUtils.containsAllOrAny((int[]) fieldValue, requiredValue, usingAllClause);
			}
			
			if(fieldValue instanceof short[]) {
				return GatherUtils.containsAllOrAny((short[]) fieldValue, requiredValue, usingAllClause);
			}
			
			if(fieldValue instanceof long[]) {
				return GatherUtils.containsAllOrAny((long[]) fieldValue, requiredValue, usingAllClause);
			}
			
			if(fieldValue instanceof float[]) {
				return GatherUtils.containsAllOrAny((float[]) fieldValue, requiredValue, usingAllClause);
			}
			
			if(fieldValue instanceof double[]) {
				return GatherUtils.containsAllOrAny((double[]) fieldValue, requiredValue, usingAllClause);
			}
		}
		
		// not sure what to do
		return false;
	}

	/**
	 * Check if the given collection or array has the value provided.
	 * 
	 * @param fieldValue
	 * @param requiredValue
	 * @return
	 */
	static boolean handleCollectionHasValue(Object fieldValue, Object requiredValue) {
		if(fieldValue == null) {
			return false;
		}
		
		if(requiredValue == null) {
			return false;
		}
		
		// check for collection
		if(fieldValue instanceof Collection) {
			Collection<?> collection = (Collection<?>) fieldValue;
			
			return collection.contains(requiredValue);
		}
		
		// check for array
		if(fieldValue.getClass().isArray()) {
			if(fieldValue instanceof Object[]) {
				return GatherUtils.contains((Object[]) fieldValue, requiredValue); 
			}
			
			if(fieldValue instanceof char[]) {
				return GatherUtils.contains((char[]) fieldValue, requiredValue);
			}
			
			if(fieldValue instanceof boolean[]) {
				return GatherUtils.contains((boolean[]) fieldValue, requiredValue);
			}
			
			// primitive number arrays can only contain numbers
			if(!(requiredValue instanceof Number)) {
				return false;
			}
			
			final Number number = (Number) requiredValue;
			
			if(fieldValue instanceof byte[]) {
				return GatherUtils.contains((byte[]) fieldValue, number);
			}
			
			if(fieldValue instanceof short[]) {
				return GatherUtils.contains((short[]) fieldValue, number);
			}
			
			if(fieldValue instanceof int[]) {
				return GatherUtils.contains((int[]) fieldValue, number);
			}
			
			if(fieldValue instanceof long[]) {
				return GatherUtils.contains((long[]) fieldValue, number);
			}
			
			if(fieldValue instanceof float[]) {
				return GatherUtils.contains((float[]) fieldValue, number);
			}
			
			if(fieldValue instanceof double[]) {
				return GatherUtils.contains((double[]) fieldValue, number);
			}
		}
		
		// not sure what to do
		return false;
	}

	/**
	 * Handle wildcard match between field and the value.
	 * 
	 * @param fieldValue
	 * @param requiredValue
	 * @return
	 */
	static boolean handleWildcardMatch(Object fieldValue, Object requiredValue) {
		if(fieldValue == null) {
			return false;
		}
		
		if(requiredValue == null) {
			return false;
		}
		
		String value = fieldValue.toString();
		String pattern = requiredValue.toString();
		
		return GatherUtils.wildcardMatch(value, pattern);	
	}

	static boolean handleRegexMatch(Object fieldValue, Object requiredValue) {
		if(fieldValue == null) {
			return false;
		}
		
		if(requiredValue == null) {
			return false;
		}
		
		String value = fieldValue.toString();
		
		if(requiredValue instanceof Pattern) {
			return GatherUtils.regexMatch(value, (Pattern) requiredValue);
		}
		
		String pattern = requiredValue.toString();
		
		return GatherUtils.regexMatch(value, pattern);
	}

	static boolean handleLessThan(Object fieldValue, Object requiredValue) {
		return handleNumericComparison(fieldValue, requiredValue, GatherNumericComparison.LESS_THAN);
	}
	
	static boolean handleGreaterThan(Object fieldValue, Object requiredValue) {
		return handleNumericComparison(fieldValue, requiredValue, GatherNumericComparison.GREATER_THAN);
	}

	static boolean handleLessThanOrEquals(Object fieldValue, Object requiredValue) {
		return handleNumericComparison(fieldValue, requiredValue, GatherNumericComparison.LESS_THAN_OR_EQUALS);
	}
	
	static boolean handleGreaterThanOrEquals(Object fieldValue, Object requiredValue) {
		return handleNumericComparison(fieldValue, requiredValue, GatherNumericComparison.GREATER_THAN_OR_EQUALS);
	}

	static boolean handleNumericComparison(Object fieldValue, Object requiredValue, GatherNumericComparison compareOperation) {
		if(fieldValue == null) {
			return false;
		}
		
		if(fieldValue instanceof Comparable) {
			@SuppressWarnings("unchecked")
			Comparable<Object> comparable = (Comparable<Object>) fieldValue;
			
			int result = comparable.compareTo(requiredValue);
			return compareOperation.test(result);
		}

//		if(GatherUtils.isNumberType(fieldValue)) {
//			Number field = GatherUtils.asNumber(fieldValue);
//			Number required = GatherUtils.asNumber(requiredValue);
//			
//			if(field != null) {
//				if(required == null) {
//					return false;
//				}
//			}
//		}
		
		// TODO: handle when comparable is not implemented
		return false;
	}

	static boolean handleValueIn(Object fieldValue, Object requiredValue) {
		if(fieldValue == null) {
			return false;
		}
		
		if(requiredValue == null) {
			return false;
		}
		
		if(requiredValue instanceof Collection) {
			Collection<?> collection = (Collection<?>) requiredValue;
			if(collection.isEmpty()) {
				return false;
			}
			
			return collection.contains(fieldValue);
		}
		
		if(requiredValue instanceof Object[]) {
			Object[] array = (Object[]) requiredValue;
			
			return GatherUtils.contains(array, fieldValue);
		}
		
		// TODO: handle primitive arrays
		return false;
	}

	static boolean handleNull(Object fieldValue, Object requiredValue) {
		if(fieldValue == null) {
			return true;
		}
		
		return false;
	}

	static boolean handleEqualsIgnoreCase(Object fieldValue, Object requiredValue) {
		if(fieldValue == null) {
			return false;
		}
		
		if(requiredValue == null) {
			return false;
		}
		
		if(fieldValue instanceof String) {
			String str = (String) fieldValue;
			return str.equalsIgnoreCase(requiredValue.toString());
		}
		
		return handleEquals(fieldValue, requiredValue);
	}

	static boolean handleEquals(Object fieldValue, Object requiredValue) {
		if(fieldValue == null) {
			return false;
		}
		
		if(requiredValue == null) {
			return false;
		}
		
		if(fieldValue.equals(requiredValue)) {
			return true;
		}
		
		return false;
	}

	private static class ResultsOrCount<T> {
		
		final List<T> list = new ArrayList<>();;
		
		int count = 0;
		
		public void add(T item) {
			this.list.add(item);
		}
		
		public int size() {
			return this.list.size();
		}
	}
}
