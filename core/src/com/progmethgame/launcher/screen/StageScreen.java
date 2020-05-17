package com.progmethgame.launcher.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * A screen that display a stage
 */
public class StageScreen implements Screen {

	/** Current stage */
	private Stage stage;

	/**
	 *  Set a current stage
	 * @param stage stage to show
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
		show();
	}

	@Override
	public void show() {
		if (stage != null)
			Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (stage != null) {
			stage.act(delta);
			stage.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		if (stage != null)
			stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
