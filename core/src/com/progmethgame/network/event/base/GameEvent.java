package com.progmethgame.network.event.base;

/** Game's network event
 *
 */
public abstract class GameEvent<T> {
	
	/** Notify the listener of this event
	 */
	public abstract void notifyListener(T target);
	
}
