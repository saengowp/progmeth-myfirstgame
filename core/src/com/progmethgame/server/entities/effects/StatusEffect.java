package com.progmethgame.server.entities.effects;

import com.progmethgame.common.DisplayType;
import com.progmethgame.server.entities.Player;

/**
 * Represent an "effect" that can be attached to the player
 * @author pigt
 *
 */
public abstract class StatusEffect {
	protected int duration;
	protected int maxDuration;
	
	protected DisplayType dispType;
	
	public StatusEffect(DisplayType dispType) {
		this.dispType = dispType;
	}
	
	/** Getter for status effect's icon */
	public DisplayType getDisplayType() {
		return this.dispType;
	}
	
	/** Max duration */
	public int getMaxDuration() {
		return maxDuration;
	}
	
	/** Apply effect to the player */
	public abstract void getEffect(Player p);
	
	/** Remove effect from the player */
	public abstract void removeEffect(Player p);
	
	/** Duration left*/
	public int getDuration() {
		return duration;
	}
	
	/** Decrease the effect's duration */
	public boolean decreaseDuration() {
		this.duration -= 1;
		return this.duration <= 0;			
	}
	
	/** Reset the effect's duration */
	public void resetDuration() {
		this.duration = maxDuration;
	}
	
	/** Clone this effect */
	public abstract StatusEffect cpy();
	
}
