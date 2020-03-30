package com.progmethgame.graphic;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class RenderComponent implements Disposable{
	
	Texture texture;
	public Vector2 position;
	public float rotation; //Deg
	
	public RenderComponent(FileHandle texturePath, Vector2 position) {
		texture = new Texture(texturePath);
		this.position = position;
		this.rotation = 0;
	}
	
	public void render(Batch batch) {
		batch.draw(texture, position.x, position.y, 0.5f, 0.5f, 1f, 1f, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
	
	
	
}
