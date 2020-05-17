package com.progmethgame.network.event.server;

import java.util.UUID;

import com.progmethgame.common.EntityData;
import com.progmethgame.network.ClientBusListener;
import com.progmethgame.network.event.base.ServerEvent;

/**
 * Server added an entity
 *
 */
public class ServerAddEntityEvent extends ServerEvent {
	
	/** data of the added entity */
	private EntityData data;
	
	public ServerAddEntityEvent(EntityData data) {
		this.data = data;
	}
	
	private ServerAddEntityEvent() {
	}
	
	@Override
	public void notifyListener(ClientBusListener target) {
		target.onEntityAdd(data);
	}

}
