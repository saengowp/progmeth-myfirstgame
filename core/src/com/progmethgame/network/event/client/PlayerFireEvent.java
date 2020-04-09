package com.progmethgame.network.event.client;

import com.progmethgame.network.ServerBusListener;
import com.progmethgame.network.event.base.ClientEvent;

public class PlayerFireEvent extends ClientEvent {

	@Override
	public void notifyListener(ServerBusListener target) {
		target.onPlayerFire(clientId);
	}

}
