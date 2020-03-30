package com.progmethgame.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.progmethgame.GameState;
import com.progmethgame.graphic.GameRenderer;

public class GameController implements InputProcessor {

	GameState state;
	GameRenderer renderer;

	public GameController(GameState state, GameRenderer renderer) {
		this.state = state;
		this.renderer = renderer;
	}
	
	/*public void tick(float delta) {
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
	}*/


	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Input.Keys.ESCAPE:
			state.gameShouldClose = true;
			return true;
		case Input.Keys.W:
			state.player.ctrlDir.y = 1;
			return true;
		case Input.Keys.S:
			state.player.ctrlDir.y = -1;
			return true;
		case Input.Keys.A:
			state.player.ctrlDir.x = -1;
			return true;
		case Input.Keys.D:
			state.player.ctrlDir.x = 1;
			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Input.Keys.W:
			state.player.ctrlDir.y = 0;
			return true;
		case Input.Keys.S:
			state.player.ctrlDir.y = 0;
			return true;
		case Input.Keys.A:
			state.player.ctrlDir.x = 0;
			return true;
		case Input.Keys.D:
			state.player.ctrlDir.x = 0;
			return true;
		default:
			break;
		}
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
		Vector3 gameCoord = this.renderer.getCamera().unproject(new Vector3(screenX, screenY, 0f));
		gameCoord.x -= 0.5;
		gameCoord.y -= 0.5;
		state.player.setFacing(new Vector2(gameCoord.x, gameCoord.y));
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
