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
	
	private int transX = 0;
	private int transY = 0;

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
	
	/**
	 * @author Shockper (from http://slick.ninjacave.com/forum/viewtopic.php?t=4713)
	 * @param screenWidth Width of the Window
	 * @param screenHeight Height of the Window
	 */
	public void focusCharakter(int screenWidth, int screenHeight) {
		int mapWidth = getWidth()*getTileWidth();
		int mapHeight = getHeight()*getTileHeight();
		if(charakter.getX()-screenWidth/2+16 < 0)
		          transX = 0;
		       else if(charakter.getX()+screenWidth/2+16 > mapWidth)
		          transX = -mapWidth+screenWidth;
		       else
		          transX = (int)-charakter.getX()+screenWidth/2-16;
		 
		       if(charakter.getY()-screenHeight/2+16 < 0)
		          transY = 0;
		       else if(charakter.getY()+screenHeight/2+16 > mapHeight)
		          transY = -mapHeight+screenHeight;
		       else
		          transY = (int)-charakter.getY()+screenHeight/2-16;
	}
	
	/**Rendert die Map komplett mit Spieler**/
	public void renderWithObjects(GameContainer container, Graphics g) throws SlickException {
		g.translate(transX, transY);
		renderGroundLayers();
		
		if(charakter != null)
			charakter.render(container, g);
		
		renderTopLayers();
		
		if(charakter != null)
			charakter.zeichneNamen(g);
		g.translate(-transX, -transY);
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
	
	public ArrayList<Shape> getCollisionObjects() {
		return collisionObjects;
	}
}
