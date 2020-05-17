package com.progmethgame.server.entities;

/**
 * Interface on the a component that update with time.
 * 
 * @author pigt
 *
 */
public interface Tickable {
	
	/**
	 * Advance this component by delta second
	 * @param delta no. of second to advance
	 */
	public void tick(float delta);
}
