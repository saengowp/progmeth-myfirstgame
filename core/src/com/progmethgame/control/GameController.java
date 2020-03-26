package com.progmethgame.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.progmethgame.GameState;

public class GameController implements InputProcessor {

	GameState state;

	public GameController(GameState state) {
		this.state = state;
	}
	
	public void tick(float delta) {
		//Temp
		final float speed = 5;
		Vector2 ppos = this.state.player.getPosition();
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			ppos.y += speed*delta;

		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			ppos.y -= speed*delta;

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			ppos.x += speed*delta;

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			ppos.x -= speed*delta;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.ESCAPE) {
			state.gameShouldClose = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
