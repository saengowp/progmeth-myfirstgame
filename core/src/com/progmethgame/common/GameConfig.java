package com.progmethgame.common;

public final class GameConfig {
	public static float SERVER_TICK_RATE = 1/30f;
	public static int CLIENT_ENTITY_INTERPOLATION_TIME_MILLIS = (int) (SERVER_TICK_RATE *1000);
	public static int SERVER_MAX_PACKET = 20;
}
