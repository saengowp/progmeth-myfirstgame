package com.progmethgame.network.event.client;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.network.ServerBusListener;
import com.progmethgame.network.event.base.ClientEvent;

/**
 * Movement vector update
 *
 */
public class PlayerMovementEvent extends ClientEvent {
	
	private Vector2 movementVec;
	
	private PlayerMovementEvent() {}
	
	public PlayerMovementEvent(Vector2 movementVec) {
		this.movementVec = movementVec;
	}

	@Override
	public void notifyListener(ServerBusListener target) {
		target.onPlayerMove(clientId, movementVec);
	}

}
