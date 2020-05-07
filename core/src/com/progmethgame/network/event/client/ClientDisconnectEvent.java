package com.progmethgame.network.event.client;

import com.progmethgame.network.ServerBusListener;
import com.progmethgame.network.event.base.ClientEvent;

/**
 * Virtual event for when the client disconnect or lose connection to the server
 *
 */
public class ClientDisconnectEvent extends ClientEvent {

	@Override
	public void notifyListener(ServerBusListener target) {
		target.onClientDisconnect(clientId);
	}

}
