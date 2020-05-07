package com.progmethgame.network.event.server;

import com.progmethgame.common.SoundType;
import com.progmethgame.network.ClientBusListener;
import com.progmethgame.network.event.base.ServerEvent;

/**
 * Request client to play a sound
 *
 */
public class ServerPlaySoundEvent extends ServerEvent {

	/** Sound requested */
	public SoundType sound;
	
	public ServerPlaySoundEvent() {
		
	}
	
	/** 
	 * Create new event to play the specified sound
	 * 
	 * @param s sound to play
	 */
	public ServerPlaySoundEvent(SoundType s) {
		this.sound = s;
	}

	@Override
	public void notifyListener(ClientBusListener target) {
		target.onPlaySound(sound);
	}

}
