package com.progmethgame.network.event.client;

import com.progmethgame.network.ServerBusListener;
import com.progmethgame.network.event.base.ClientEvent;

public class ClientDisconnectEvent extends ClientEvent {

	@Override
	public void notifyListener(ServerBusListener target) {
		target.onClientDisconnect(clientId);
	}

}
