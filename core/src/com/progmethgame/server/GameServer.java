package com.progmethgame.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.progmethgame.command.PlayerJoinCommand;
import com.progmethgame.command.StartGameCommand;
import com.progmethgame.common.network.ClientCommand;
import com.progmethgame.common.network.ClientPacket;
import com.progmethgame.common.network.NetUtils;
import com.progmethgame.common.network.ServerPacket;

public class GameServer extends Thread {
	
	Server server;
	private volatile boolean running;
	GameRuntime runtime;
	long lastTick;
	ConcurrentLinkedQueue<ClientPacket> clientQueue;
	
	final float tickRate = 1/30f;
	
	ArrayList<PlayerConnection> connections;
	
	ArrayList<ClientCommandBroadcaste> commandQueue;
	
	public void terminate() {
		running = false;
	}

	@Override
	public void run() {
		super.run();

		Gdx.app.log("Server", "Initializing");

		running = true;
		clientQueue = new ConcurrentLinkedQueue<ClientPacket>();
		commandQueue = new ArrayList<ClientCommandBroadcaste>();
		server = new Server();
		NetUtils.registerKryo(server.getKryo());
		
		connections = new ArrayList<PlayerConnection>();
		
		
		try {
			runtime = new GameRuntime(this);
		} catch (GameError e1) {
			Gdx.app.error("Server", "Error initializing GameRuntime", e1);
			return;
		}

		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			Gdx.app.error("Server", "Fail to bind to 54555 54777 ports", e);
			return;
		}
		
		server.addListener(new Listener() {
			@Override
			public void connected(Connection connection) {
				super.connected(connection);
				//Faking packet
				ClientPacket packet = new ClientPacket();
				packet.commands.add(new PlayerJoinCommand(connection, GameServer.this));
				clientQueue.add(packet);
			}

			@Override
			public void received(Connection connection, Object object) {
				super.received(connection, object);
				if (object instanceof ClientPacket) {
					ClientPacket packet = (ClientPacket) object;
					clientQueue.add(packet);
				}
			}	
		});
		
		server.start();
		
		lastTick = System.currentTimeMillis();
		
		while (running) {
			ClientPacket packet;
			while ((packet = clientQueue.poll()) != null)
				packet.apply(runtime);
			long cTime = System.currentTimeMillis();
			
			
			while ((cTime - lastTick)/1000f < tickRate) {
				try {
					sleep(((int)(tickRate * 1000)) - (cTime - lastTick));
				} catch (InterruptedException e) {
					Gdx.app.error("Server", "Interupted while sleeping", e);
					return;
				}
				cTime = System.currentTimeMillis();
			}

			runtime.tick((cTime - lastTick)/1000f);
			
			lastTick = cTime;
			
			for (PlayerConnection con : connections) {
				ServerPacket p = new ServerPacket();
				for (ClientCommandBroadcaste com : commandQueue) {
					if (com.playerid == -1 || com.playerid == con.getPlayerId())
						p.commands.add(com.command);
				}
				con.getConnection().sendTCP(p);
			}
			
			commandQueue.clear();
		}
		
		server.stop();
		
	}
	
	public void acceptConnection(Connection connection) {
		PlayerConnection con = new PlayerConnection(connection, new Random().nextInt());
		connections.add(con);
		runtime.registerPlayer(con.getPlayerId());
	}
	
	public void notifyClient(int playerid, ClientCommand command) {
		commandQueue.add(new ClientCommandBroadcaste(playerid, command));
	}
	
}

class ClientCommandBroadcaste {
	
	int playerid;
	ClientCommand command;
	
	public ClientCommandBroadcaste(int playerid, ClientCommand command) {
		this.playerid = playerid;
		this.command = command;
	}
}
