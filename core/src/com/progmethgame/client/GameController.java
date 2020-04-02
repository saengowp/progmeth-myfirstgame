package com.progmethgame.client;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class GameController implements InputProcessor {
	
	Vector2 movementVec;
	
	public GameController(Vector2 movementVec) {
		this.movementVec = movementVec;
	}

	@Override
	public boolean keyDown(int keycode) {
		Vector2 control = movementVec;

		switch (keycode) {
		case Keys.W:
			control.y += 1; 
			return true;
		case Keys.A:
			control.x += -1;
			return true;
		case Keys.S:
			control.y += -1;
			return true;
		case Keys.D:
			control.x += 1; 
			return true;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		Vector2 control = movementVec;

		switch (keycode) {
		case Keys.W:
			control.y -= 1; 
			return true;
		case Keys.A:
			control.x -= -1;
			return true;
		case Keys.S:
			control.y -= -1;
			return true;
		case Keys.D:
			control.x -= 1; 
			return true;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
