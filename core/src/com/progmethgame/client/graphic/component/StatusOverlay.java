package com.progmethgame.client.graphic.component;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.client.ClientEntity;
import com.progmethgame.common.DisplayType;
import com.progmethgame.common.context.GameContext;

public class StatusOverlay extends Overlay {

	public float health;
	public DisplayType effectIcon;
	
	@Override
	public void render(Viewport view, Batch batch, Rectangle rect, ClientEntity target) {
		int x = (int) rect.x;
		int y = (int) rect.y;
		int w = (int) rect.width;
		int h = (int) rect.height;
		
		AssetManager assetMan = GameContext.getClientContext().getAssetManager();
		Healthbar.render(batch, x, y + h + 10, w, h/10,  health, "healthbar.png", "healthbarfill.png");
		
		if (effectIcon != null) {
			Texture effectTex = assetMan.get(effectIcon.filename(), Texture.class);
			batch.draw(effectTex, x , y, w, h);
		}
		
	}

}
