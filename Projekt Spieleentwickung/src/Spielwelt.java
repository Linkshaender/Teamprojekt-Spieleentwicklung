import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;


public class Spielwelt extends TiledMap{

	private ArrayList<Rectangle> movementBlockers = new ArrayList<Rectangle>();
	
	
	public Spielwelt(String path) throws SlickException {
		super(path);
	}
	
	public void init() {
		addMovementBlockers();
		
	}
	
	private void addMovementBlockers() {
		int x = 0, y = 0;
		while(x < getWidth()) {
			y = 0;
			while(y < getHeight()) {
				int tileID = getTileId(x, y, 0);
				String blocked = getTileProperty(tileID, "movementBlocker", "false");
				if(blocked.equals("true")) {
					movementBlockers.add(new Rectangle(x*getTileWidth(), y*getTileHeight(), getTileWidth(), getTileHeight()));
				}
				y++;
			}
			x++;
		}
	}
	
	public ArrayList<Rectangle> getMovementBlockers() {
		return movementBlockers;
	}

}
