package com.progmethgame.launcher.screen;

/**
 * Display Help information
 * 
 * @author pigt
 *
 */
public class HelpStage extends QuitableTextStage {

	/** Help Information */
	public static final String HELPTEXT = "WASD to move"
			+ "\nE to change weapon"
			+ "\nSPACE to shoot"
			+ "\n See game manual for more information.";
	
	public HelpStage() {
		super(HELPTEXT);
	}

}
