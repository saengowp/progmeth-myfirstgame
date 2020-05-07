package com.progmethgame.common.context;

import java.util.Map;
import java.util.UUID;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.client.ClientEntity;
import com.progmethgame.network.ClientBus;

/**
 * Client's context. Provides client's data interface and action
 */
public interface ClientContext extends Disposable{
	
	/**
	 * Get AssetManager
	 * @return
	 */
	public AssetManager getAssetManager();
	
	/**
	 * Get client-server's communication bus.
	 * @return
	 */
	public ClientBus getNetworkBus();
	
	/**
	 * Quit the game and return to main screen.
	 */
	public void quit();
	
	/**
	 * Apply time step by delta seconds.
	 */
	public void tick(float delta);
	
	/**
	 * Return unmodifiable list of all entities.
	 */
	public Map<UUID, ClientEntity> getEntities();
	
	/**
	 * Return the UUID assigned by the server.
	 * 
	 * Note that Entity with the same UUID represent controllable character.
	 */
	public UUID getClientUUID();
}
