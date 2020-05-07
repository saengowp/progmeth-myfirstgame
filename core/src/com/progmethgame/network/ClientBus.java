package com.progmethgame.network;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.progmethgame.network.event.base.ClientEvent;
import com.progmethgame.network.event.base.ServerEvent;

/**
 * Communication bus for the client
 *
 */
public class ClientBus implements Disposable {
	
	/** KyroNet client */
	private final Client client;
	
	/** Connect to the server. 
	 * 
	 * Constructor might block for few second.
	 * 
	 * @param ipaddr server's IP address
	 * @param listener bus's event listener
	 * @throws IOException
	 */
	public ClientBus(String ipaddr, ClientBusListener listener) throws IOException {
		Gdx.app.log("GameClient", "Initializing game client");
		
		// Init kyronet
		this.client = new Client();
		SerializationUtil.registerKryo(client.getKryo());
		client.start();
		client.addListener(new Listener() {
			@Override
			public void received(Connection connection, Object object) {
				super.received(connection, object);
				if (object instanceof NetworkPacket<?>) {
					//Might be risky since generic type is not present at runtime.
					NetworkPacket<?> packet = (NetworkPacket<?>) object;
					
					Gdx.app.postRunnable(new Runnable() {	
						@Override
						public void run() {
							for (Object o : packet.eventsList) {
								if (o instanceof ServerEvent) {
									ServerEvent event = (ServerEvent) o;
									event.notifyListener(listener);
								} else {
									Gdx.app.error("ClientBus", "Found " + o.getClass() + " in the networkpacket rouge server?");
								}
							}
						}
					});
				}
			}
			
			@Override
			public void disconnected(Connection connection) {
				super.disconnected(connection);
				Gdx.app.postRunnable(()->listener.onDisconnect());
			}
		});
		
		// Connect
		Gdx.app.log("GameClient", "Connecting to " + ipaddr);
		client.connect(10000, ipaddr, 54555, 54777);
	}
	
	/**
	 * Send an event to the server
	 * @param event
	 */
	public void sendEvent(ClientEvent event) {
		client.sendTCP(event);
	}

	@Override
	public void dispose() {
		client.stop();
		try {
			client.dispose();
		} catch (IOException e) {
			// Do nothing, It's dying anyway.
			e.printStackTrace();
		}
	}
}
