package com.progmethgame.common.network;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.progmethgame.command.EntityAddCommand;
import com.progmethgame.command.EntityMovementCommand;
import com.progmethgame.command.PlayerControlCommand;
import com.progmethgame.command.StartGameCommand;
import com.progmethgame.server.entities.Entity;

public class NetUtils {
	public static void registerKryo(Kryo k) {
		Class[] dataType = {
			ClientPacket.class,
			ServerPacket.class,
			StartGameCommand.class,
			ArrayList.class,
			EntityAddCommand.class,
			Entity.EntityType.class,
			EntityMovementCommand.class,
			PlayerControlCommand.class,
			Vector2.class
		};

		for (Class c : dataType) 
			k.register(c);
	}
}
