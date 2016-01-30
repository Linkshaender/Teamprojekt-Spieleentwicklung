package de.tgirobertosan.suiweed.items;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Waffe {

	private Image schwert;
	
	public Waffe() throws SlickException{
		
		schwert = new Image("res/character/images/Schwert.png");
	}
	
	
	public Image getWaffe(){
		return schwert;
	}
	
}
