package com.progmethgame.physic;

import com.badlogic.gdx.math.Vector2;

public class PhysicalComponent {
	public Vector2 position;
	public Vector2 prvPos;
	public Vector2 velocity;
	public float mass;
	
	public PhysicalComponent(Vector2 position, float mass) {
		this.position = position;
		this.mass = mass;
		this.velocity = new Vector2(0, 0);
		this.prvPos = new Vector2(position);
	}

}
