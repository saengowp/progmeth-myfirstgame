package com.progmethgame.network;

import java.util.UUID;

import com.progmethgame.common.EntityData;
import com.progmethgame.common.SoundType;

public interface ClientBusListener {
	public void onEntityAdd(UUID id, EntityData data);
	public void onEntityRemove(UUID id);
	public void onEntityUpdate(EntityData data);
	public void onPlaySound(SoundType sound);
	
	/** Always call after server finish sending entity and ready to display data.
	 * @param assignedId ID assigned by the server
	 * 
	 */
	public void onServerReady(UUID assignedId);
	/** Always called first when client connect or level reset
	 * 
	 */
	public void onServerReset();
	
	/** Called when server disconnect eg. Connection lost, Server closed
	 * 
	 */
	public void onDisconnect();
}
