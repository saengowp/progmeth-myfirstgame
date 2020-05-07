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

	/** id of the added entity */
	public UUID entityId;
	
	/** data of the added entity */
	public EntityData data;
	
	@Override
	public void notifyListener(ClientBusListener target) {
		target.onEntityAdd(entityId, data);
	}

}
