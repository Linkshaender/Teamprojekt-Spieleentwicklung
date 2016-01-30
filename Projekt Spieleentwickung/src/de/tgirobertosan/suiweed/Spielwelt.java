package de.tgirobertosan.suiweed;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.tiled.GlobalTile;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.ObjectGroup;
import org.newdawn.slick.tiled.TileOnLayer;
import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.tiled.TiledMapPlus;

import de.tgirobertosan.suiweed.charakter.Charakter;
import de.tgirobertosan.suiweed.charakter.InputHandler;


public class Spielwelt extends TiledMapPlus {
	
	//Settings für Editor:
	private int layerIndexAbovePlayer = 2; //Layer >= layerIndexAbovePlayer werden nach dem Spieler gerendert
	private String collisionObjectLayerName = "block"; //Name des ObjectLayers mit dem Kollisionsobjekte auf Map erzeugt werden

	private ArrayList<Shape> collisionObjects = new ArrayList<Shape>();
	
	/**Der Charakter, falls er sich auf dieser Map befindet**/
	private Charakter charakter;
	
	private int x = 0;
	private int y = 0;

	public Spielwelt(String path) throws SlickException {
		super(path);
		
	}
	
	public void init(InputHandler inputHandler) throws SlickException {
		addObjects(inputHandler);
		addTileCollisions();
	}

	/**
	 * @param animated Ob animierte Tiles der Spiewelt animiert werden sollten
	 */
	public void setTileAnimations(boolean animated) {
		for(TileSet tileset : getTilesets()) {
			for(GlobalTile globaltile : tileset.getGlobalTiles())
				globaltile.setAnimated(animated);
		}
	}
	
	public void renderCollisionObjects(Graphics g) {
		for(Shape shape : collisionObjects)
			g.fill(shape);
	}
	
	public void renderWithObjects(GameContainer container, Graphics g) throws SlickException {
		renderGroundLayers();
		
		if(charakter != null)
			charakter.render(container, g);
		
		renderTopLayers();
		
		if(charakter != null)
			charakter.zeichneNamen(g);
	}
	
	public void renderGroundLayers() {
		for(int i = 0; i < layerIndexAbovePlayer && i < getLayerCount(); i++) {
			render(x, y, i);
		}
	}
	
	public void renderTopLayers() {
		for(int i = layerIndexAbovePlayer;i < getLayerCount(); i++) {
			render(x, y, i);
		}
	}
	
	private void addTileCollisions() {
		for(TileSet tileSet : getTilesets())
			for(GlobalTile globalTile : tileSet.getGlobalTiles()) {
				Shape shape = globalTile.getCollisionShape();
				if(shape != null) {
					for(TileOnLayer tileOnLayer : getAllTilesFromAllLayers(tileSet.name)) {
						if(tileOnLayer.getGid() == globalTile.getGid()) {
							collisionObjects.add(shape.transform(Transform.createTranslateTransform(tileOnLayer.x*tileSet.getTileWidth()+x, tileOnLayer.y*tileSet.getTileHeight()+y)));
						}
					}
				}
			}
	}
	
	private void addObjects(InputHandler inputHandler) {
		for(ObjectGroup objectGroup : getObjectGroups()) {
			if(objectGroup.name.equalsIgnoreCase(collisionObjectLayerName))
				for(GroupObject blockObject : objectGroup.getObjects()) {
					collisionObjects.add(blockObject.getShape());
				}
			else {
				for(GroupObject groupObject : objectGroup.getObjects()) {
					if(groupObject.type.equalsIgnoreCase("charakter")) {
						charakter = new Charakter(groupObject.name, groupObject.x, groupObject.y, this);
						inputHandler.setCharakter(charakter);
					}
				}
			}
		}
	}
	

	public boolean checkCollision(Shape shape) {
		for(Shape collisionObject : getCollisionObjects()) {
			if(shape.intersects(collisionObject))
				return true;
		}
		return false;
	}
	
	public Charakter getCharakter() {
		return charakter;
	}
	
	public void setCharakter(Charakter charakter) {
		this.charakter = charakter;
	}
	
	public float getFocusX(float x) {
		return -1*x+(getWidth()*getTileWidth())/2;
	}
	
	public float getFocusY(float y) {
		return -1*y+(getHeight()*getTileHeight())/2;
	}
	
	public ArrayList<Shape> getCollisionObjects() {
		return collisionObjects;
	}
}
