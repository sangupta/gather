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

/**
 *
 * @author sangupta
 *
 * @since 1.0.0
 */
interface GatherNumericComparison {

	static final GatherNumericComparison LESS_THAN = new GatherNumericComparison() {

		@Override
		public boolean test(int value) {
			if(value < 0) {
				return true;
			}

			return false;
		}

	};

	static final GatherNumericComparison LESS_THAN_OR_EQUALS = new GatherNumericComparison() {

		@Override
		public boolean test(int value) {
			if(value <= 0) {
				return true;
			}

			return false;
		}

	};

	static final GatherNumericComparison GREATER_THAN = new GatherNumericComparison() {

		@Override
		public boolean test(int value) {
			if(value > 0) {
				return true;
			}

			return false;
		}

	};

	static final GatherNumericComparison GREATER_THAN_OR_EQUALS = new GatherNumericComparison() {

		@Override
		public boolean test(int value) {
			if(value >= 0) {
				return true;
			}

			return false;
		}
	};

	public boolean test(int value);

}
