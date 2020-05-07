package com.progmethgame.network.event.client;

import com.progmethgame.network.ServerBusListener;
import com.progmethgame.network.event.base.ClientEvent;

/**
 * Client press fire button
 *
 */
public class PlayerFireEvent extends ClientEvent {

	@Override
	public void notifyListener(ServerBusListener target) {
		target.onPlayerFire(clientId);
	}

}
