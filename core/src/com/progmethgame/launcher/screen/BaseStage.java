package com.progmethgame.launcher.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BaseStage extends Stage {
	protected Skin skin;

	public BaseStage() {
		super(new ScreenViewport());
		this.skin = new Skin(Gdx.files.internal("plain-james/skin/plain-james-ui.json"));
	}
}
