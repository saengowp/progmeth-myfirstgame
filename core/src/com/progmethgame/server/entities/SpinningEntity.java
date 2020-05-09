package com.progmethgame.server.entities;

public class SpinningEntity extends TestEntity {

	public void tick(float delta) {
		this.facingDirection.rotate(delta * 90);
	};

}
