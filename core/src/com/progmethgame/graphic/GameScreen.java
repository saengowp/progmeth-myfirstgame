package com.progmethgame.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progmethgame.control.GameController;

public class GameScreen implements Screen {

	final int worldWidth = 20;
	final int worldHeight = 20;
	
	GameRenderer renderer;
	GameController controller;
	
	OrthographicCamera camera;
	Viewport viewport;
	
	public GameScreen(GameRenderer renderer, GameController controller) {
		this.renderer = renderer;
		this.controller = controller;
		
		this.camera = renderer.getCamera();
		this.viewport = new FillViewport(worldWidth, worldHeight, this.camera);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this.controller);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {

	}

}
