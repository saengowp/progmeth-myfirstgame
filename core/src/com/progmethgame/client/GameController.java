package com.progmethgame.client;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.progmethgame.common.context.GameContext;
import com.progmethgame.network.ClientBus;
import com.progmethgame.network.event.client.PlayerFireEvent;
import com.progmethgame.network.event.client.PlayerMovementEvent;
import com.progmethgame.network.event.client.PlayerSwapGunEvent;

public class GameController implements InputProcessor {
	
	private final Vector2 movementVec;
	private final ClientBus bus;
	
	public GameController() {
		this.movementVec = new Vector2();
		this.bus = GameContext.getClientContext().getNetworkBus();
	}
	
	private void notifyMovement() {
		PlayerMovementEvent e = new PlayerMovementEvent();
		e.movementVec = movementVec;
		bus.sendEvent(e);
	}

	@Override
	public boolean keyDown(int keycode) {
		Vector2 control = movementVec;

		switch (keycode) {
		case Keys.W:
			control.y += 1;
			notifyMovement();
			return true;
		case Keys.A:
			control.x += -1;
			notifyMovement();
			return true;
		case Keys.S:
			control.y += -1;
			notifyMovement();
			return true;
		case Keys.D:
			control.x += 1; 
			notifyMovement();
			return true;
		case Keys.SPACE:
			bus.sendEvent(new PlayerFireEvent());
			return true;
		case Keys.E:
			bus.sendEvent(new PlayerSwapGunEvent());
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
			notifyMovement();
			return true;
		case Keys.A:
			control.x -= -1;
			notifyMovement();
			return true;
		case Keys.S:
			control.y -= -1;
			notifyMovement();
			return true;
		case Keys.D:
			control.x -= 1; 
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
