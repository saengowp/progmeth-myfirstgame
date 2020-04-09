package com.progmethgame.server;

import java.util.ArrayList;

public class NetworkPacket<T> {
	
	public ArrayList<T> eventsList;
	
	public NetworkPacket() {
		this.eventsList = new ArrayList<T>();
	}
}
