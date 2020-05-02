package com.progmethgame.client.graphic.component;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Healthbar {
	
	public static void render(Batch batch, int x, int y, AssetManager assetMan, float health) {
		Texture hTex = assetMan.get("healthbar.png", Texture.class);
		Texture hfTex = assetMan.get("healthbarfill.png", Texture.class);
		batch.draw(hTex, x, y);
		batch.draw(hfTex, x, y, (int) (health * hTex.getWidth()), hTex.getHeight());
	}
	
}
