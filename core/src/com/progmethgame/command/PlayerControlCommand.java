package com.progmethgame.command;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.common.network.ServerCommand;
import com.progmethgame.server.GameRuntime;

public class PlayerControlCommand implements ServerCommand {

	public int playerId;
	public Vector2 movementVec;

	@Override
	public void applyAction(GameRuntime target) {
		target.onPlayerMove(playerId, movementVec);
	}

}
