package de.tgirobertosan.suiweed.charakter;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import de.tgirobertosan.suiweed.items.Waffe;


public class Inventar implements KeyListener, MouseListener {


	//Attribute

	private float x = 100;
	private float y = 100;
	private float slotX = x + 28;
	private float slotY = y + 81;
	private float slotBreite = 32;
	private float slotHoehe = 32;
	private float slotRandBreite = 4;
	private Image inventar;
	private boolean sichtbarkeit = false; //true = sichtbar, false = unsichtbar
	private Shape inventarleiste;
	private ArrayList<Shape> slots = new ArrayList<Shape>();
	private byte slotSpaltenAnzahl = 4;
	private byte slotZeilenAnzahl  = 6;
	private Waffe waffe; //WAFFE IST NUR TEST ZWECK, INVENTAR SOLLTE GEGENSTAND HABEN!
	private boolean waffeImInventar = false;
	private Input input;

	//Methoden

	public void init(GameContainer container) throws SlickException{
		inventar = new Image("res/character/images/Inventar.png");
		inventarleiste = new Rectangle(x, y, 339, 63);

		for(byte spalte = 0, zeile = 0; spalte < slotSpaltenAnzahl; spalte++){
			slots.add(new Rectangle((spalte *slotBreite) + slotX + (spalte * slotRandBreite), (zeile*slotHoehe) + slotY+ (zeile * slotRandBreite), slotBreite, slotHoehe));

			if(zeile  < slotZeilenAnzahl -1 && spalte == slotSpaltenAnzahl - 1){
				zeile++;
				spalte = -1;
			}
		}

		waffe = new Waffe();
		input = container.getInput();
		input.addKeyListener(this);
		input.addMouseListener(this);

	}

	public void update(GameContainer arg0, int arg1) throws SlickException {



	}	

	public void render(GameContainer arg0, Graphics g) throws SlickException {
		zeichne(x, y);
		g.setColor(Color.cyan);
		/*g.draw(inventarleiste);
		for(int i = 0; i < slots.size();i++){
			g.draw(slots.get(i));
		}*/
	}


	private void zeichne(float x , float y){

		if(sichtbarkeit){
			inventar.draw(x,y);
			if(waffeImInventar){ //Testzweck
				waffe.getWaffe().drawCentered(slots.get(0).getCenterX(), slots.get(0).getCenterY());
			}
		}
	}

	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int arg0, char arg1) {

		switch(arg0){
		case(Input.KEY_I): sichtbarkeit = !sichtbarkeit ; break;
		default:break;
		}

		if(Input.KEY_E == arg0 && sichtbarkeit){
			waffeImInventar = !waffeImInventar;
		}
	}

	@Override
	public void keyReleased(int arg0, char arg1) {


	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(sichtbarkeit && oldx >= inventarleiste.getX() && oldy >= inventarleiste.getY()){
			if(oldx <= inventarleiste.getX() + inventarleiste.getWidth() && oldy <= inventarleiste.getY()+ inventarleiste.getHeight()){

				x = x + (newx - oldx);
				y = y + (newy - oldy);
				slotX = slotX + (newx - oldx);
				slotY = slotY + (newy - oldy);
				inventarleiste.setLocation(x, y);

				for(byte spalte = 0, zeile = 0, i = 0; i < slots.size(); spalte++, i++){

					slots.get(i).setLocation((spalte *slotBreite) + slotX + (spalte*slotRandBreite), (zeile*slotHoehe) + slotY + (zeile * slotRandBreite));

					if(zeile  < slotZeilenAnzahl -1 && spalte == slotSpaltenAnzahl - 1){
						zeile++;
						spalte = -1;
					}
				}
			}
		}

	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {


	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub

	}
}