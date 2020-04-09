package com.progmethgame.command;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.client.GameClient;
import com.progmethgame.common.network.ClientCommand;

public class EntityMovementCommand implements ClientCommand {
	
	public int gid;
	public Vector2 position;

	@Override
	public void applyAction(GameClient target) {
		target.setEntityPos(gid, position);
	}

}
