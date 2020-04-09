package com.progmethgame.client;

import java.util.UUID;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.progmethgame.common.DisplayType;
import com.progmethgame.network.EntityData;

public class ClientEntity extends Sprite{
	
	private UUID gid;
	private DisplayType type;
	private ClientRuntime runtime;
	
	public ClientEntity(UUID gid, DisplayType type, ClientRuntime runtime) {
		this.gid = gid;
		this.type = type;
		this.runtime = runtime;
		
		updateTexture();
	}
	
	public UUID getGid() {
		return gid;
	}
	
	public DisplayType getType() {
		return type;
	}
	
	private void updateTexture() {
		String texpath = "";
		switch (type) {
		case PLAYER:
			texpath = "player.png";
			break;
		}
		
		setRegion(runtime.getAssetManaget().get(texpath, Texture.class));
		setSize(1, 1);
	}
	
	public void update(EntityData data) {
		this.gid = data.id;
		this.type = data.dispType;
		this.setPosition(data.position.x, data.position.y);
		
		updateTexture();
	}
	
	public static ClientEntity fromData(EntityData data, ClientRuntime runtime) {
		ClientEntity e = new ClientEntity(data.id, data.dispType, runtime);
		return e;
	}
}
