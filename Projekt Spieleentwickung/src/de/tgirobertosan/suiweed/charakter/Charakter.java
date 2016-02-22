package de.tgirobertosan.suiweed.charakter;
import java.io.IOException;
import java.util.ArrayList;


import java.util.TimerTask;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;

import de.tgirobertosan.suiweed.gui.Dialogfenster;
import de.tgirobertosan.suiweed.spielwelt.Location;
import de.tgirobertosan.suiweed.spielwelt.Spielwelt;

import java.util.Timer;


public class Charakter {

	private String name;
	private float x = 0;
	private float y = 0;
	private float gegnerX = 100; //Test für Gegnerposition
	private float gegnerY = 100; //Test für Gegnerposition
	private int kampfdistanz = 10; //Abstand von Spieler und Gegner im Kampf
	private int maxAngriffsdistanzX = 100;
	private int maxAngriffsdistanzY = 100;
	private int waffenStaminaverbrauch = 10; //Test für Waffenstaminaverbrauch
	private int waffenSchaden = 15; //Test für Waffenschaden
	private int gegnerLeben = 650; //Test für Gegnerleben
	private int stamina = 100;// aktuelle Stamina
	private int maxStamina = 100; // maximale Stamina für den Charakter
	private int breite = 47;
	private int hoehe = 48;
	private int animationsGeschw = 99;
	private int bewegungsGeschw = 1;
	private boolean timerLaeuft = false;
	private Richtung richtung = Richtung.RUNTER;
	private ArrayList<Animation> bewegungsAnimation = new ArrayList<Animation>();
	//private Input input;
	private SpriteSheet laufSprite;
	private Inventar inventar = new Inventar();

	private Spielwelt spielwelt;
	private Shape collisionShape;
	private int collisionXOffset = 1+breite/2;
	private int collisionYOffset = 15+hoehe/2;

	Timer timer = new Timer();

	private Dialogfenster dialogfenster;
	
	public enum Richtung {
		RUNTER, LINKS, RECHTS, HOCH
	}

	public Charakter(String name) {
		this(name, 0, 0, null);
	}

	public Charakter(String name, float x, float y, Spielwelt spielwelt) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.spielwelt = spielwelt;
	}

	public void init(GameContainer container) throws SlickException, IOException{

		laufSprite = new SpriteSheet("res/character/sprites/lauf.png", 141, 192);
		this.collisionShape = new Circle(x+collisionXOffset, y+collisionYOffset, breite/4);
		initialisiereAnimation();
		inventar.init(container);
		//input = container.getInput();
		//input.addKeyListener(this);
	
		dialogfenster = new Dialogfenster(container, 1);
		dialogfenster.aktualisiereDialogfenster(1);
	}
	
	

	public void update(GameContainer arg0, int arg1) throws SlickException {
		inventar.update(arg0, arg1);
		//bewege();
	}	

	public void renderCharakter() {
		zeichne();
		
	}

	public void renderInventar(GameContainer container, Graphics g) throws SlickException {
		inventar.render(container, g);
		dialogfenster.render(g);
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
				richtung = Richtung.RECHTS;
			} else if(xDir < 0) {
				bewegungsAnimation.get(2).stop();
				bewegungsAnimation.get(1).start();
				richtung = Richtung.LINKS;
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
				richtung = Richtung.RUNTER;
			} else if(yDir < 0) {
				bewegungsAnimation.get(0).stop();
				bewegungsAnimation.get(3).start();
				richtung = Richtung.HOCH;
			} else {
				bewegungsAnimation.get(0).stop();
				bewegungsAnimation.get(3).stop();
			}
		}
	}

	private void zeichne(){

		switch(richtung){
		case RUNTER: bewegungsAnimation.get(0).draw(x, y);break;
		case LINKS: bewegungsAnimation.get(1).draw(x, y);break;
		case RECHTS: bewegungsAnimation.get(2).draw(x, y);break;
		case HOCH: bewegungsAnimation.get(3).draw(x, y);break;
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

	public void setLocation(Location destination) {
		this.x = destination.getX();
		this.y = destination.getY();
		this.collisionShape.setCenterX(x+collisionXOffset);
		this.collisionShape.setCenterY(y+collisionYOffset);
		changeSpielwelt(destination.getSpielwelt());
	}


	public Spielwelt getSpielwelt() {
		return spielwelt;
	}

	public void changeSpielwelt(Spielwelt destinationWelt) {
		if(destinationWelt != null && !destinationWelt.equals(spielwelt)) {
			destinationWelt.setCharakter(this);
			spielwelt.changeSpielwelt(destinationWelt);
			this.spielwelt = destinationWelt;
		}
	}

	public Shape getCollisionShape() {
		return collisionShape;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean isLookingInDirectionOf(Shape shape) {
		boolean lookingInDirection = true;
		switch(richtung) {
		case RUNTER:
			if(shape.getMaxY() < this.y)
				lookingInDirection = false;
			break;
		case HOCH:
			if(shape.getMinY() > this.y)
				lookingInDirection = false;
			break;
		case LINKS:
			if(shape.getMinX() > this.x)
				lookingInDirection = false;
			break;
		case RECHTS:
			if(shape.getMaxX() < this.x)
				lookingInDirection = false;
			break;
		}
		return lookingInDirection;
	}

	public boolean isInNearOf(Shape shape, int range) {
		boolean pointIsX = true;
		boolean isInNear = false;
		for(float point : shape.getPoints()) {
			if(pointIsX) {
				pointIsX = false;
				if(point > collisionShape.getCenterX()+range || point < collisionShape.getCenterX()-range)
					isInNear = false;
				else
					isInNear = true;
			} else {
				pointIsX = true;
				if(!isInNear)
					continue;
				if(point <= collisionShape.getCenterY()+range && point >= collisionShape.getCenterY()-range)
					return true;
			}
		}
		return false;
	}

	public void attack() {

		if(timerLaeuft == false){

			starteStaminaTimer();
			timerLaeuft = true;
		}

		if(stamina > 0){
			stamina = stamina - waffenStaminaverbrauch; //Stamina Abzug bei Angriff

			if(x > gegnerX - maxAngriffsdistanzX && x < gegnerX + maxAngriffsdistanzX && y < gegnerY + maxAngriffsdistanzY && y > gegnerY - maxAngriffsdistanzY ){  //Prüfen ob Gegner nahe genug für Angiff ist

				while(x > gegnerX + kampfdistanz){
					move(-1,0);


				}

				while(x < gegnerX - kampfdistanz){
					move(1,0);
				}

				while(y < gegnerY - kampfdistanz){
					move(0,1);
				}

				while(y > gegnerY + kampfdistanz){
					move(0,-1);
				}   //Automatisches bewegen des Charakters an den Gegner

				gegnerLeben = gegnerLeben - waffenSchaden;


				System.out.println("Gegner:"+gegnerLeben);
				if(gegnerLeben <= 0){
					System.out.println("Tot");


				}
			}
		}
	}

	public void zeichneTestgegner(Graphics g) {

		g.setColor(Color.red);
		g.drawOval(100, 130, 20, 20);
		// TODO Auto-generated method stub

	}

	public void starteStaminaTimer(){

		timer.scheduleAtFixedRate(new TimerTask() { //Timer für die Staminaregeneration
			@Override
			public void run() {
				if(stamina < maxStamina){
					stamina = stamina + 1;
					System.out.println("Stamina:"+" "+stamina);
				}
			}
		}, 1000, 1000);

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