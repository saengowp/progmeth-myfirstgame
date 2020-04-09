package com.progmethgame.network;

import java.util.ArrayList;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.progmethgame.common.DisplayType;
import com.progmethgame.network.event.base.ClientEvent;
import com.progmethgame.network.event.base.GameEvent;
import com.progmethgame.network.event.base.ServerEvent;
import com.progmethgame.network.event.client.ClientDisconnectEvent;
import com.progmethgame.network.event.client.ClientJoinEvent;
import com.progmethgame.network.event.client.PlayerFireEvent;
import com.progmethgame.network.event.client.PlayerMovementEvent;
import com.progmethgame.network.event.server.ServerAddEntityEvent;
import com.progmethgame.network.event.server.ServerReadyEvent;
import com.progmethgame.network.event.server.ServerRemoveEntityEvent;
import com.progmethgame.network.event.server.ServerResetEvent;
import com.progmethgame.network.event.server.ServerUpdateEntityEvent;
import com.progmethgame.server.NetworkPacket;
import com.progmethgame.server.entities.Entity;

public class SerializationUtil {
	public static void registerKryo(Kryo k) {
		Class[] dataType = {
			NetworkPacket.class,
			ArrayList.class,
			ClientEvent.class,
			ServerEvent.class,
			GameEvent.class,
			ClientDisconnectEvent.class,
			ClientJoinEvent.class,
			PlayerMovementEvent.class,
			ServerAddEntityEvent.class,
			ServerReadyEvent.class,
			ServerRemoveEntityEvent.class,
			ServerResetEvent.class,
			ServerUpdateEntityEvent.class,
			EntityData.class,
			DisplayType.class,
			Vector2.class,
			PlayerFireEvent.class
		};

		for (Class c : dataType) 
			k.register(c);
		
		k.register(UUID.class, new UUIDSerializer());
	}
}

class UUIDSerializer extends Serializer<UUID> {

	@Override
	public void write(Kryo kryo, Output output, UUID object) {
		output.writeString(object.toString());
	}

	@Override
	public UUID read(Kryo kryo, Input input, Class<UUID> type) {
		return UUID.fromString(input.readString());
	}
	
}
