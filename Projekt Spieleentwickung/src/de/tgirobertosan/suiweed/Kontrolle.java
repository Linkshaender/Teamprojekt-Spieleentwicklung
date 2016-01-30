package de.tgirobertosan.suiweed;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

import de.tgirobertosan.suiweed.charakter.Charakter;
import de.tgirobertosan.suiweed.charakter.InputHandler;

import de.tgirobertosan.suiweed.gui.MenuState;

public class Kontrolle extends StateBasedGame{
	
	private AppGameContainer spiel = null;

	public Kontrolle(String name) {
		super(name);

	}
	
	public void spielErstellen(){
		try {
			spiel = new AppGameContainer(new Kontrolle("Test"));
			spiel.setDisplayMode(1000, 800, false);//Breite,Hoehe,Fullscreen
			spiel.setTargetFrameRate(60);
			spiel.start();
			

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new MenuState());
		this.addState(new SpielState());
		
		
	}
	
	
	
	

}