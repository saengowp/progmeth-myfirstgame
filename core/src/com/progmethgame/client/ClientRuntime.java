package com.progmethgame.client;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.utils.Disposable;
import com.progmethgame.common.DisplayType;
import com.progmethgame.common.EntityData;
import com.progmethgame.common.GameConfig;
import com.progmethgame.common.SoundType;
import com.progmethgame.common.context.ClientContext;
import com.progmethgame.launcher.GameLauncher;
import com.progmethgame.network.ClientBus;
import com.progmethgame.network.ClientBusListener;

/**
 * Store and manage the game's state
 */
public class ClientRuntime implements ClientBusListener, Disposable, ClientContext {
	
	/** Screen */
	private GameScreen screen;
	
	/** Client id assigned by the server. This is also the controllable player's entity UUID */
	private UUID clientId;
	
	/** Communication bus */
	private ClientBus bus;
	
	/** Store entities*/
	private HashMap<UUID, ClientEntity> entities;
	
	/** Assets loader */
	private AssetManager assetsMan;
	
	/**
	 * Create new runtime, connect to the server and display the game.
	 * @param ipaddr server's IP address
	 */
	public ClientRuntime(String ipaddr) {
		GameLauncher.getLauncher().displayMessage("Waiting...");
		
		// Initialization
		this.entities = new HashMap<UUID, ClientEntity>();
		this.assetsMan = new AssetManager();
		initAssets();
		this.assetsMan.finishLoading();
		
		// Initialize the bus (time-consuming)
		new Thread(() -> {
			Gdx.app.postRunnable(()->GameLauncher.getLauncher().displayMessage("Connecting... " + ipaddr));
			
			try {
				bus = new ClientBus(ipaddr, this);
			} catch (IOException e) {
				Gdx.app.error("ClientBus", "Error while initializing client bus", e);
				Gdx.app.postRunnable(()->GameLauncher.getLauncher().displayError(e));
				return;
			}
			
			Gdx.app.postRunnable(()->GameLauncher.getLauncher().displayMessage("Connected. Awaiting server READY signal"));
		}).start();
	}
	
	/**
	 * Initialize all assets required
	 */
	private void initAssets() {
		//DisplayType's textures
		for (DisplayType t : DisplayType.values()) {
			assetsMan.load(t.filename(), Texture.class);
		}
		
		// Custom textures
		assetsMan.load("hud.png", Texture.class);
		assetsMan.load("healthbar.png", Texture.class);
		assetsMan.load("healthbarfill.png", Texture.class);
		assetsMan.load("HUDhpbarblack.png", Texture.class);
		assetsMan.load("HUDhpbar.png", Texture.class);
			
		//Font
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assetsMan.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetsMan.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		FreeTypeFontLoaderParameter fontParam = new  FreeTypeFontLoaderParameter();
		fontParam.fontFileName = "PressStart2P.ttf";
		fontParam.fontParameters.size = 13;
		fontParam.fontParameters.color = Color.BLACK;
		fontParam.fontParameters.borderColor = Color.WHITE;
		fontParam.fontParameters.borderWidth = 1;
		assetsMan.load("font.ttf", BitmapFont.class, fontParam);
		FreeTypeFontLoaderParameter fontBigParam = new FreeTypeFontLoaderParameter();
		fontBigParam.fontFileName = "PressStart2P.ttf";
		fontBigParam.fontParameters.size = 100;
		fontBigParam.fontParameters.color = Color.BLACK;
		fontBigParam.fontParameters.borderColor = Color.WHITE;
		fontBigParam.fontParameters.borderWidth = 1;
		assetsMan.load("fontbig.ttf", BitmapFont.class, fontBigParam);
		
		//Music
		assetsMan.load("music.ogg", Music.class);
		
		//Sound
		for (SoundType s : SoundType.values()) {
			assetsMan.load(s.getFilepath(), Sound.class);
		}
	}
	
	@Override
	public Map<UUID, ClientEntity> getEntities() {
		return Collections.unmodifiableMap(entities);
	}
	
	@Override
	public AssetManager getAssetManager() {
		return this.assetsMan;
	}
	
	@Override
	public ClientBus getNetworkBus() {
		return bus;
	}
	
	@Override
	public UUID getClientUUID() {
		return clientId;
	}

	@Override
	public void onEntityAdd(UUID id, EntityData data) {
		entities.put(id, new ClientEntity(data));
	}

	@Override
	public void onEntityRemove(UUID id) {
		entities.remove(id);
	}

	@Override
	public void onEntityUpdate(EntityData data) {
		//It's possible that the server send update command before add command. ignore it
		if (entities.containsKey(data.id))
			entities.get(data.id).update(data);
	}

	@Override
	public void onServerReady(UUID assignedId) {
		Gdx.app.log("ClientRuntime", "Assigned player id " + assignedId.toString());
		this.clientId = assignedId;
		
		// Init screen
		this.screen = new GameScreen();
		GameLauncher.getLauncher().setScreen(screen);
		
		// Init music
		Music music = assetsMan.get("music.ogg", Music.class);
		music.setLooping(true);
		music.setVolume(GameConfig.AUDIO_VOLUME);
		music.play();
	}

	@Override
	public void onServerReset() {
		GameLauncher.getLauncher().displayMessage("Resetting level");
		entities.clear();
	}

	public void tick(float delta) {
		for (ClientEntity e: entities.values())
			e.tick(delta);
	}

	@Override
	public void dispose() {
		if (bus != null)
			bus.dispose();
		if (assetsMan != null)
			assetsMan.dispose();
	}

	@Override
	public void onDisconnect() {
		GameLauncher.getLauncher().displayMessageQuitable("Server Disconnected");
	}
	
	@Override
	public void quit() {
		GameLauncher.getLauncher().displayMessageQuitable("Game Exited");
	}

	@Override
	public void onPlaySound(SoundType sound) {
		Sound s = assetsMan.get(sound.getFilepath(), Sound.class);
		s.play(1);
	}
}
