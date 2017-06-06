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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility functions for the Gather library.
 *  
 * @author sangupta
 *
 */
abstract class GatherUtils {
	
	final static Map<String, Pattern> COMPILED_PATTERNS = new HashMap<>();
	
	final static Object[] NUMBER_TYPES = new Object[] { Integer.class, Long.class, Byte.class, Short.class,
														Float.class, Double.class, AtomicLong.class, AtomicInteger.class
	};

	static boolean contains(char[] array, Object value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof Character) {
			char val = (Character) value;
			for(char item : array) {
				if(item == val) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	static boolean contains(boolean[] array, Object value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof Boolean) {
			boolean val = (Boolean) value;
			for(boolean item : array) {
				if(item == val) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	static boolean contains(byte[] array, Object value) {
		if(value == null) {
			return false;
		}
		
		if(value instanceof Number) {
			return contains(array, (Number) value);
		}
		
		return false;
	}
	
	static boolean contains(byte[] array, Number value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		byte val = value.byteValue();
		for(byte item : array) {
			if(item == val) {
				return true;
			}
		}
		
		return false;
	}
	
	static boolean contains(short[] array, Object value) {
		if(value == null) {
			return false;
		}
		
		if(value instanceof Number) {
			return contains(array, (Number) value);
		}
		
		return false;
	}
	
	static boolean contains(short[] array, Number value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		short val = value.shortValue();
		for(short item : array) {
			if(item == val) {
				return true;
			}
		}
		
		return false;
	}
	
	static boolean contains(int[] array, Object value) {
		if(value == null) {
			return false;
		}
		
		if(value instanceof Number) {
			return contains(array, (Number) value);
		}
		
		return false;
	}
	
	static boolean contains(int[] array, Number value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		int val = value.intValue();
		for(int item : array) {
			if(item == val) {
				return true;
			}
		}
		
		return false;
	}
	
	static boolean contains(long[] array, Object value) {
		if(value == null) {
			return false;
		}
		
		if(value instanceof Number) {
			return contains(array, (Number) value);
		}
		
		return false;
	}
	
	static boolean contains(long[] array, Number value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		long val = value.longValue();
		for(long item : array) {
			if(item == val) {
				return true;
			}
		}
		
		return false;
	}
	
	static boolean contains(float[] array, Object value) {
		if(value == null) {
			return false;
		}
		
		if(value instanceof Number) {
			return contains(array, (Number) value);
		}
		
		return false;
	}
	
	static boolean contains(float[] array, Number value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		float val = value.floatValue();
		for(float item : array) {
			if(item == val) {
				return true;
			}
		}
		
		return false;
	}
	
	static boolean contains(double[] array, Object value) {
		if(value == null) {
			return false;
		}
		
		if(value instanceof Number) {
			return contains(array, (Number) value);
		}
		
		return false;
	}
	
	static boolean contains(double[] array, Number value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		double val = value.doubleValue();
		for(double item : array) {
			if(item == val) {
				return true;
			}
		}
		
		return false;
	}
	
	static boolean contains(Object[] array, Object valueToCheck) {
		if(array == null) {
			return false;
		}
		
		if(array.length == 0) {
			return false;
		}
		
		for(Object item : array) {
			if(item == null) {
				continue;
			}
			
			if(item.equals(valueToCheck)) {
				return true;
			}
		}
		
		return false;
	}

	static boolean wildcardMatch(String string, String pattern) {
		int i = 0;
		int j = 0;
		int starIndex = -1;
		int iIndex = -1;

		while (i < string.length()) {
			if (j < pattern.length() && (pattern.charAt(j) == '?' || pattern.charAt(j) == string.charAt(i))) {
				++i;
				++j;
			} else if (j < pattern.length() && pattern.charAt(j) == '*') {
				starIndex = j;
				iIndex = i;
				j++;
			} else if (starIndex != -1) {
				j = starIndex + 1;
				i = iIndex+1;
				iIndex++;
			} else {
				return false;
			}
		}

		while (j < pattern.length() && pattern.charAt(j) == '*') {
			++j;
		}

		return j == pattern.length();
	}
	
	public static boolean regexMatch(String value, Pattern pattern) {
		if(pattern == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		Matcher matcher = pattern.matcher(value);
		if(matcher == null) {
			return false;
		}
		
		return matcher.matches();
	}

	public static boolean regexMatch(String value, String pattern) {
		if(pattern == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		Pattern compiled = COMPILED_PATTERNS.get(pattern);
		if(compiled == null) {
			compiled = Pattern.compile(pattern);
			COMPILED_PATTERNS.put(pattern, compiled);
		}
		
		return regexMatch(value, compiled);
	}

	public static boolean isNumberType(Object object) {
		if(object == null) {
			return false;
		}
		
		return contains(NUMBER_TYPES, object.getClass());
	}
	
	public static Number asNumber(Object object) {
		if(object == null) {
			return null;
		}
		
		if(!isNumberType(object)) {
			return null;
		}
		
		if(object instanceof AtomicLong) {
			return ((AtomicLong) object).get();
		} else if(object instanceof AtomicInteger) {
			return ((AtomicInteger) object).get();
		} else if(object instanceof Number) {
			return (Number) object;
		}
		
		return null;
	}
	
	static boolean containsAllOrAny(boolean[] array, Object value, boolean usingAllClause) {
		if(array == null) {
			return false;
		}
		
		if(array.length == 0) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof boolean[]) {
			boolean[] required = (boolean[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(boolean item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Boolean[]) {
			Boolean[] required = (Boolean[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(boolean item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Collection<?>) {
			Collection<?> required = (Collection<?>) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Object item : required) {
				boolean itemResult;
				if(item instanceof Boolean) {
					itemResult = contains(array, item);
				} else {
					itemResult = false;
				}

				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		return false;
	}
	
	static boolean containsAllOrAny(char[] array, Object value, boolean usingAllClause) {
		if(array == null) {
			return false;
		}
		
		if(array.length == 0) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof char[]) {
			char[] required = (char[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(char item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Character[]) {
			Character[] required = (Character[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Character item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Collection<?>) {
			Collection<?> required = (Collection<?>) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Object item : required) {
				boolean itemResult;
				if(item instanceof Character) {
					itemResult = contains(array, item);
				} else {
					itemResult = false;
				}

				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		return false;
	}
	
	static boolean containsAllOrAny(byte[] array, Object value, boolean usingAllClause) {
		if(array == null) {
			return false;
		}
		
		if(array.length == 0) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof byte[]) {
			byte[] required = (byte[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(byte item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Number[]) {
			Number[] required = (Number[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Number item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Collection<?>) {
			Collection<?> required = (Collection<?>) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Object item : required) {
				boolean itemResult;
				if(item instanceof Number) {
					itemResult = contains(array, item);
				} else {
					itemResult = false;
				}

				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		return false;
	}
	
	static boolean containsAllOrAny(short[] array, Object value, boolean usingAllClause) {
		if(array == null) {
			return false;
		}
		
		if(array.length == 0) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof short[]) {
			short[] required = (short[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(short item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Number[]) {
			Number[] required = (Number[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Number item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Collection<?>) {
			Collection<?> required = (Collection<?>) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Object item : required) {
				boolean itemResult;
				if(item instanceof Number) {
					itemResult = contains(array, item);
				} else {
					itemResult = false;
				}

				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		return false;
	}
	
	static boolean containsAllOrAny(int[] array, Object value, boolean usingAllClause) {
		if(array == null) {
			return false;
		}
		
		if(array.length == 0) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof int[]) {
			int[] required = (int[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(int item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Number[]) {
			Number[] required = (Number[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Number item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Collection<?>) {
			Collection<?> required = (Collection<?>) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Object item : required) {
				boolean itemResult;
				if(item instanceof Number) {
					itemResult = contains(array, item);
				} else {
					itemResult = false;
				}

				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		return false;
	}
	
	static boolean containsAllOrAny(long[] array, Object value, boolean usingAllClause) {
		if(array == null) {
			return false;
		}
		
		if(array.length == 0) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof long[]) {
			long[] required = (long[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(long item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Number[]) {
			Number[] required = (Number[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Number item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Collection<?>) {
			Collection<?> required = (Collection<?>) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Object item : required) {
				boolean itemResult;
				if(item instanceof Number) {
					itemResult = contains(array, item);
				} else {
					itemResult = false;
				}

				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		return false;
	}
	
	static boolean containsAllOrAny(float[] array, Object value, boolean usingAllClause) {
		if(array == null) {
			return false;
		}
		
		if(array.length == 0) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof float[]) {
			float[] required = (float[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(float item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Number[]) {
			Number[] required = (Number[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Number item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Collection<?>) {
			Collection<?> required = (Collection<?>) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Object item : required) {
				boolean itemResult;
				if(item instanceof Number) {
					itemResult = contains(array, item);
				} else {
					itemResult = false;
				}

				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		return false;
	}
	
	static boolean containsAllOrAny(double[] array, Object value, boolean usingAllClause) {
		if(array == null) {
			return false;
		}
		
		if(array.length == 0) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof double[]) {
			double[] required = (double[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(double item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Number[]) {
			Number[] required = (Number[]) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Number item : required) {
				boolean itemResult = contains(array, item);
				
				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		if(value instanceof Collection<?>) {
			Collection<?> required = (Collection<?>) value;
			
			boolean result;
			if(usingAllClause) {
				result = true;
			} else {
				result = false;
			}
			
			for(Object item : required) {
				boolean itemResult;
				if(item instanceof Number) {
					itemResult = contains(array, item);
				} else {
					itemResult = false;
				}

				if(usingAllClause) {
					result = result & itemResult;
				} else {
					result = result | itemResult;
				}
			}
			
			return result;
		}
		
		return false;
	}
	
}
