package com.progmethgame.network.event.server;

import java.util.UUID;

import com.progmethgame.network.ClientBusListener;
import com.progmethgame.network.event.base.ServerEvent;

public class ServerReadyEvent extends ServerEvent {

	public UUID assignedId;
	
	@Override
	public void notifyListener(ClientBusListener target) {
		target.onServerReady(assignedId);
	}

}
