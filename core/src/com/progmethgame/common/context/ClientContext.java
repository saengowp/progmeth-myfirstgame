package com.progmethgame.common.context;

import java.util.Map;
import java.util.UUID;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.client.ClientEntity;
import com.progmethgame.network.ClientBus;

public interface ClientContext extends Disposable{
	
	public AssetManager getAssetManager();
	public ClientBus getNetworkBus();
	public void quit();
	
	/**
	 * Apply time step by delta seconds.
	 */
	public void tick(float delta);
	
	/**
	 * Return unmodifiable mapping of all entities.
	 */
	public Map<UUID, ClientEntity> getEntities();
	
	/**
	 * Return the UUID assigned by the server.
	 * 
	 * Note that Entity with the same UUID represent controllable character.
	 */
	public UUID getClientUUID();
}
