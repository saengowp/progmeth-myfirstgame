package com.progmethgame.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BaseStage extends Stage {
	protected Skin skin;

	public BaseStage() {
		this.skin = new Skin(Gdx.files.internal("plain-james/skin/plain-james-ui.json"));
	}
}
