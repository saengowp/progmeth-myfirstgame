package com.progmethgame.network.event.client;

import com.progmethgame.network.ServerBusListener;
import com.progmethgame.network.event.base.ClientEvent;

/**
 * Client issued debug command event
 *
 */
public class ClientDebugEvent extends ClientEvent {

	/** Debug command */
	private String debugMsg;
	
	private ClientDebugEvent() {
		this("");
	}
	
	/**
	 *  Create new debug event with specified command
	 * 
	 * @param msg command
	 */
	public ClientDebugEvent(String msg) {
		this.debugMsg = msg;
	}
	
	@Override
	public void notifyListener(ServerBusListener target) {
		target.onDebug(clientId, debugMsg);
	}

}
