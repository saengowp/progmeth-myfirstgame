package com.progmethgame.client;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.network.event.client.PlayerFireEvent;
import com.progmethgame.network.event.client.PlayerMovementEvent;
import com.progmethgame.network.event.client.PlayerSwapGunEvent;

/**
 * Game's input device processor
 */
public class GameInputController implements InputProcessor {
	
	/** Player's movement direction vector */
	private final Vector2 movementVec;
	
	/** Initialize controller */
	public GameInputController() {
		this.movementVec = new Vector2();
	}
	
	/** Notify direction vector changes to the server */
	private void notifyMovement() {
		GameContext.getClientContext().getNetworkBus().sendEvent(new PlayerMovementEvent(movementVec));
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.W:
			movementVec.y += 1;
			notifyMovement();
			return true;
		case Keys.A:
			movementVec.x += -1;
			notifyMovement();
			return true;
		case Keys.S:
			movementVec.y += -1;
			notifyMovement();
			return true;
		case Keys.D:
			movementVec.x += 1; 
			notifyMovement();
			return true;
		case Keys.SPACE:
			GameContext.getClientContext().getNetworkBus().sendEvent(new PlayerFireEvent());
			return true;
		case Keys.E:
			GameContext.getClientContext().getNetworkBus().sendEvent(new PlayerSwapGunEvent());
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.W:
			movementVec.y -= 1;
			notifyMovement();
			return true;
		case Keys.A:
			movementVec.x -= -1;
			notifyMovement();
			return true;
		case Keys.S:
			movementVec.y -= -1;
			notifyMovement();
			return true;
		case Keys.D:
			movementVec.x -= 1; 
			notifyMovement();
			return true;
		case Keys.ESCAPE:
			GameContext.getClientContext().quit();
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
