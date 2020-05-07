package com.progmethgame.network.event.server;

import com.progmethgame.network.ClientBusListener;
import com.progmethgame.network.event.base.ServerEvent;

/** 
 * Server request client to reset its world state
 * 
 */
public class ServerResetEvent extends ServerEvent {

	@Override
	public void notifyListener(ClientBusListener target) {
		target.onServerReset();
	}

}
