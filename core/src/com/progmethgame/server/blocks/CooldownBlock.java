package com.progmethgame.server.blocks;

import com.progmethgame.common.GameConfig;
import com.progmethgame.server.entities.Player;

/**
 * A block that have "cooldown" on its effect
 * @author pigt
 *
 */
public abstract class CooldownBlock extends Block implements Interactable {

	/** Number of second before block is active again */
	private int cooldown;
	
	/** Number of tick after last cooldown decrement */
	private int tickCount;
	
	/** Cooldown interval (second) */
	protected static final int maxCooldown = 5;
	
	public CooldownBlock() {
		super();
		this.cooldown = 0;
		this.tickCount = 0;
	}
	
	@Override
	public void interact(Player p) {
		if(cooldown <= 0) {
			applyEffect(p);
			cooldown = maxCooldown;
		}
	}
	
	@Override
	public void tick(float delta) {
		// TODO Auto-generated method stub
		if (tickCount == 0) {
			if (cooldown > 0) this.cooldown -= 1;
		}
		tickCount++;
		tickCount %= (int) 1/GameConfig.SERVER_TICK_RATE;
	}
	
	protected abstract void applyEffect(Player p);
	
}
