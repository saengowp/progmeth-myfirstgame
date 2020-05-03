package com.progmethgame.network.event.server;

import com.progmethgame.common.SoundType;
import com.progmethgame.network.ClientBusListener;
import com.progmethgame.network.event.base.ServerEvent;

public class ServerPlaySoundEvent extends ServerEvent {

	public SoundType sound;
	
	public ServerPlaySoundEvent() {
		
	}
	
	public ServerPlaySoundEvent(SoundType s) {
		this.sound = s;
	}

	@Override
	public void notifyListener(ClientBusListener target) {
		target.onPlaySound(sound);
	}

}
