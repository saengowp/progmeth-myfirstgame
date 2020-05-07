package com.progmethgame.network.event.client;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.network.ServerBusListener;
import com.progmethgame.network.event.base.ClientEvent;

/**
 * Movement vector update
 *
 */
public class PlayerMovementEvent extends ClientEvent {
	
	public Vector2 movementVec;

	@Override
	public void notifyListener(ServerBusListener target) {
		target.onPlayerMove(clientId, movementVec);
	}

}
