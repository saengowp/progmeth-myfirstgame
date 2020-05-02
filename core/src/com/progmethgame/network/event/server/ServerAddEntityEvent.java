package com.progmethgame.network.event.server;

import java.util.UUID;

import com.progmethgame.common.EntityData;
import com.progmethgame.network.ClientBusListener;
import com.progmethgame.network.event.base.ServerEvent;

public class ServerAddEntityEvent extends ServerEvent {

	public UUID entityId;
	public EntityData data;
	
	@Override
	public void notifyListener(ClientBusListener target) {
		target.onEntityAdd(entityId, data);
	}

}
