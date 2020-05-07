package com.progmethgame.network.event.server;

import com.progmethgame.common.EntityData;
import com.progmethgame.network.ClientBusListener;
import com.progmethgame.network.event.base.ServerEvent;

/**
 * Entity's data got updated
 * @author pigt
 *
 */
public class ServerUpdateEntityEvent extends ServerEvent {

	/** Updated data */
	public EntityData data;
	
	@Override
	public void notifyListener(ClientBusListener target) {
		target.onEntityUpdate(data);
		
	}

}
