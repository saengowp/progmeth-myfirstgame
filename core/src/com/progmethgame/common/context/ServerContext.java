package com.progmethgame.common.context;

import java.util.Map;
import java.util.UUID;

import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.common.SoundType;
import com.progmethgame.server.entities.Entity;
import com.progmethgame.server.entities.Player;

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
	 * Get all players
	 * @return Map of UUID to Player
	 */
	public Map<UUID, Player> getPlayers();
}
