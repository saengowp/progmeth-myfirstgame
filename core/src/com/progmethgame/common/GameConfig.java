package com.progmethgame.common;

/**
 * Game's configuration and constants.
 */
public final class GameConfig {
	
	/** Server update rate (Hz) */
	public static float SERVER_TICK_RATE = 1/30f;
	
	/** Delay duration for entity interpolation */
	public static int CLIENT_ENTITY_INTERPOLATION_TIME_MILLIS = (int) (SERVER_TICK_RATE *1000);
	
	/** Max event to send in a single packet */
	public static int SERVER_MAX_PACKET = 20;
	
	/** Music and Sound's volume */
	public static float AUDIO_VOLUME = 0.50f;
	
	/** Width and Height of client's map viewport */
	public static int CLIENT_MAP_VIEWPORT_SIZE = 20;
	
	/** Map file's location*/
	public static String MAP_FILEPATH = "map/map.tmx";
	
	public static int SERVER_GAMEOVER_TIMER = (int) (5/SERVER_TICK_RATE);
}
