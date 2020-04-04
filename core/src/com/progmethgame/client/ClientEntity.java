package com.progmethgame.client;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.progmethgame.server.entities.Entity.EntityType;

public class ClientEntity extends Sprite{
	
	private int gid;
	private EntityType type;
	
	public ClientEntity(int gid, EntityType type) {
		this.gid = gid;
		this.type = type;
		
		String texturePath = null;
		switch (type) {
		case PLAYER_CONTROLABLE:
			
			//Fallthrough
		case PLAYER:
			texturePath = "player.png";
			break;

		default:
			break;
		}
		
		setRegion(new Texture(texturePath));
		setSize(1, 1);
	}
	
	public int getGid() {
		return gid;
	}
	
	public EntityType getType() {
		return type;
	}
}
