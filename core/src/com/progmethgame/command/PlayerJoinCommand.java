package com.progmethgame.command;

import com.esotericsoftware.kryonet.Connection;
import com.progmethgame.common.network.ServerCommand;
import com.progmethgame.server.GameRuntime;
import com.progmethgame.server.GameServer;

//This is actually fake command. It got send from within the server itself.

public class PlayerJoinCommand implements ServerCommand {

	public Connection connection;
	private GameServer server;
	
	public PlayerJoinCommand(Connection con, GameServer server) {
		this.connection = con;
		this.server = server;
	}
	
	@Override
	public void applyAction(GameRuntime target) {
		server.acceptConnection(connection);
	}

}
