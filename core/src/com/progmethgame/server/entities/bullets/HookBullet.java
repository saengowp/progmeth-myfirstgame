package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;

public class HookBullet extends Bullet {
	
	public static final float hookSpeed = BulletConfig.HOOK_SPEED;

	public HookBullet(Player owner) {
		super(DisplayType.BULLET_HOOK , BulletConfig.HOOK_BLLET_SPEED, owner);
		// TODO Auto-generated constructor stub
	}
	
	public void onCollide(Player hitPlayer) {
		//set velocity to hooked player
		hitPlayer.setVelocity(( owner.getPosition().cpy().add(hitPlayer.getPosition().cpy().scl(-1)).nor().scl(hookSpeed)));
	}
}