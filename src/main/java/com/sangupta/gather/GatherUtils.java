package com.sangupta.gather;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility functions for the Gather library.
 *  
 * @author sangupta
 *
 */
class GatherUtils {
	
	final static Map<String, Pattern> COMPILED_PATTERNS = new HashMap<>(); 

	static boolean contains(byte[] array, Object value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof Byte) {
			byte val = (Byte) value;
			for(byte item : array) {
				if(item == val) {
					return true;
				}
			}
		}
		
		return false;
	}
	
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
	
	static boolean contains(short[] array, Object value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof Short) {
			short val = (Short) value;
			for(short item : array) {
				if(item == val) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	static boolean contains(int[] array, Object value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof Integer) {
			int val = (Integer) value;
			for(int item : array) {
				if(item == val) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	static boolean contains(long[] array, Object value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof Long) {
			long val = (Long) value;
			for(long item : array) {
				if(item == val) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	static boolean contains(float[] array, Object value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof Float) {
			float val = (Float) value;
			for(float item : array) {
				if(item == val) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	static boolean contains(double[] array, Object value) {
		if(array == null) {
			return false;
		}
		
		if(value == null) {
			return false;
		}
		
		if(value instanceof Double) {
			double val = (Double) value;
			for(double item : array) {
				if(item == val) {
					return true;
				}
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
		
		Matcher matcher = compiled.matcher(value);
		if(matcher == null) {
			return false;
		}
		
		return matcher.matches();
	}

}
