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
import java.util.Arrays;
import java.util.List;

class GatherReflect {

	static <T> Field getField(T item, String key) {
		if(item == null) {
			return null;
		}
		
		if(key == null || key.trim().isEmpty()) {
			return null;
		}
		
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
			return null;
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
        
        if(fields == null) {
        	throw new IllegalArgumentException("List to store fields in cannot be null");
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
