package com.progmethgame.network.event.base;

import java.util.UUID;

import com.progmethgame.network.ServerBusListener;

/** Event emitted from game clients
 *
 */
public abstract class ClientEvent extends GameEvent<ServerBusListener> {
	
	/** ID of the client connection who emitted this event
	 */
	public UUID clientId;
	
}
