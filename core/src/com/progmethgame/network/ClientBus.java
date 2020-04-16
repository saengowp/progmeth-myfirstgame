package com.progmethgame.network;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.progmethgame.network.event.base.ClientEvent;
import com.progmethgame.network.event.base.ServerEvent;
import com.progmethgame.server.NetworkPacket;

public class ClientBus {
	private final Client client;
	
	/** Connect to the server. Constructor might block for few second.
	 * 
	 * @param ipaddr
	 * @param listener
	 * @throws IOException
	 */
	public ClientBus(String ipaddr, ClientBusListener listener) throws IOException {
		Gdx.app.log("GameClient", "Initializing game client");
		
		this.client = new Client();
		SerializationUtil.registerKryo(client.getKryo());
		client.start();
		client.addListener(new Listener() {
			@Override
			public void received(Connection connection, Object object) {
				super.received(connection, object);
				if (object instanceof NetworkPacket<?>) {
					NetworkPacket<ServerEvent> packet = (NetworkPacket<ServerEvent>) object; 
					//Might be risky since generic type is not present at runtime. 
					
					Gdx.app.postRunnable(new Runnable() {	
						@Override
						public void run() {
							for (ServerEvent e : packet.eventsList) {
								e.notifyListener(listener);
							}
						}
					});
				}
			}
		});
		
		Gdx.app.log("GameClient", "Connecting to " + ipaddr);
		client.connect(10000, ipaddr, 54555, 54777);
	}
	
	public void sendEvent(ClientEvent event) {
		client.sendTCP(event);
	}
}
