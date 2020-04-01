package com.progmethgame.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Player extends Sprite implements Disposable {
	
	Texture texture;
	Vector2 velocity;
	Vector2 controlVec;
	Vector2 prvPos;
	
	public Player() {
		this.texture = new Texture("player.png");
		this.setRegion(texture);
		this.setSize(1, 1);
		this.setPosition(1, 1);
		
		this.velocity = new Vector2();
		this.controlVec = new Vector2();
		this.prvPos = new Vector2();
	}
	
	public void normalizeMovement() {
		float walkSpeed = 5f;
		velocity = controlVec.cpy().nor().scl(walkSpeed);
	}

	@Override
	public void dispose() {
		this.texture.dispose();
	}

}
