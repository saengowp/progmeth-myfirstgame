package com.progmethgame.common;

public class PlayerStatus {
	public int health;
	public DisplayType gunIcon;

	public PlayerStatus() {
		//Default value
		this.health = 0;
		this.gunIcon = DisplayType.TEST;
	}
}
