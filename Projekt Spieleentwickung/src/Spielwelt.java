import java.util.ArrayList;

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


public class Spielwelt extends TiledMapPlus {
	
	//Settings für Editor:
	private int layerIndexAbovePlayer = 2; //Layer >= layerIndexAbovePlayer werden nach dem Spieler gerendert
	private String collisionObjectLayerName = "block"; //Name des ObjectLayers mit dem Kollisionsobjekte auf Map erzeugt werden

	private ArrayList<Shape> collisionObjects = new ArrayList<Shape>();
	
	private int x = 0;
	private int y = 0;

	public Spielwelt(String path) throws SlickException {
		super(path);
		
		
	}
	
	public void init() throws SlickException {
		addObjects();
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

	public ArrayList<Shape> getMovementBlockers() {
		return collisionObjects;
	}
	
	public void renderCollisionObjects(Graphics g) {
		for(Shape shape : collisionObjects)
			g.fill(shape);
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
	
	private void addObjects() {
		for(ObjectGroup objectGroup : getObjectGroups()) {
			if(objectGroup.name.equalsIgnoreCase(collisionObjectLayerName))
				for(GroupObject blockObject : objectGroup.getObjects()) {
					collisionObjects.add(blockObject.getShape());
				}
		}
	}
	
	public float getFocusX(float x) {
		return -1*x+(getWidth()*getTileWidth())/2;
	}
	
	public float getFocusY(float y) {
		return -1*y+(getHeight()*getTileHeight())/2;
	}
}
