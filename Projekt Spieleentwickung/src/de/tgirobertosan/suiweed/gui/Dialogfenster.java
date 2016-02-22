package de.tgirobertosan.suiweed.gui;

import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Dialogfenster {
	
	private Timer timer = new Timer();
	
	private String text = "";
	private String aktuellerText = "";
	
	private Image fenster;
	private String path = "res/gui/images/dialogfenster.png";
	
	private int fensterHoehe;
	private int fensterBreite;
	private int x;
	private int y;
	
	private boolean sichtbarkeit = false;
	
	private int counter = 0;
	
	private GameContainer container;
	
	private SimpleFont simplefont;
	
	private int line = 1;
	
	public Dialogfenster(GameContainer container, int line) throws SlickException, IOException {
		
		super();
		this.container = container;
		this.line = line;
		
		fenster = new Image(path);
		
		fensterHoehe = container.getHeight()/7;
		fensterBreite = container.getWidth();
		x = 0;
		y = container.getHeight() - fensterHoehe;
	
		simplefont = new SimpleFont( "Times New Roman", Font.BOLD, 20);
		
		fenster.setAlpha(0.5f);
		
	}

	public void render(Graphics g) throws SlickException{

		if (sichtbarkeit) {
			fenster.draw(x, y, fensterBreite, fensterHoehe);
			
			g.setFont(simplefont.get());
			g.drawString(aktuellerText, x, y);
		}
	
		

		
	}

	public void aktualisiereDialogfenster(int line) throws IOException{
		
		sichtbarkeit = !sichtbarkeit;

		text = Files.readAllLines(Paths.get("res/gui/dialoge.txt")).get(line);

		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				if(counter <= text.length() ){
				
					aktuellerText = text.substring(0, counter);
					counter++;
				}
				else{
					timer.cancel();
					timer.purge();
					
				}
				
			}
		}, 0, 250);
		

	}
	
	



}
