package com.progmethgame.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.progmethgame.launcher.GameLauncher;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("ProgMeth Game");
		config.useVsync(true);
		
		new Lwjgl3Application(new GameLauncher(), config);
	}
}
