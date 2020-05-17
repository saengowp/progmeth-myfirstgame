package com.progmethgame.launcher.screen;

import com.badlogic.gdx.utils.Align;

/**
 * Display about menu
 * 
 * @author pigt
 *
 */
public class AboutStage extends QuitableTextStage {
	
	/** Information Text */ 
	public static final String ABOUTTEXT = "Irritating Crusader (\"The game\") is a two-player, top-down, shooter game where the player \"disrupt\" the opponent by shooting the \"effect bullets\", eventually causing the other player to \"confuse\" and get killed by the \"traps\"."
			+ "\n\n The game is developed by Pipat Saengow and Siri Thammarerkrit"
			+ "\n\n Copyrighted Material:" +
			"\n    LibGDX is an Apache2-licensed game engine by Bad Logic Games.\n" + 
			"    KryoNet is a networking library by Nathan Sweet licensed under BSD-3-Clause.\n" + 
			"    PressStart2P is a font by CodeMan38 licensed under Open Font License.\n" + 
			"    plain-james is a scene2d ui skin by Raymond \"Raeleus\" Buckley licensed under CC BY 4.0\n" + 
			"    TMX Format is a tiled map data format by mapeditor.org licensed under CC BY-SA 3.0.\n" + 
			"    8-bit Game Over sound effect by Euphrosyyn via freesound.org CC BY 3.0\n";
	
	public AboutStage() {
		super(ABOUTTEXT);
		textLabel.setAlignment(Align.left);
	}
}
