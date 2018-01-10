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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Collection of Java Reflection methods that are used within {@link Gather}
 * framework.
 *
 * @author sangupta
 *
 * @since 1.0.0
 */
abstract class GatherReflect {

	static final Map<Class<?>, Map<String, Field>> CLASS_FIELD_KEY_CACHE = new HashMap<>();

	static class FieldAndInstance {

		final Field field;

		final Object instance;

		FieldAndInstance(Field field, Object instance) {
			this.field = field;
			this.instance = instance;
		}
	}

	static <T> FieldAndInstance getFieldAndInstance(T item, String key) {
		if (item == null) {
			return null;
		}

		if (key == null) {
			return null;
		}

		if (!key.contains(".")) {
			// this is a plain request
			return new FieldAndInstance(getField(item, key), item);
		}

		// this is a composed object
		String[] tokens = key.split("\\.");

		Object instance = item;
		Field field = null;

		for (int index = 0; index < tokens.length; index++) {
			String token = tokens[index];
			field = getField(instance, token);
			if (field == null) {
				return null;
			}

			if (index == tokens.length - 1) {
				// this is the last token
				return new FieldAndInstance(field, instance);
			}

			field.setAccessible(true);

			try {
				instance = field.get(instance);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException("Unable to get hold of field from class");
			}
		}

		return null;
	}

	static <T> Field getField(T item, String key) {
		if (item == null) {
			return null;
		}

		if (key == null || key.trim().isEmpty()) {
			return null;
		}

		Map<String, Field> classCache = CLASS_FIELD_KEY_CACHE.get(item.getClass());
		if (classCache != null) {
			return classCache.get(key);
		}

		if (key.contains(".")) {
			// TODO: this is a composed object
			return null;
		}

		// class based cache does not exist
		Map<String, Field> fieldCache = new HashMap<>();
		CLASS_FIELD_KEY_CACHE.put(item.getClass(), fieldCache);

		Class<?> classOfT = item.getClass();
		List<Field> fields = getAllFields(classOfT);
		if (fields == null || fields.isEmpty()) {
			return null;
		}

		for (Field field : fields) {
			fieldCache.put(field.getName(), field);
		}

		return fieldCache.get(key);
	}

	static List<Field> getAllFields(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		List<Field> fields = new ArrayList<>();
		populateAllFields(clazz, fields);

		return fields;
	}

	static void populateAllFields(Class<?> clazz, List<Field> fields) {
		if (clazz == null) {
			return;
		}

		if (fields == null) {
			throw new IllegalArgumentException("List to store fields in cannot be null");
		}

		Field[] array = clazz.getDeclaredFields();
		if (array != null && array.length > 0) {
			fields.addAll(Arrays.asList(array));
		}

		if (clazz.getSuperclass() == null) {
			return;
		}

		populateAllFields(clazz.getSuperclass(), fields);
	}

}
