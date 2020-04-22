package com.progmethgame.client;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.Viewport;

/** Debugging Interface.
 *  It provides debugging command line's rendering, input, and network component.
 *
 */
public class GameDebugger implements InputProcessor {
	
	private boolean activated = false;
	private final AssetManager assets;
	private String command = "";
	private ArrayList<String> history = new ArrayList<String>();
	private int histPtr = 0;
	private final Consumer<String> debugOutput;
	
	public GameDebugger(AssetManager assets, Consumer<String> debugOutput) {
		this.assets = assets;
		this.debugOutput = debugOutput;
	}
	
	public void render(Batch batch, Viewport hudViewport) {
		if (!activated)
			return;
		
		assets.get("font.ttf", BitmapFont.class).draw(batch, "DEBUGGER: [ESC] to close [ENTER] to trigger the command", 0, hudViewport.getScreenHeight());
		assets.get("font.ttf", BitmapFont.class).draw(batch, "COMMAND:" + command, 0, 20);
	}
	
	private void sendDebug() {
		Gdx.app.log("Debugger", "Sending " + command);
		debugOutput.accept(command);
	}

	@Override
	public boolean keyDown(int keycode) {
		return activated;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		switch (keycode) {
		
		case Keys.ENTER:
			if (activated) {
				sendDebug();
				history.add(command);
				histPtr = -1;
			}
		//Flow through
		case Keys.ESCAPE:
			if (activated) {
				activated = false;
				command = "";
				return true;
			}
			break;
			
		case Keys.SLASH:
			if (!activated)
				activated = true;
			break;
			
		case Keys.UP:
			if (activated && histPtr + 1 < history.size()) {
				histPtr++;
				command = history.get(history.size() - histPtr - 1);
			}
			break;
			
		case Keys.DOWN:
			if (activated && histPtr -1 >= 0) {
				histPtr--;
				command = history.get(history.size() - histPtr - 1);
			}
			break;
		
		default:
			break;
		}
		
		return activated;
	}

	@Override
	public boolean keyTyped(char character) {
		if (activated) {
			histPtr = -1;
			if (character == '\b' && command.length() > 0)
				command = command.substring(0, command.length() - 1);
			else if (Character.isLetterOrDigit(character) || character == ' ')
				command += character;
			return true;
		}
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
