package com.sangupta.gather;

class GatherUtils {

	static boolean contains(Object[] array, Object fieldValue) {
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
			
			if(item.equals(fieldValue)) {
				return true;
			}
		}
		
		return false;
	}

}
