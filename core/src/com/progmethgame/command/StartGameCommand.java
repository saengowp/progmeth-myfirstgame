package com.progmethgame.command;

import com.progmethgame.client.GameClient;
import com.progmethgame.common.network.ClientCommand;
import com.progmethgame.common.network.ServerCommand;
import com.progmethgame.server.GameRuntime;

public class StartGameCommand implements ClientCommand {
	
	public int userid;

	@Override
	public void applyAction(GameClient target) {
		target.startGame(userid);
	}

}
