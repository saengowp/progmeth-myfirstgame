package com.progmethgame.server;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class GameMap {
	
	public int map[][];
	public int mapWidth, mapHeight;
	
	public GameMap() throws GameError {
		Gdx.app.debug("Map", "Initializing");
		try {
			parseMap();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new GameError("Map Load fail", e);
		}
		
		Gdx.app.debug("Map", "Map initialized");
	}
	
	private void parseMap() throws ParserConfigurationException, SAXException, IOException {
		FileHandle file = Gdx.files.internal("map/map.tmx");
		DocumentBuilderFactory dbF = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbF.newDocumentBuilder();
		Document doc = db.parse(file.read());
		
		NodeList layers = doc.getElementsByTagName("layer");
		Node tilelayer = layers.item(0);
		NodeList layerchild = tilelayer.getChildNodes();
		int width = Integer.valueOf(tilelayer.getAttributes().getNamedItem("width").getNodeValue());
		int height = Integer.valueOf(tilelayer.getAttributes().getNamedItem("height").getNodeValue());
		String data = "";
		for (int i = 0; i < layerchild.getLength(); i++) {
			Node n = layerchild.item(i);
			if (n.getNodeName() == "data") {
				assert(n.getAttributes().getNamedItem("encoding").getNodeValue() == "csv");
				data = n.getTextContent();
			}
		}
		
		
		Gdx.app.debug("Map Data","W " + width + "H " + height);
		
		map = new int[width][height];
		
		String[] dats = data.split(",");
		
		String debug = "";
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map[i][height - j -1] = 0x1FFFFFFF & Integer.parseUnsignedInt(dats[i + j * width].strip());
			}
		}
		
		for (int i = height - 1; i >= 0; i--) {
			for (int j = 0; j < width; j++)
				debug += map[j][i] + " ";
			debug += "\n";
		}
		
		Gdx.app.debug("Map Data", debug);
		
		this.mapHeight = height;
		this.mapWidth = width;
	}
	
	public boolean isSolid(int x, int y) {
		return (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) && map[x][y] != 12;
	}
}
