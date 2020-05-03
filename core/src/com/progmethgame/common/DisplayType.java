package com.progmethgame.common;

/**
 * Represent different available texture on the client.
 *
 */
public enum DisplayType {
	PLAYER("player.png"),
	BULLET_BURN("test.png"),
	BULLET_SLOW("test.png"),
	BULLET_CONFUSE("rick.png"),
	BULLET_STUNT("test.png"),
	BULLET_HOOK("test.png"),
	BULLET_TELEPORT("test.png"),
	RICK("rick.png"),
	TEST("test.png"),
	POTION("potion.png"),
	SMALLCUBE("smallcube.png"),
	SMALLCUBEEX("smallcubeexcited.png");
	
	/**
	 *  Location of the file associated with this DisplayType
	 */
	private final String filename;
	
	private DisplayType(String filename) {
		this.filename = filename;
	}
	
	public String filename() {
		return this.filename;
	}
}
