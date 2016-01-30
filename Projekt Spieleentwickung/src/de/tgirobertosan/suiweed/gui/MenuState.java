package de.tgirobertosan.suiweed.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MenuState extends BasicGameState{
	
	
	private int id = 0;

	private int xButton;
	private int hoeheButton = 50;
	private int breiteButton = 120;
	
	private Image[] bild;

	private Image hintergrundbild;
	private int hoeheBild;

	private Sound hintergrundmusik;
	private Sound click;
	private Shape[] buttons;

	private Shape hitboxMaus;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	
		hoeheBild = container.getHeight();
		xButton = container.getWidth() / 2 - breiteButton/2;

		
		hintergrundbild = new Image("res/gui/images/hintergrundbild.png");
		hintergrundbild = hintergrundbild.getScaledCopy((int)((int) hoeheBild/0.76), hoeheBild ); //breite ergibt sich aus dem Seitenverhältniss

		hintergrundmusik = new Sound("res/gui/audio/hintergrundmusik.ogg");
		hintergrundmusik.play();
		click = new Sound("res/gui/audio/klick.wav");


		Rectangle start = new Rectangle(xButton, 100, breiteButton, hoeheButton);
		Rectangle beenden = new Rectangle(xButton, 200, breiteButton, hoeheButton);
		

		buttons = new Shape[2];
		buttons[0]= start;
		buttons[1]= beenden;
		
		bild = new Image[buttons.length];
		bild[0] = new Image("res/gui/images/start.png");
		bild[1] = new Image("res/gui/images/ende.png");   
		
		hitboxMaus = new Circle(0, 0, 2);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		hintergrundbild.drawCentered(container.getWidth()/2, container.getHeight()/2);

		g.setColor(Color.red);
		g.drawString("Spielmenü", xButton, 50);

		for (int i = 0; i < buttons.length; i++) {
			g.setColor(Color.white);
			g.texture(buttons[i], bild[i], true);
		}

		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		hitboxMaus.setCenterX(container.getInput().getMouseX());
		hitboxMaus.setCenterY(container.getInput().getMouseY());
		
		for (int i = 0; i < buttons.length; i++) {

			if(hitboxMaus.intersects(buttons[i]) && container.getInput().isMouseButtonDown(0)){

				
				if(i == 0){
					click.play();
					hintergrundmusik.stop();
					game.enterState(1);
				}
				if(i == 1 ){
					click.play();

					container.exit();
				}
			}
		}
		
	


	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

	
}
