package com.progmethgame.client.graphic.component;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.client.ClientEntity;
import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;

public class HudOverlay extends Overlay {
	
	public float health;
	public String text; //Temporary
	public DisplayType gunIcon;
	public DisplayType effectIcon;
	
	@Override
	public void render(Viewport view, Batch batch, Rectangle rect, ClientEntity target) {
		
		//Don't render other player's hud.
		if (!GameContext.getClientContext().getClientUUID().equals(target.getGid()))
			return;
		
		AssetManager assets = GameContext.getClientContext().getAssetManager();
		Texture hudBg = assets.get("hud.png", Texture.class);
		batch.draw(hudBg, 0, 0);
		
		Healthbar.render(batch, 10, 10, 300, 20, health);
		
		BitmapFont fnt = assets.get("font.ttf", BitmapFont.class);
		fnt.draw(batch, text, 10, 400);
		fnt.draw(batch, "Debug2: gunIcon " + gunIcon + "\n Effect Icon" + (effectIcon != null ? effectIcon.toString() : "None"), 10, 380);
		
		Texture gunBg = assets.get(gunIcon.filename(), Texture.class);
		batch.draw(gunBg, 10, 40, 100, 100);
		
		if (effectIcon != null) {
			Texture effectTex = assets.get(effectIcon.filename(), Texture.class);
			batch.draw(effectTex ,120, 40, 100, 100);
		}
		
	}

}
