package com.sangupta.gather;

/**
 * A single criteria in a {@link Gather} query.
 * 
 * @author sangupta
 *
 */
class GatherCriteria {

	public final String key;
	
	public final Object value;
	
	public final GatherOperation operation;
	
	public final GatherSiblingJoin join;
	
	public GatherCriteria(String key, GatherOperation operation, Object value, GatherSiblingJoin siblingJoin) {
		this.key = key;
		this.operation = operation;
		this.value = value;
		this.join = siblingJoin;
	}
	
}
