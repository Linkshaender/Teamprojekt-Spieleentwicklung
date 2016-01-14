import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.TiledMapPlus;


public class Spielwelt extends TiledMapPlus {

	private ArrayList<Shape> movementBlockers = new ArrayList<Shape>();

	
	public Spielwelt(String path) throws SlickException {
		super(path);
		
		
	}
	
	public void init() throws SlickException {
		for(GroupObject blockObject : getObjectGroup("block").getObjects()) {
			movementBlockers.add(blockObject.getShape());
		}
	}
	
	/* Ersetzt durch modifizierte Slick-Version
	private void addMovementBlockers() {
		int x = 0, y = 0;
		while(x < getWidth()) {
			y = 0;
			while(y < getHeight()) {
				int tileID = getTileId(x, y, 0);
				String blocked = getTileProperty(tileID, "movementBlocker", "false");
				if(blocked.equals("true")) {
					movementBlockers.add(new Rectangle(x*getTileWidth(), y*getTileHeight(), getTileWidth(), getTileHeight()));
					//Neue Methode:
					
				}
				y++;
			}
			x++;
		}
	}*/
	
	public ArrayList<Shape> getMovementBlockers() {
		return movementBlockers;
	}
}
