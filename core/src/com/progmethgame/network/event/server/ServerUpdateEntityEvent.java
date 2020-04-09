package com.progmethgame.network.event.server;

import com.progmethgame.network.ClientBusListener;
import com.progmethgame.network.EntityData;
import com.progmethgame.network.event.base.ServerEvent;

public class ServerUpdateEntityEvent extends ServerEvent {

	public EntityData data;
	
	@Override
	public void notifyListener(ClientBusListener target) {
		target.onEntityUpdate(data);
		
	}

}
