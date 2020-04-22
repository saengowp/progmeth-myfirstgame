package com.progmethgame.network.event.client;

import com.progmethgame.network.ServerBusListener;
import com.progmethgame.network.event.base.ClientEvent;

public class PlayerSwapGunEvent extends ClientEvent {

	@Override
	public void notifyListener(ServerBusListener target) {
		target.onPlayerSwapGun(clientId);
	}

}
