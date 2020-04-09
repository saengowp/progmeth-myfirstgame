package com.progmethgame.network.event.server;

import java.util.UUID;

import com.progmethgame.network.ClientBusListener;
import com.progmethgame.network.event.base.ServerEvent;

public class ServerRemoveEntityEvent extends ServerEvent {

	public UUID entityId;
	
	@Override
	public void notifyListener(ClientBusListener target) {
		target.onEntityRemove(entityId);
	}

}
