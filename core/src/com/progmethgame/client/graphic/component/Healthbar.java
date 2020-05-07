package com.progmethgame.client.graphic.component;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.progmethgame.common.context.GameContext;

/**
 * Utility for drawing health bar
 */
public class Healthbar {
	
	/**
	 * Render the health bar
	 * @param batch Screen batch
	 * @param x 
	 * @param y 
	 * @param width
	 * @param height
	 * @param health Value between 0 and 1 indicating health
	 * @param healthtex Path to empty healthbar's texture
	 * @param healthfilltex Path to full healthbar's texture 
	 */
	public static void render(Batch batch, int x, int y, int width, int height, float health, String healthtex, String healthfilltex) {
		AssetManager assetMan = GameContext.getClientContext().getAssetManager();
		Texture hTex = assetMan.get(healthtex, Texture.class);
		Texture hfTex = assetMan.get(healthfilltex, Texture.class);
		batch.draw(hTex, x, y, width, height);
		batch.draw(hfTex, x, y, (int) (health * width), height, 0f, 1f, health, 0f);
	}
	
}
