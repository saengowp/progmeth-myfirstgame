package com.progmethgame.server;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.progmethgame.common.GameConfig;
import com.progmethgame.server.blocks.Block;
import com.progmethgame.server.blocks.BlockManager;
import com.progmethgame.server.entities.Player;
import com.progmethgame.server.entities.Tickable;

/**
 * Store map's state and managed entity's interaction with the map
 */
public class GameMap implements Tickable {
	
	/** 2d array of map's block id */
	private int map[][];
	
	/** 2d array of map's block object*/
	private Block blocks[][];
	
	/** width of the map */
	private int mapWidth;
	
	/** height of the map */
	private int mapHeight;
	
	/** List of available spawn location */
	private final Vector2[] spawnPoints = {new Vector2(2, 2), new Vector2(18, 18)};
	
	/** Index of the next spawn location*/
	private int spawnPointsIdx = 0;
	
	/**
	 * Read and initialize the map
	 * @throws ServerStartupError
	 */
	public GameMap() throws ServerStartupError {
		Gdx.app.debug("Map", "Initializing");
		try {
			parseMap();
		} catch (MapParserException e) {
			throw new ServerStartupError("Map Load fail", e);
		}
		
		blocks = new Block[mapWidth][mapHeight];
		for (int i = 0; i < mapWidth; i++) {
			for (int j = 0; j < mapHeight; j++) {
				blocks[i][j] = BlockManager.fromId(map[i][j]);
			}
		}
		
		Gdx.app.debug("Map", "Map initialized");
	}
	
	/**
	 * Parse the map file into 2d block id array.
	 * @throws MapParserException
	 */
	private void parseMap() throws MapParserException {
		DocumentBuilderFactory dbF = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbF.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new MapParserException("Error while initializing document builder", e);
		}
		
		Document doc;
		try (InputStream s = ClassLoader.getSystemResourceAsStream(GameConfig.MAP_FILEPATH)){
			doc = db.parse(s);
		} catch (SAXException | IOException e) {
			throw new MapParserException("Error whild XML parsing the map file", e);
		}
		
		NodeList layers = doc.getElementsByTagName("layer");
		Node tilelayer = layers.item(0);
		NodeList layerchild = tilelayer.getChildNodes();
		int width = Integer.valueOf(tilelayer.getAttributes().getNamedItem("width").getNodeValue());
		int height = Integer.valueOf(tilelayer.getAttributes().getNamedItem("height").getNodeValue());
		String data = null;
		for (int i = 0; i < layerchild.getLength(); i++) {
			Node n = layerchild.item(i);
			if (n.getNodeName() == "data") {
				assert(n.getAttributes().getNamedItem("encoding").getNodeValue() == "csv");
				data = n.getTextContent();
			}
		}
		
		if (data == null)
			throw new MapParserException("No data found");
		
		
		Gdx.app.debug("Map Data","W " + width + "H " + height);
		
		map = new int[width][height];
		
		String[] dats = data.split(",");
		
		//String debug = "";
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map[i][height - j -1] = 0x1FFFFFFF & Integer.parseUnsignedInt(dats[i + j * width].trim());
			}
		}
		
		/*
		for (int i = height - 1; i >= 0; i--) {
			for (int j = 0; j < width; j++)
				debug += map[j][i] + " ";
			debug += "\n";
		}
		
		Gdx.app.debug("Map Data", debug);
		*/
		this.mapHeight = height;
		this.mapWidth = width;
	}
	
	/**
	 * Retriev the block at specified position
	 * @param x
	 * @param y
	 * @return Block at that location or null if there's no block there
	 */
	public Block getBlock(int x, int y) {
		return (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) ? blocks[x][y]:null;
	}
	
	/**
	 * Initialize the player's state
	 * 
	 * @param player
	 */
	public void onPlayerEnter(Player player) {
		player.getPosition().set(spawnPoints[spawnPointsIdx++]);
		spawnPointsIdx %= spawnPoints.length;
	}
	
	/**
	 * @return Map's width
	 */
	public int getWidth() {
		return mapWidth;
	}
	
	/**
	 * 
	 * @return Map's height
	 */
	public int getHeight() {
		return mapHeight;
	}

	/**
	 *  Reset the map
	 */
	public void reset() {
		this.spawnPointsIdx = 0;
	}

	@Override
	public void tick(float delta) {
		for (int i = 0; i < mapWidth; i++) {
			for (int j = 0; j < mapHeight; j++) {
				blocks[i][j].tick(delta);
			}
		}
	}
}
