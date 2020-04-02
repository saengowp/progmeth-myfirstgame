package com.progmethgame.command;

import com.progmethgame.client.ClientEntity;
import com.progmethgame.client.GameClient;
import com.progmethgame.common.network.ClientCommand;
import com.progmethgame.server.entities.Entity.EntityType;

public class EntityAddCommand implements ClientCommand {
	
	public int gid;
	public EntityType type;

	@Override
	public void applyAction(GameClient target) {
		ClientEntity e = new ClientEntity(gid, type);
		target.addEntity(e);
	}

}
