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

import java.util.HashSet;
import java.util.Set;

interface GatherAggregator {
	
	public void aggregate(int index, Object value);
	
	public Number getResult(int counted);
	
	// ***************************************
	// DEFAULT IMPLEMENTATIONS FOLLOW
	// ***************************************
	
	static class UniqueAggregator implements GatherAggregator {
		
		final Set<Object> set = new HashSet<>();

		@Override
		public void aggregate(int index, Object value) {
			if(value == null) {
				return;
			}
			
			this.set.add(value);
		}

		@Override
		public Number getResult(int counted) {
			return this.set.size();
		}
		
		public Set<Object> getUniqueSet() {
			return this.set;
		}
		
	}
	
	static class CountingAggregator implements GatherAggregator {
		
		int count = 0;
		
		@Override
		public void aggregate(int index, Object value) {
			count++;
		}
		
		@Override
		public Number getResult(int counted) {
			return this.count;
		}
	}
	
	static class DoubleMinAggregator implements GatherAggregator {
		
		double result = Double.MAX_VALUE;
		
		@Override
		public void aggregate(int index, Object value) {
			double fieldValue;
			
			if(value instanceof Number) {
				fieldValue = ((Number) value).doubleValue();
			} else {
				throw new IllegalArgumentException("Field type is not numeric");
			}

			if(result > fieldValue) {
				result = fieldValue;
			}
		}
		
		@Override
		public Number getResult(int counted) {
			return this.result;
		}
	}
	
	static class DoubleSumAggregator implements GatherAggregator {
		
		double result = 0;
		
		@Override
		public void aggregate(int index, Object value) {
			double fieldValue;
			
			if(value instanceof Number) {
				fieldValue = ((Number) value).doubleValue();
			} else {
				throw new IllegalArgumentException("Field type is not numeric");
			}

			result += fieldValue;
		}
		
		@Override
		public Number getResult(int counted) {
			return this.result;
		}
	}
	
	static class DoubleAverageAggregator implements GatherAggregator {
		
		double result = 0;
		
		int count = 0;
		
		@Override
		public void aggregate(int index, Object value) {
			double fieldValue;
			
			if(value instanceof Number) {
				fieldValue = ((Number) value).doubleValue();
			} else {
				throw new IllegalArgumentException("Field type is not numeric");
			}

			result += fieldValue;
			count++;
		}
		
		@Override
		public Number getResult(int counted) {
			if(this.count == 0) {
				return 0d;
			}
			
			return (double) this.result / (double) this.count;
		}
	}
	
	static class DoubleMaxAggregator implements GatherAggregator {
		
		double result = Double.MIN_VALUE;
		
		@Override
		public void aggregate(int index, Object value) {
			double fieldValue;
			
			if(value instanceof Number) {
				fieldValue = ((Number) value).doubleValue();
			} else {
				throw new IllegalArgumentException("Field type is not numeric");
			}

			if(result < fieldValue) {
				result = fieldValue;
			}
		}
		
		@Override
		public Number getResult(int counted) {
			return this.result;
		}
	}
	
	static class LongMinAggregator implements GatherAggregator {
		
		long result = Long.MAX_VALUE;
		
		@Override
		public void aggregate(int index, Object value) {
			long fieldValue;
			
			if(value instanceof Number) {
				fieldValue = ((Number) value).longValue();
			} else {
				throw new IllegalArgumentException("Field type is not numeric");
			}

			if(result > fieldValue) {
				result = fieldValue;
			}
		}
		
		@Override
		public Number getResult(int counted) {
			return this.result;
		}
	}
	
	static class LongMaxAggregator implements GatherAggregator {
		
		long result = Long.MIN_VALUE;
		
		@Override
		public void aggregate(int index, Object value) {
			long fieldValue;
			
			if(value instanceof Number) {
				fieldValue = ((Number) value).longValue();
			} else {
				throw new IllegalArgumentException("Field type is not numeric");
			}

			if(result < fieldValue) {
				result = fieldValue;
			}
		}
		
		@Override
		public Number getResult(int counted) {
			return this.result;
		}
	}
	
	static class LongSumAggregator implements GatherAggregator {
		
		long result = 0;
		
		@Override
		public void aggregate(int index, Object value) {
			long fieldValue;
			
			if(value instanceof Number) {
				fieldValue = ((Number) value).longValue();
			} else {
				throw new IllegalArgumentException("Field type is not numeric");
			}

			result += fieldValue;
		}
		
		@Override
		public Number getResult(int counted) {
			return this.result;
		}
	}
	
	static class LongAverageAggregator implements GatherAggregator {
		
		long result = 0;
		
		int count = 0;
		
		@Override
		public void aggregate(int index, Object value) {
			long fieldValue;
			
			if(value instanceof Number) {
				fieldValue = ((Number) value).longValue();
			} else {
				throw new IllegalArgumentException("Field type is not numeric");
			}

			result += fieldValue;
			count++;
		}
		
		/**
		 * @param counted the parameter value is not used and ignored
		 */
		@Override
		public Number getResult(int counted) {
			if(this.count == 0) {
				return 0d;
			}
			
			return this.result / this.count;
		}
	}
}
