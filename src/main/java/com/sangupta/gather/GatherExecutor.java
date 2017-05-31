package com.sangupta.gather;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The query executor that takes a {@link Gather} query and fires it against a given
 * collection of objects.
 * 
 * @author sangupta
 *
 */
class GatherExecutor {

	static <T> List<T> getResults(Collection<T> collection, Gather gather) {
		if(collection == null) {
			return null;
		}
		
		List<T> results = new ArrayList<>();
		if(collection.isEmpty()) {
			return results;
		}
		
		// run filtering criteria first
		for(T item : collection) {
			if(matches(item, gather)) {
				results.add(item);
			}
		}
		
		return results;
	}

	static <T> boolean matches(T item, Gather gather) {
		if(item == null) {
			return false;
		}
		
		if(gather.criteria.isEmpty()) {
			return true;
		}
		
		boolean fulfilled = false;
		for(GatherCriteria criteria : gather.criteria) {
			boolean result = matchCriteria(item, criteria);
			
//			Employee e = (Employee) item;
//			if(e.name.equals("sandeep")) {
//				System.out.println("found item");
//			}
			
			switch(criteria.join) {
				case OR:
					fulfilled = result | fulfilled;
					break;
					
				case AND:
					fulfilled = result & fulfilled;
					break;
					
			}
		}
		
		return fulfilled;
	}

	/**
	 * 
	 * @param item an always non-null object
	 * 
	 * @param criteria
	 * @param classOfT
	 * @return
	 */
	private static <T> boolean matchCriteria(T item, GatherCriteria criteria) {
		Field field = GatherReflect.getField(item, criteria.key);
		
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

	private static boolean valueMatchesCriteria(Object fieldValue, GatherOperation operation, Object requiredValue) {
		switch(operation) {
			case Equals:
				return handleEquals(fieldValue, requiredValue);
				
			case EqualsIgnoreCase:
				return handleEqualsIgnoreCase(fieldValue, requiredValue);
				
			case GreaterThan:
				return handleGreaterThan(fieldValue, requiredValue);
			
			case In:
				return handleValueIn(fieldValue, requiredValue);
			
			case IsNull:
				return handleNull(fieldValue, requiredValue);
			
			case LessThan:
				return handleLessThan(fieldValue, requiredValue);
			
			case Not:
				return handleNot(fieldValue, requiredValue);
			
			case RegexMatch:
				return handleRegexMatch(fieldValue, requiredValue);
			
			case WildcardMatch:
				return handleWildcardMatch(fieldValue, requiredValue);
			
			default:
				throw new IllegalStateException("Unknown operation in criteria: " + operation);
		
		}
	}

	private static boolean handleWildcardMatch(Object fieldValue, Object requiredValue) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean handleRegexMatch(Object fieldValue, Object requiredValue) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean handleNot(Object fieldValue, Object requiredValue) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean handleLessThan(Object fieldValue, Object requiredValue) {
		if(fieldValue == null) {
			return false;
		}
		
		if(fieldValue instanceof Comparable) {
			@SuppressWarnings("unchecked")
			Comparable<Object> comparable = (Comparable<Object>) fieldValue;
			
			int result = comparable.compareTo(requiredValue);
			if(result < 0) {
				return true;
			}
		}
		
		// TODO: handle when comparable is not implemented
		return false;
	}

	private static boolean handleValueIn(Object fieldValue, Object requiredValue) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean handleGreaterThan(Object fieldValue, Object requiredValue) {
		if(fieldValue == null) {
			return false;
		}
		
		if(fieldValue instanceof Comparable) {
			@SuppressWarnings("unchecked")
			Comparable<Object> comparable = (Comparable<Object>) fieldValue;
			
			int result = comparable.compareTo(requiredValue);
			if(result > 0) {
				return true;
			}
		}
		
		// TODO: handle when comparable is not implemented
		return false;
	}

	private static boolean handleNull(Object fieldValue, Object requiredValue) {
		if(fieldValue == null) {
			return true;
		}
		
		return false;
	}

	private static boolean handleEqualsIgnoreCase(Object fieldValue, Object requiredValue) {
		if(requiredValue == null) {
			return false;
		}
		
		if(fieldValue instanceof String) {
			String str = (String) fieldValue;
			return str.equalsIgnoreCase(requiredValue.toString());
		}
		
		return handleEquals(fieldValue, requiredValue);
	}

	private static boolean handleEquals(Object fieldValue, Object requiredValue) {
		if(requiredValue == null) {
			return false;
		}
		
		if(fieldValue.equals(requiredValue)) {
			return true;
		}
		return false;
	}

}
