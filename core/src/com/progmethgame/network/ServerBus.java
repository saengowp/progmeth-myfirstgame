package com.progmethgame.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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

/** 
 * Server's communication bus.
 * 
 * Provides communication (event source and event bus) to multiple clients.
 * event should be handled by this bus's thread.
 */
public class ServerBus implements Runnable {
	
	/** Server is running*/
	private volatile boolean running;
	
	/** Kryonet server*/
	private final Server kryoServer;
	
	/** Client's event waiting to be processed */
	private final ConcurrentLinkedQueue<ClientEvent> inputEventQueue;
	
	/** Server's event waiting to be send*/
	private final ArrayList<ServerEventBroadcast> outputEventQueue;
	
	/** Map from Kyro connection to UUID */
	private final HashMap<Connection, UUID> clientConnections;
	
	/** ServerBus's event listener */
	private final ServerBusListener listener;
	
	/** Server Tick rate governor */
	private final Ticker ticker;
	
	/**
	 * Initialize and spin-off a separate server bus thread
	 * 
	 * @param listener Event Listener
	 * @throws IOException
	 */
	public ServerBus(ServerBusListener listener) throws IOException {
		log("Initializing");
		
		// Init Field
		this.listener = listener;
		this.ticker = new Ticker(GameConfig.SERVER_TICK_RATE);
		this.inputEventQueue = new ConcurrentLinkedQueue<ClientEvent>();
		this.outputEventQueue = new ArrayList<ServerEventBroadcast>();
		this.clientConnections = new HashMap<Connection, UUID>();
		
		// Init kyro
		this.kryoServer = new Server();
		SerializationUtil.registerKryo(kryoServer.getKryo());
		kryoServer.addListener(new KyroListener(inputEventQueue, clientConnections));
		kryoServer.bind(54555, 54777);
		kryoServer.start();
		
		// Start the server bus thread
		new Thread(this, "ServerThread").start();
	}
	
	/** 
	 * Stop the bus's thread and cleanup all the resources.
	 */
	public void terminate() {
		running = false;
	}
	
	/**
	 * Process event received from the clients
	 */
	private void processInputEvent() {
		ClientEvent event;
		while ((event = inputEventQueue.poll()) != null)
			event.notifyListener(listener);
	}
	
	/**
	 * Send event from the server
	 */
	private void processOutputEvent() {
		synchronized (clientConnections) {
			for (Connection con : clientConnections.keySet()) {
				UUID curId = clientConnections.get(con);
				
				NetworkPacket<ServerEvent> packet = new NetworkPacket<ServerEvent>();
				for (ServerEventBroadcast m : outputEventQueue) {
					if (m.target == null || m.target.equals(curId)) {
						packet.eventsList.add(m.event);
					}
					
					if (packet.eventsList.size() > GameConfig.SERVER_MAX_PACKET) {
						con.sendTCP(packet);
						packet.eventsList.clear();
					}
				}
				
				con.sendTCP(packet);
			}
			
			outputEventQueue.clear();
		}
	}
	
	/**
	 * Log message to stderr
	 * @param m message
	 */
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

	/** 
	 * Send a server event to the client(s).
	 * 
	 * @param clientId Target client (if clientId == null then all clients will received the event)
	 * @param event
	 */
	public void sendEvent(UUID clientId, ServerEvent event) {
		outputEventQueue.add(new ServerEventBroadcast(clientId, event));
	}
	
	/**
	 * Get all client's uuid connected to the server
	 * @return
	 */
	public List<UUID> getConnectionUUIDs() {
		return new ArrayList<UUID>(clientConnections.values());
	}
	
}

/**
 * Custom KyroNet listener for the server bus
 *
 */
class KyroListener extends Listener {
	
	/** Queue of message received */
	private final Queue<ClientEvent> queue;
	
	/** Client connection UUID mapping */
	private final Map<Connection, UUID> con;
	
	/**
	 * Create new listener which push message to the queue and register client connection to the con
	 * 
	 * Queue is assume to be thread-safe
	 * con will always be lock when there's an operation
	 * 
	 * @param queue client message storage queue
	 * @param con client UUID mapping storage
	 */
	public KyroListener(Queue<ClientEvent> queue, Map<Connection, UUID> con) {
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

/**
 * Tick governor
 */
class Ticker {
	
	/** Target tick rate */
	private final float tickRate;
	
	/** timestamp of the last tick */
	private long lastTickMillis;
	
	/** Duration between the last tick and this tick */
	private long lastTickDuration = 0;
	
	public Ticker(float tickRate) {
		this.tickRate = tickRate;
		this.lastTickMillis = System.currentTimeMillis();
	}
	
	public void tick() throws InterruptedException {
		long now = System.currentTimeMillis();
		while (now - lastTickMillis < tickRate * 1000) {
			Thread.sleep(Math.max(0, (long) (tickRate*1000 - (now - lastTickMillis))));
			now = System.currentTimeMillis();
		}
		lastTickDuration = now - lastTickMillis;
		lastTickMillis = now;
	}
	
	public long getLastDeltaMillis() {
		return lastTickDuration;
	}
}

/**
 * Data structure for storing server event sending request
 * @author pigt
 *
 */
class ServerEventBroadcast {
	
	/** Target client. null if ALL clients*/
	UUID target;
	
	/** The event */
	ServerEvent event;
	
	/** Create new broadcast to target with event */
	public ServerEventBroadcast(UUID target, ServerEvent event) {
		this.target = target;
		this.event = event;
	}
}
