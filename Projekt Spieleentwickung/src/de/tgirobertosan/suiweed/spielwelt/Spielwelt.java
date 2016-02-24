package de.tgirobertosan.suiweed.spielwelt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

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

import de.tgirobertosan.suiweed.Kampfsystem;
import de.tgirobertosan.suiweed.SpielState;
import de.tgirobertosan.suiweed.charakter.Charakter;
import de.tgirobertosan.suiweed.spielwelt.trigger.Dialogzeile;
import de.tgirobertosan.suiweed.spielwelt.trigger.LocatedSound;
import de.tgirobertosan.suiweed.spielwelt.trigger.Teleport;
import de.tgirobertosan.suiweed.spielwelt.trigger.Trigger;
import de.tgirobertosan.suiweed.spielwelt.trigger.Trigger.TriggerEvent;


public class Spielwelt extends TiledMapPlus {
	
	//Settings für Editor:
	private int layerIndexAbovePlayer = 2; //Layer >= layerIndexAbovePlayer werden nach dem Spieler gerendert
	private String collisionObjectLayerName = "block"; //Name des ObjectLayers mit dem Kollisionsobjekte auf Map erzeugt werden

	private String name;
	
	/**Der Charakter, falls er sich auf dieser Map befindet**/
	private Charakter charakter;
	private SpielState spielState;
	
	private int absoluteX = 0;
	private int absoluteY = 0;
	
	private int transX = 0;
	private int transY = 0;
	private int mapWidth;
	private int mapHeight;
	private float maxDistanceQuadriert = 100000;
	

	private ArrayList<Shape> collisionObjects = new ArrayList<Shape>();
	private ArrayList<Trigger> interactionTriggers = new ArrayList<Trigger>();
	private ArrayList<Trigger> locationTriggers = new ArrayList<Trigger>();
	private ArrayList<Trigger> initTriggers = new ArrayList<Trigger>();
	
	private ArrayList<Gegner> gegner = new ArrayList<Gegner>();

	private boolean checkInitTriggers = true;
	private HashSet<SoundOnSpielwelt> loopedSounds = new HashSet<SoundOnSpielwelt>();
	
	//Testsound
	private SoundOnSpielwelt testSound = new SoundOnSpielwelt("res/spielwelt/audio/kebab.ogg", -1, -1, 1);
	private Kampfsystem kampfsystem = new Kampfsystem();

	public Spielwelt(String path) throws SlickException {
		super(path);
		this.name = path.substring(path.lastIndexOf("/")+1);
		this.mapWidth = getWidth()*getTileWidth();
		this.mapHeight = getHeight()*getTileHeight();
		this.maxDistanceQuadriert = (float) (Math.pow(mapWidth, 2)+Math.pow(mapHeight, 2));
		
	}
	
