package com.sangupta.gather;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GatherReflect {

	static <T> Field getField(T item, String key) {
		Class<?> classOfT = item.getClass();
		List<Field> fields = getAllFields(classOfT);
		if(fields == null || fields.isEmpty()) {
			return null;
		}
		
		// TODO: optimize this
		for(Field field : fields) {
			if(field.getName().equals(key)) {
				return field;
			}
		}
		
		return null;
	}

	static List<Field> getAllFields(Class<?> clazz) {
		if(clazz == null) {
			throw new IllegalArgumentException("Class to read fields from cannot be null");
		}
		
		// TODO: implement caching for faster retrievals
        List<Field> fields = new ArrayList<>();
        populateAllFields(clazz, fields);
        return fields;
    }
    
    static void populateAllFields(Class<?> clazz, List<Field> fields) {
        if(clazz == null) {
            return;
        }
        
        Field[] array = clazz.getDeclaredFields();
        if(array != null && array.length > 0) {
            fields.addAll(Arrays.asList(array));
        }
        
        if(clazz.getSuperclass() == null) {
            return;
        }
        
        populateAllFields(clazz.getSuperclass(), fields);
    }
    
}
