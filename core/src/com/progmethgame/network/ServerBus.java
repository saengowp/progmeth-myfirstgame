package com.progmethgame.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.progmethgame.common.GameConfig;
import com.progmethgame.network.event.base.ClientEvent;
import com.progmethgame.network.event.base.ServerEvent;
import com.progmethgame.network.event.client.ClientDisconnectEvent;
import com.progmethgame.network.event.client.ClientJoinEvent;
import com.progmethgame.server.NetworkPacket;

/** Provides communication (event source and event bus) to multiple clients.
 * event should be handled by this bus's thread.
 */
public class ServerBus implements Runnable {
	
	private volatile boolean running;
	
	private final Server kryoServer;
	private final ConcurrentLinkedQueue<ClientEvent> inputEventQueue;
	private final ArrayList<ServerEventBroadcast> outputEventQueue;
	private final HashMap<Connection, UUID> clientConnections;
	
	private final ServerBusListener listener;
	
	private final Ticker ticker;
	
	/** Initialize and spin-off separate server thread
	 * 
	 * @param listener
	 * @throws IOException
	 */
	public ServerBus(ServerBusListener listener) throws IOException {
		log("Initializing");
		
		this.listener = listener;
		this.ticker = new Ticker(GameConfig.SERVER_TICK_RATE);
		
		this.inputEventQueue = new ConcurrentLinkedQueue<ClientEvent>();
		this.outputEventQueue = new ArrayList<ServerEventBroadcast>();
		this.clientConnections = new HashMap<Connection, UUID>();
		
		this.kryoServer = new Server();
		SerializationUtil.registerKryo(kryoServer.getKryo());
		kryoServer.addListener(new KyroListener(inputEventQueue, clientConnections));
		kryoServer.bind(54555, 54777);
		kryoServer.start();
		
		new Thread(this, "ServerThread").start();
	}
	
	/** Stop server's thread and cleanup all resources.
	 * 
	 */
	public void terminate() {
		running = false;
	}
	
	private void processInputEvent() {
		ClientEvent event;
		while ((event = inputEventQueue.poll()) != null)
			event.notifyListener(listener);
	}
	
	private void processOutputEvent() {
		synchronized (clientConnections) {
			for (Connection con : clientConnections.keySet()) {
				UUID curId = clientConnections.get(con);
				
				NetworkPacket<ServerEvent> packet = new NetworkPacket<ServerEvent>();
				for (ServerEventBroadcast m : outputEventQueue) {
					if (m.target == null || m.target.equals(curId)) {
						packet.eventsList.add(m.event);
					}
				}
				
				con.sendTCP(packet);
			}
			
			outputEventQueue.clear();
		}
	}
	
	private void log(String m) {
		System.err.println("server: " + m);
	}

	@Override
	public void run() {
		log("Starting");
		running = true;
		
		while (running) {
			processInputEvent();
			
			try {
				ticker.tick();
			} catch (InterruptedException e) {
				log("Interupted");
				break;
			}
			listener.onTick(ticker.getLastDeltaMillis()/1000f);
			
			processOutputEvent();
		}
		
		kryoServer.stop();
	}

	/** Send a server event to the client(s).
	 * 
	 * @param clientId Target client (if clientId == null then all clients will received the event)
	 * @param event
	 */
	public void sendEvent(UUID clientId, ServerEvent event) {
		outputEventQueue.add(new ServerEventBroadcast(clientId, event));
	}
	
}

class KyroListener extends Listener {
	
	private final ConcurrentLinkedQueue<ClientEvent> queue;
	private final HashMap<Connection, UUID> con;
	
	public KyroListener(ConcurrentLinkedQueue<ClientEvent> queue, HashMap<Connection, UUID> con) {
		this.queue = queue;
		this.con = con;
	}
	
	@Override
	public void connected(Connection connection) {
		synchronized (con) {
			UUID id = UUID.randomUUID();
			con.put(connection, id);
			
			ClientJoinEvent event = new ClientJoinEvent();
			event.clientId = id;
			
			queue.add(event);
		}
	}

	@Override
	public void received(Connection connection, Object object) {
		if (object instanceof ClientEvent) {
			ClientEvent e = (ClientEvent) object;
			synchronized (con) {
				e.clientId = con.get(connection);
			}
			 
			queue.add(e);
		}
	}
	
	@Override
	public void disconnected(Connection connection) {
		synchronized (con) {
			ClientDisconnectEvent event = new ClientDisconnectEvent();
			event.clientId = con.get(connection);
			con.remove(connection);
			queue.add(event);
		}
	}
}

class Ticker {
	
	private final float tickRate;
	private long lastTickMillis;
	private long lastTickDuration = 0;
	
	public Ticker(float tickRate) {
		this.tickRate = tickRate;
		this.lastTickMillis = System.currentTimeMillis();
	}
	
	public void tick() throws InterruptedException {
		while (System.currentTimeMillis() - lastTickMillis < tickRate * 1000)
			Thread.sleep(Math.min(1, (long) (tickRate*1000 - (System.currentTimeMillis() - lastTickMillis))));
		lastTickDuration = System.currentTimeMillis() - lastTickMillis;
		lastTickMillis = System.currentTimeMillis();
	}
	
	public long getLastDeltaMillis() {
		return lastTickDuration;
	}
}

class ServerEventBroadcast {
	
	UUID target;
	ServerEvent event;
	
	public ServerEventBroadcast(UUID target, ServerEvent event) {
		this.target = target;
		this.event = event;
	}
}
