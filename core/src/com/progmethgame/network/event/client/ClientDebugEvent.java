package com.progmethgame.network.event.client;

import com.progmethgame.network.ServerBusListener;
import com.progmethgame.network.event.base.ClientEvent;

public class ClientDebugEvent extends ClientEvent {

	public String debugMsg;
	
	public ClientDebugEvent() {
		this("");
	}
	
	public ClientDebugEvent(String msg) {
		this.debugMsg = msg;
	}
	
	@Override
	public void notifyListener(ServerBusListener target) {
		target.onDebug(clientId, debugMsg);
	}

}
