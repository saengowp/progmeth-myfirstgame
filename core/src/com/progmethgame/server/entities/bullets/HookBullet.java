package com.progmethgame.server.entities.bullets;

import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.effects.Stunt;

public class HookBullet extends Bullet {
	
	public static final float hookSpeed = BulletConfig.HOOK_SPEED;

	public HookBullet(Player owner) {
		super(DisplayType.BULLET_HOOK , BulletConfig.HOOK_BLLET_SPEED, owner);
		// TODO Auto-generated constructor stub
	}
	
	public void onCollide(Player hitPlayer) {
		//set velocity to hooked player
		hitPlayer.setEffect(new Stunt());

		hitPlayer.getVelocity().set((owner.getPosition().cpy().add(hitPlayer.getPosition().cpy().scl(-1)).nor().scl(hookSpeed)));
		GameContext.getServerContext().removeEntity(this);
	}

	@Override
	public Bullet cpy() {
		// TODO Auto-generated method stub
		return new HookBullet(owner);
	}
}
