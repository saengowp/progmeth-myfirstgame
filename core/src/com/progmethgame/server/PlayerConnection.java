package com.progmethgame.server;

import com.esotericsoftware.kryonet.Connection;

public class PlayerConnection {
	
	private Connection connection;
	private int playerid;
	
	public PlayerConnection(Connection connection, int playerid) {
		this.connection = connection;
		this.playerid = playerid;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public int getPlayerId() {
		return playerid;
	}
}
