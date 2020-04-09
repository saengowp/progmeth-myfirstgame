package com.progmethgame.client.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TextStage extends BaseStage {
	
	public TextStage(String text) {
		super();
		Label label = new Label(text, skin);
		label.setFillParent(true);
		this.addActor(label);
	}
}
