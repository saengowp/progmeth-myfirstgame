package com.progmethgame.entity.component;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class RenderComponent implements Disposable{
	
	Texture texture;
	Vector2 position;
	
	public RenderComponent(FileHandle texturePath, Vector2 position) {
		texture = new Texture(texturePath);
		this.position = position;
	}
	
	public void render(Batch batch) {
		batch.draw(texture, position.x , position.y, 1, 1);
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
	
	
	
}