	public void init(SpielState spielState) throws SlickException {
		this.spielState = spielState;
		addObjects();
		addTileCollisions();
		charakter.gibKampfsystem(kampfsystem);
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
	public void renderTriggerShapes(Graphics g) {
		for(Trigger trigger : locationTriggers) {
			g.fill(trigger.getTriggerArea());
		}
	}
	
	/**
	 * @author Shockper (from http://slick.ninjacave.com/forum/viewtopic.php?t=4713)
	 * @param screenWidth Width of the Window
	 * @param screenHeight Height of the Window
	 */
	public void focusCharakter(int screenWidth, int screenHeight) {
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
	
	public void playLoopedSounds() {
		for(SoundOnSpielwelt sound : loopedSounds) {
			if(!sound.playing())
				sound.playSoundFor(charakter, maxDistanceQuadriert);
		}
	}
	
	/**Rendert die Map komplett mit Spieler**/
	public void renderWithObjects(GameContainer container, Graphics g) throws SlickException {
		g.translate(transX, transY);
		renderGroundLayers();
		
		for(Gegner gegner: gegner){
			gegner.render(g);
		}
		if(charakter != null) {
			charakter.renderCharakter();
			g.draw(getCharakter().getCollisionShape());
		}
		
		renderTopLayers();

		//renderTriggerShapes(g);
		//renderCollisionObjects(g);
		if(charakter != null)
			charakter.zeichneNamen(g);
		g.translate(-transX, -transY);
		if(charakter != null)
			charakter.renderInventar(container, g);
		
	}
	
	public void renderGroundLayers() {
		for(int i = 0; i < layerIndexAbovePlayer && i < getLayerCount(); i++) {
			render(absoluteX, absoluteY, i);
		}
	}
	
	public void renderTopLayers() {
		for(int i = layerIndexAbovePlayer;i < getLayerCount(); i++) {
			render(absoluteX, absoluteY, i);
		}
	}
	
	private void addTileCollisions() {
		for(TileSet tileSet : getTilesets())
			for(GlobalTile globalTile : tileSet.getGlobalTiles()) {
				Shape shape = globalTile.getCollisionShape();
				if(shape != null) {
					for(TileOnLayer tileOnLayer : getAllTilesFromAllLayers(tileSet.name)) {
						if(tileOnLayer.getGid() == globalTile.getGid()) {
							collisionObjects.add(shape.transform(Transform.createTranslateTransform(tileOnLayer.x*tileSet.getTileWidth()+absoluteX, tileOnLayer.y*tileSet.getTileHeight()+absoluteY)));
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
			else {
				for(GroupObject groupObject : objectGroup.getObjects()) {
					Trigger trigger = null;
					System.out.println(groupObject.type);
					if(groupObject.type.equalsIgnoreCase("charakter") && charakter == null) {
						charakter = new Charakter(groupObject.name, groupObject.x, groupObject.y, this);
						spielState.getInputHandler().setCharakter(charakter);
					} else if(groupObject.type.equalsIgnoreCase("Teleport")) {
						trigger = Teleport.getFromGroupObject(groupObject, null, spielState);
					} else if(groupObject.type.equalsIgnoreCase("Gegner")) {
						gegner.add(new Gegner(groupObject.x, groupObject.y));
					}else if(groupObject.type.equalsIgnoreCase("LocatedSound")) {
						trigger = LocatedSound.getFromGroupObject(groupObject);
					} else if(groupObject.type.equalsIgnoreCase("Dialogzeile")) {
						trigger = Dialogzeile.getFromGroupObject(groupObject);
					}
					if(trigger == null)
						continue;
					if(trigger.getTriggerEvent() == TriggerEvent.INTERACT)
						interactionTriggers.add(trigger);
					else if(trigger.getTriggerEvent() == TriggerEvent.INIT) {
						//NOTE: INIT-Triggers werden erst in checkTriggers() gefeuert, Map wäre hier noch nicht gerendert.
						initTriggers.add(trigger);
					}
					else
						locationTriggers.add(trigger);
				}
			}
		}
	}
	
	public void testLocatedSound(int x, int y) {
		//Methode ist nur da um zu testen ob Sound Placement (Mausklick aus InputHandler) funktioniert
		if(!testSound.playing()) {
			testSound.setX(-transX+x);
			testSound.setY(-transY+y);
			playSoundAt(testSound);
		}
	}
	
	public void updateGegner(){
		for(Gegner gegner: gegner){
			gegner.update();
			gegner.schaueNachSpieler(charakter);
		}
		//Die Methode sortiert die Gegner in der ArrayList nach der Y-Position
		Collections.sort(gegner, new Comparator<Gegner>() {

			@Override
			public int compare(Gegner o1, Gegner o2) {
				// TODO Auto-generated method stub
				return Float.compare(o1.getY(), o2.getY());
			}
		});
	}
	
	public void playSoundAt(SoundOnSpielwelt sound) {
		playSoundAt(sound, false);
	}

	public void playSoundAt(SoundOnSpielwelt sound, boolean loop) {
		sound.playSoundFor(charakter, maxDistanceQuadriert);
		if(loop)
			loopedSounds.add(sound);
	}
	
	public void checkTriggers() {
		if(checkInitTriggers) {
			for(Trigger trigger : initTriggers)
				trigger.checkAndFire(charakter);
			checkInitTriggers = false;
		}
		for(Trigger trigger : locationTriggers) {
			trigger.checkAndFire(charakter);
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
	
	public String getName() {
		return name;
	}

	public ArrayList<Trigger> getInteractionTriggers() {
		return interactionTriggers;
	}
	
	public void changeSpielwelt(Spielwelt neueSpielwelt) {
		if(neueSpielwelt != null) {
			spielState.changeSpielwelt(neueSpielwelt);
		}
	}
	
	public ArrayList<Gegner> getGegner(){
		
		return(gegner);
		
	}
}
