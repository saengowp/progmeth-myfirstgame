package com.progmethgame.server.entities.bullets;

import com.badlogic.gdx.math.Vector2;
import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;

public class TeleportBullet extends Bullet {

	public TeleportBullet(Player owner) {
		super(DisplayType.BULLET_TELEPORT, BulletConfig.TELEPORT_BULLET_SPEED, owner);
		// TODO Auto-generated constructor stub
	}
	
	public void onCollide(Player hitPlayer) {
		//copy position
		Vector2 ownerPosition = owner.getPosition().cpy();
		Vector2 hitPosition = hitPlayer.getPosition().cpy();
		//swap position
		hitPlayer.getPosition().set(ownerPosition);
		owner.getPosition().set(hitPosition);
	}
}
