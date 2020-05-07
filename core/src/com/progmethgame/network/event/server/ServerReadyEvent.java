package com.progmethgame.network.event.server;

import java.util.UUID;

import com.progmethgame.network.ClientBusListener;
import com.progmethgame.network.event.base.ServerEvent;

/**
 * Signal that client should display the game now
 * @author pigt
 *
 */
public class ServerReadyEvent extends ServerEvent {

	/** ID that the server assigned to this client */
	public UUID assignedId;
	
	@Override
	public void notifyListener(ClientBusListener target) {
		target.onServerReady(assignedId);
	}

}
