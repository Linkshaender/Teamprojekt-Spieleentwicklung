package de.tgirobertosan.suiweed.charakter;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;

import de.tgirobertosan.suiweed.Spielwelt;



public class Charakter {

	private String name;
	private float x = 0;
	private float y = 0;
	private int breite = 47;
	private int hoehe = 48;
	private int animationsGeschw = 99;
	private int bewegungsGeschw = 1;
	private String richtung = "Runter";
	private ArrayList<Animation> bewegungsAnimation = new ArrayList<Animation>();
	//private Input input;
	private SpriteSheet laufSprite;
	private Inventar inventar = new Inventar();
	
	private Spielwelt spielwelt;
	private Shape collisionShape;

	public Charakter(String name) {

		this.name = name;
	}

	public void init(GameContainer container) throws SlickException {

		laufSprite = new SpriteSheet("res/character/sprites/lauf.png", 141, 192);
		this.collisionShape = new RoundedRectangle(x+5, y, breite-10, hoehe, 10);
		initialisiereAnimation();
		inventar.init(container);
		//input = container.getInput();
		//input.addKeyListener(this);
	}

	public void update(GameContainer arg0, int arg1) throws SlickException {
		inventar.update(arg0, arg1);
		//bewege();
	}	

	public void render(GameContainer arg0, Graphics g) throws SlickException {
			zeichne();
			inventar.render(arg0, g);
		
	}

	/*
	private void bewege(){

		if(input.isKeyDown(Input.KEY_S)){
			bewegungsAnimation.get(0).start();
			y++;
			richtung = "Runter";
		}
		else bewegungsAnimation.get(0).stop();


		if(input.isKeyDown(Input.KEY_A)){
			bewegungsAnimation.get(1).start();
			x--;
			richtung = "Links";
		}
		else bewegungsAnimation.get(1).stop();


		if(input.isKeyDown(Input.KEY_D)){
			bewegungsAnimation.get(2).start();
			x++;
			richtung = "Rechts";
		}
		else bewegungsAnimation.get(2).stop();


		if(input.isKeyDown(Input.KEY_W)){
			bewegungsAnimation.get(3).start();
			y--;
			richtung = "Hoch";
		}
		else bewegungsAnimation.get(3).stop();	
	}
	*/
	
	//xDir = 1 -> bewegung "nach rechts". xDir = -1 -> "nach links. yDir = -1 -> "nach oben". yDir = 1 -> "nach unten".
	public void move(int xDir, int yDir) {
		float xOffset = xDir * bewegungsGeschw;
		float yOffset = yDir * bewegungsGeschw;
		
		collisionShape.setX(collisionShape.getX()+xOffset);
		if(spielwelt.checkCollision(collisionShape))
			collisionShape.setX(collisionShape.getX()-xOffset);
		else {
			x = x + xOffset;
			if(xDir > 0) {
				bewegungsAnimation.get(1).stop();
				bewegungsAnimation.get(2).start();
				richtung = "Rechts";
			} else if(xDir < 0) {
				bewegungsAnimation.get(2).stop();
				bewegungsAnimation.get(1).start();
				richtung = "Links";
			} else {
				bewegungsAnimation.get(1).stop();
				bewegungsAnimation.get(2).stop();
			}
		}

		collisionShape.setY(collisionShape.getY()+yOffset);
		if(spielwelt.checkCollision(collisionShape))
			collisionShape.setY(collisionShape.getY()-yOffset);
		else {
			y = y + yOffset;
			if(yDir > 0) {
				bewegungsAnimation.get(3).stop();
				bewegungsAnimation.get(0).start();
				richtung = "Runter";
			} else if(yDir < 0) {
				bewegungsAnimation.get(0).stop();
				bewegungsAnimation.get(3).start();
				richtung = "Hoch";
			} else {
				bewegungsAnimation.get(0).stop();
				bewegungsAnimation.get(3).stop();
			}
		}
	}

	private void zeichne(){

		switch(richtung){
		case("Runter"): bewegungsAnimation.get(0).draw(x, y);break;
		case("Links"): bewegungsAnimation.get(1).draw(x, y);break;
		case("Rechts"): bewegungsAnimation.get(2).draw(x, y);break;
		case("Hoch"): bewegungsAnimation.get(3).draw(x, y);break;
		default: break;
		}
	}


	public void zeichneNamen(Graphics g) {

		g.setColor(Color.red);
		g.drawString(name, x + 6 ,y - 16 );

	}


	private void initialisiereAnimation(){

		for(byte i = 0; i < 4; i++){

			bewegungsAnimation.add(new Animation(new SpriteSheet(laufSprite.getSubImage(0,i * hoehe, breite*3, hoehe), breite, hoehe),animationsGeschw));
			bewegungsAnimation.get(i).setPingPong(true);


		}		

	}
	
	public void setSpielwelt(Spielwelt spielwelt) {
		this.spielwelt = spielwelt;
	}
	
	public Shape getCollisionShape() {
		return collisionShape;
	}

	/*@Override
	public void inputEnded() {


	}

	@Override
	public void inputStarted() {


	}

	@Override
	public boolean isAcceptingInput() {

		return true;
	}

	@Override
	public void setInput(Input arg0) {


	}

	@Override
	public void keyPressed(int arg0, char arg1) {



	}

	@Override
	public void keyReleased(int arg0, char arg1) {


	}*/
}