package com.progmethgame.common;

/**
 * Represent different available texture on the client.
 *
 */
public enum DisplayType {
	PLAYER("player.png"),
	BULLET_BURN("bullets/burnbullet.png"),
	BULLET_SLOW("bullets/slowbullet.png"),
	BULLET_CONFUSE("bullets/confusebullet.png"),
	BULLET_STUNT("bullets/stuntbullet.png"),
	BULLET_HOOK("bullets/hookbullet.png"),
	BULLET_TELEPORT("bullets/teleportbullet.png"),
	RICK("rick.png"),
	TEST("test.png"),
	POTION("potion.png"),
	SMALLCUBE("smallcube.png"),
	SMALLCUBEEX("smallcubeexcited.png"),
	EFFECT_BURN("effects/burn.png"),
	EFFECT_CONFUSE("effects/confuse.png"),
	EFFECT_SLOW("effects/slow.png"),
	EFFECT_STUNT("effects/stunt.png"),
	PLAYER_BLUE("players/TopBlue.png"),
	PLAYER_BLUE_ICON("players/Blue.png"),
	PLAYER_RED("players/TopRed.png"),
	PLAYER_RED_ICON("players/Red.png");
	
	
	
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
