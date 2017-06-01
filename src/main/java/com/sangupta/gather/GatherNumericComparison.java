package com.sangupta.gather;

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
