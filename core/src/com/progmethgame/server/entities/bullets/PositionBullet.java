package com.progmethgame.server.entities.bullets;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.server.entities.Player;

public class PositionBullet extends Bullet {
	private Player Owner;
	
	public PositionBullet(int gid, EntityType type, float speed, Player Owner) {
		super(gid, type, speed);
		// TODO Auto-generated constructor stub
		this.Owner = Owner;
		switch(type) {
		case BULLET_HOOK:
			this.speed = 9.0f;
			break;
		case BULLET_TELEPORT:
			this.speed = 9.0f;
			break;
		}
	}
	
	public void onHit(Player hitPlayer) {
		switch(type) {
		case BULLET_HOOK:
			float moveSpeed = 20f;
			float delta = 1/20f;
			hitPlayer.moveWithSpeed(moveSpeed, delta);
			break;
		case BULLET_TELEPORT:
			Vector2 OwnerPosition = Owner.position.cpy();
			Vector2 hitPosition = hitPlayer.position.cpy();
			
			Owner.position = hitPosition;
			hitPlayer.position = OwnerPosition;
			break;
		}
	}

}
