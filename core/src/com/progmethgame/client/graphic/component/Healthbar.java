package com.progmethgame.client.graphic.component;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.progmethgame.common.GameConfig;
import com.progmethgame.common.context.GameContext;

public class Healthbar {
	
	public static void render(Batch batch, int x, int y, int width, int height, float health) {
		AssetManager assetMan = GameContext.getClientContext().getAssetManager();
		Texture hTex = assetMan.get("healthbar.png", Texture.class);
		Texture hfTex = assetMan.get("healthbarfill.png", Texture.class);
		batch.draw(hTex, x, y, width, height);
		batch.draw(hfTex, x, y, (int) (health * width), height);
	}
	
}
