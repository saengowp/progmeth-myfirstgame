package com.progmethgame.client;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.progmethgame.command.PlayerControlCommand;
import com.progmethgame.common.network.ClientPacket;
import com.progmethgame.common.network.NetUtils;
import com.progmethgame.common.network.ServerPacket;

public class GameClient {
	
	Client client;
	
	GameScreen screen;
	GameMain gameControl;
	
	int userid;
	
	HashMap<Integer, ClientEntity> entities;
	Vector2 movementVec;
	
	public GameClient(String ipaddr, GameMain gameControl) {
		this.gameControl = gameControl;
		this.entities = new HashMap<Integer, ClientEntity>();
		this.movementVec = new Vector2();
		
		Gdx.app.log("GameClient", "Initializing game client");
		
		this.client = new Client();
		NetUtils.registerKryo(client.getKryo());
		client.start();
		
		
		client.addListener(new Listener() {
			@Override
			public void received(Connection connection, Object object) {
				super.received(connection, object);
				if (object instanceof ServerPacket) {
					ServerPacket packet = (ServerPacket) object;
					Gdx.app.postRunnable(new Runnable() {
						
						@Override
						public void run() {
							packet.apply(GameClient.this);
						}
					});
				}
			}
		});
		
		Gdx.app.log("GameClient", "Connecting to " + ipaddr);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					client.connect(10000, ipaddr, 54555, 54777);
				} catch (IOException e) {
					Gdx.app.postRunnable(() -> {
						Gdx.app.log("GameClient", "Connection failed " + e.getMessage());
						gameControl.displayMessage(e.getMessage());
					});
					return;
				}
				
				Gdx.app.postRunnable(() -> {
					Gdx.app.log("GameClient", "Connection Established");
					gameControl.displayMessage("Connected");
				});
			}
		}).start();;
		
		
	}
	
	private float tickacc = 0;
	private float tickRate = 1/20f;
	
	public void tick(float delta) {
		tickacc += delta;
		if (tickacc < tickRate)
			return;
		
		ClientPacket packet = new ClientPacket();
		
		PlayerControlCommand movecom = new PlayerControlCommand();
		movecom.playerId = userid;
		movecom.movementVec = movementVec;

		packet.commands.add(movecom);

		client.sendTCP(packet);
		
		//Gdx.app.debug("SendMove", movementVec.x + ", " + movementVec.y);
		
		tickacc = 0;
	}
	
	public void startGame(int userid) {

		GameController control = new GameController(movementVec);

		screen = new GameScreen(this, control);
		gameControl.setScreen(screen);
		this.userid = userid;

		
		Gdx.app.debug("GameClient", "Starting game with userid " + userid);
	}
	
	public void addEntity(ClientEntity e) {
		entities.put(e.getGid(), e);
	}
	
	public void setEntityPos(int gid, Vector2 pos) {
		entities.get(gid).setPosition(pos.x, pos.y);
	}
	
	public int getPlayerId() {
		return userid;
	}
}
