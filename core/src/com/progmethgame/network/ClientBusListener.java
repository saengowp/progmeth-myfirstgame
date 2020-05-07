package com.progmethgame.network;

import java.util.UUID;

import com.progmethgame.common.EntityData;
import com.progmethgame.common.SoundType;

/**
 * Client bus's event listener
 */
public interface ClientBusListener {
	
	/**
	 * Server added an entity
	 * @param id entity's id
	 * @param data entity's data
	 */
	public void onEntityAdd(UUID id, EntityData data);
	
	/**
	 * Server remove an entity
	 * @param id entity's id
	 */
	public void onEntityRemove(UUID id);
	
	/**
	 * Server update entity's data
	 * @param data update data
	 */
	public void onEntityUpdate(EntityData data);
	
	/**
	 * Server wants to play a sound
	 * @param sound
	 */
	public void onPlaySound(SoundType sound);
	
	/** 
	 * Server have initialized the client and is now ready.
	 * 
	 * Always call after server finished sending entities and ready to display data.
	 * @param assignedId ID assigned by the server
	 * 
	 */
	public void onServerReady(UUID assignedId);
	
	/** 
	 * Server instruct client to clear all game state.
	 * 
	 * Always called first when client connect or level reset
	 * 
	 */
	public void onServerReset();
	
	/** 
	 * Called when server disconnect eg. Connection lost, Server closed
	 * 
	 */
	public void onDisconnect();
}
