package com.progmethgame.client.graphic.component;

import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.client.ClientEntity;
import com.progmethgame.common.context.GameContext;

public class BannerWinOverlay extends Overlay {
	
	private UUID winningPlayer;
	
	public BannerWinOverlay() {
	}
	
	public BannerWinOverlay(UUID winPlayer) {
		this.winningPlayer = winPlayer;
	}
	
	@Override
	public void render(Viewport view, Batch batch, Rectangle rect, ClientEntity target) {
		String text;
		if (GameContext.getClientContext().getClientUUID().equals(winningPlayer)) {
			text = "Victory";
		} else {
			text = "Game Over";
		}
		
		
		
		BitmapFont fnt = GameContext.getClientContext().getAssetManager().get("fontbig.ttf", BitmapFont.class);
		GlyphLayout textl = new GlyphLayout(fnt, text);
		fnt.draw(batch, text, view.getScreenWidth()/2 - textl.width/2, view.getScreenHeight()/2 + textl.height/2);
	}

}
