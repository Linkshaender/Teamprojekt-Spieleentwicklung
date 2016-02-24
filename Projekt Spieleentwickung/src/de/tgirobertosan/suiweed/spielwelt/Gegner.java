package de.tgirobertosan.suiweed.spielwelt;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import de.tgirobertosan.suiweed.charakter.Charakter;

public class Gegner {



	private float x;
	private float y;
	private float startX;
	private Image gegner;
	private Charakter spieler;
	private boolean move;
	private boolean verfolgen;

	public Gegner(float x, float y){
		this.x = x;
		this.y = y;

		try {
			gegner = new Image("res/gegner/image/gegner.png");
			sicht = new Circle(x + 20, y + 25, 150);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		startX = x;
	}

	private Circle sicht;


	public void render(Graphics g){
		g.drawImage(gegner, x, y);

	}

	public void update(){
		if(!verfolgen)
			bewegen();
		else 
			verfolgen();
	}
	private void bewegen(){
		if(move){
			x = (float) (x + 0.3);
		}
		else if(!move){
			x = (float) (x - 0.3);
		}
		if(x >= startX + 15){
			move = false;
		}
		else if(x <= startX - 15){
			move = true;
		}	

	}
	public void verfolgen(){
		if(x>=spieler.getX()){
			x = x - 0.6f;
		}
		else if(x<spieler.getX()){
			x = x + 0.6f;
		}
		if(y>=spieler.getY()){
			y = y - 0.6f;
		}
		else if(y<spieler.getY()){
			y = y + 0.6f;
		}
	}
	public void schaueNachSpieler(Charakter spieler){
		this.spieler = spieler;
		if(sicht.intersects(spieler.getCollisionShape())){
			verfolgen = true;
		}
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public Circle getSicht() {
		return sicht;
	}

	public boolean isVerfolgen() {
		return verfolgen;
	}
	public void setVerfolgen(boolean verfolgen){
		this.verfolgen = verfolgen;
	}
}
