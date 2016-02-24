package de.tgirobertosan.suiweed.gui;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;



public class MenuState extends BasicGameState{


	private int id = 0;

	private int xButton;
	private int hoeheButton = 100;
	private int breiteButton = 150;

	private Image hintergrundbild;

	private Sound hintergrundmusik;
	private Sound click;

	private ArrayList<MouseOverArea> mouseOverArea = new ArrayList<MouseOverArea>();

	private boolean mouseClicked = false;


	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		xButton = container.getWidth() / 2 - breiteButton/2;


		hintergrundbild = new Image("res/gui/images/background.png");
		hintergrundbild = hintergrundbild.getScaledCopy(container.getWidth(), container.getHeight() ); //breite ergibt sich aus dem Seitenverhältniss

	    //hintergrundmusik = new Sound("res/gui/audio/hintergrundmusik.ogg");

		//click = new Sound("res/gui/audio/klick.wav");


		mouseOverArea.add(0,new MouseOverArea(container, new Image("res/gui/images/start.png"), new Rectangle(xButton, 100, breiteButton, hoeheButton)));
		mouseOverArea.get(0).setMouseOverImage(new Image("res/gui/images/startHover.png"));
		mouseOverArea.get(0).setMouseDownImage(new Image("res/gui/images/startPressed.png"));


		mouseOverArea.add(1,new MouseOverArea(container, new Image("res/gui/images/ende.png"), new Rectangle(xButton, 200, breiteButton, hoeheButton)));
		mouseOverArea.get(1).setMouseOverImage(new Image("res/gui/images/endeHover.png"));
		mouseOverArea.get(1).setMouseDownImage(new Image("res/gui/images/endePressed.png"));



	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

		hintergrundbild.drawCentered(container.getWidth()/2, container.getHeight()/2);


		for (int i = 0; i < mouseOverArea.size(); i++) {

			mouseOverArea.get(i).render(container, g);

		}



	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		if(container.getInput().isMouseButtonDown(0)) {
			mouseClicked = true;
		}

		if(!container.getInput().isMouseButtonDown(0) && mouseClicked){


			mouseClicked = false;


			if(mouseOverArea.get(0).isMouseOver()){

				game.enterState(1);

			}

			if(mouseOverArea.get(1).isMouseOver()){

				container.exit();

			}


		}


	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}


}