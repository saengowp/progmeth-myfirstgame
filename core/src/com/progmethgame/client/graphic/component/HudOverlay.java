package com.progmethgame.client.graphic.component;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.client.ClientEntity;
import com.progmethgame.common.context.GameContext;

public class HudOverlay extends Overlay {

	@Override
	public void render(Viewport view, Batch batch, ClientEntity target) {
		
		//Don't render other player's hud.
		if (!GameContext.getClientContext().getClientUUID().equals(target.getGid()))
			return;
		
		AssetManager assets = GameContext.getClientContext().getAssetManager();
		Texture hudBg = assets.get("hud.png", Texture.class);
		batch.draw(hudBg, 0, 0);
	}

}
