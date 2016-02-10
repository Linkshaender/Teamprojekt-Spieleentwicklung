package de.tgirobertosan.suiweed.spielwelt;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import de.tgirobertosan.suiweed.charakter.Charakter;

public class SoundOnSpielwelt extends Sound {

	private float volumeModifier;
	private float x;
	private float y;
	
	public SoundOnSpielwelt(String ref, float x, float y, float volumeModifier) throws SlickException {
		super(ref);
		this.x = x;
		this.y = y;
		this.volumeModifier = volumeModifier;
	}
	
	public void playSoundFor(Charakter charakter, float maxDistanceQuadriert) {
		float aQuadrat = (float) Math.pow(Math.abs(x-charakter.getX()), 2);
		float bQuadrat = (float) Math.pow(Math.abs(y-charakter.getY()), 2);
		float cQuadrat = aQuadrat+bQuadrat;
		this.playAt(1, ((maxDistanceQuadriert-cQuadrat)/maxDistanceQuadriert)*volumeModifier, 0, 0, 1);
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

}
