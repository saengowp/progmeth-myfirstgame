package com.progmethgame.common.context;

import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.common.SoundType;
import com.progmethgame.server.entities.Entity;

/**
 * Provides methods for interacting with server runtime
 */
public interface ServerContext extends Disposable{
	
	/**
	 * Add new entity
	 * @param e Entity to be added
	 */
	public void addEntity(Entity e);
	
	/**
	 * Remove the entity
	 * @param e
	 */
	public void removeEntity(Entity e);
	
	/**
	 * Play sound effect on all clients
	 * @param s
	 */
	public void playSound(SoundType s);
	
	/**
	 * Reset and start a new game
	 */
	public void reset();
	
	/**
	 * Check if have a player alive
	 */
	public void checkWinCondition();
}
