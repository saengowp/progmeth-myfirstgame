package com.progmethgame.network;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

/**
 * Event listener for the server bus
 */
public interface ServerBusListener {
	
	/**
	 * Bus finished processing the event. 
	 * @param delta time since last onTick
	 */
	public void onTick(float delta);
	
	/**
	 * Client connected to the server
	 * @param id client's id
	 */
	public void onClientJoin(UUID id);
	
	/**
	 * Client disconnected from the server
	 * @param id client's id
	 */
	public void onClientDisconnect(UUID id);
	
	/**
	 * Client's controller request vector changes.
	 * @param id client's id
	 * @param direction new movement vector
	 */
	public void onPlayerMove(UUID id, Vector2 direction);
	
	/**
	 * Client request gun fire action.
	 * @param id client's id
	 */
	public void onPlayerFire(UUID id);
	
	/**
	 * Client issue debug command to the server
	 * @param id client's id
	 * @param debugMsg debug command
	 */
	public void onDebug(UUID id, String debugMsg);
	
	/**
	 * Client request gun change.
	 * @param id client's id
	 */
	public void onPlayerSwapGun(UUID id);
}
